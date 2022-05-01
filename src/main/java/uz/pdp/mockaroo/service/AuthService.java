package uz.pdp.mockaroo.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;
import uz.pdp.mockaroo.config.filter.JwtUtils;
import uz.pdp.mockaroo.entity.User;
import uz.pdp.mockaroo.payload.auth.LoginDTO;
import uz.pdp.mockaroo.payload.auth.RegisterDTO;
import uz.pdp.mockaroo.payload.auth.SessionDTO;
import uz.pdp.mockaroo.payload.response.ApiResponse;
import uz.pdp.mockaroo.payload.response.AppErrorDTO;
import uz.pdp.mockaroo.property.ServerProperties;
import uz.pdp.mockaroo.repository.UserRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {

    private final UserRepository userRepository;
    private final ServerProperties serverProperties;
    private final ObjectMapper objectMapper;

    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username + " not found!"));
    }

    public ResponseEntity<ApiResponse<SessionDTO>> getToken(LoginDTO dto) {

        try {
            HttpClient httpclient = HttpClientBuilder.create().build();
            HttpPost httppost = new HttpPost(serverProperties.getServerUrl() + "/user/login");
            byte[] bytes = objectMapper.writeValueAsBytes(dto);
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
            httppost.addHeader("Content-Type", "application/x-www-form-urlencoded");
            httppost.setEntity(new InputStreamEntity(byteArrayInputStream));
            HttpResponse response = httpclient.execute(httppost);
            JsonNode json_auth = objectMapper.readTree(EntityUtils.toString(response.getEntity()));
            if (json_auth.has("success") && json_auth.get("success").asBoolean()) {
                JsonNode node = json_auth.get("data");
                SessionDTO sessionDto = objectMapper.readValue(node.toString(), SessionDTO.class);
                return new ResponseEntity<>(new ApiResponse<>(sessionDto), HttpStatus.OK);
            }
            return ResponseEntity.ok(new ApiResponse<>(objectMapper.readValue(json_auth.get("error").toString(),
                    AppErrorDTO.class)));
        } catch (IOException e) {
            return new ResponseEntity<>(new ApiResponse<>(AppErrorDTO.builder()
                    .message(e.getLocalizedMessage())
                    .message(e.getMessage())
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build()), HttpStatus.OK);
        }
    }

    public ResponseEntity<ApiResponse<SessionDTO>> getRefreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {

        // gets "Authentication" header from request
        String authorizationHeader = request.getHeader(AUTHORIZATION);

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                //token
                String refresh_token = authorizationHeader.substring("Bearer ".length());

                //jwt algorithm
                Algorithm algorithm = JwtUtils.getAlgorithm();

                //checks token to valid  if not valid throws exception
                JWTVerifier verifier = JWT.require(algorithm).build();

                //if valid decode it with given algorithm
                DecodedJWT decodedJWT = verifier.verify(refresh_token);

                // from payload part gets subject
                String username = decodedJWT.getSubject();

                // gets phone from db
                User user = getUserByUsername(username);

                //creates new access token
                String access_token = JWT.create()
                        .withSubject(user.getUsername())
                        .withExpiresAt(JwtUtils.getExpiry())
                        .withIssuer(request.getRequestURL().toString())
//                        .withClaim("roles", Collections.singletonList(user.getRole().name()))
                        .sign(algorithm);

                //puts into map given refresh and created access tokens
                Map<String, String> tokens = new HashMap<>();
                tokens.put("access_token", access_token);
                tokens.put("refresh_token", refresh_token);

                //sets response type
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);

                new ObjectMapper().writeValue(response.getOutputStream(), tokens);

                SessionDTO sessionDto = SessionDTO.builder()
                        .refreshToken(refresh_token)
                        .accessToken(access_token)
                        .build();

                return ResponseEntity.ok(new ApiResponse<>(sessionDto));

            } catch (Exception exception) {
                response.setHeader("error", exception.getMessage());
                response.setStatus(HttpStatus.FORBIDDEN.value());
                //response.sendError(FORBIDDEN.value());
                Map<String, String> error = new HashMap<>();
                error.put("error_message", exception.getMessage());
                response.setContentType(MimeTypeUtils.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }
        } else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(new AppErrorDTO(HttpStatus.NOT_FOUND, "Not Found")));

        return null;
    }


    public User getUserByUsername(String username) {
        log.error("User not found with username: {}", username);
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username + " not found"));
    }

    public ResponseEntity<ApiResponse<?>> registerUser(RegisterDTO registerDTO) {
        if (userRepository.findByUsername(registerDTO.getEmail()).isPresent())
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse<>("This email already exist", false));

        userRepository
                .save(
                        new User(
                                registerDTO.getName(),
                                registerDTO.getEmail(),
                                passwordEncoder.encode(registerDTO.getPassword())
                        ));

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse
                        .builder()
                        .success(true)
                        .message("Account successfully created")
                        .build());

    }

}
