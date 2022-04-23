package uz.pdp.mockaroo.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;
import uz.pdp.mockaroo.config.filter.JwtUtils;
import uz.pdp.mockaroo.dto.AppErrorDto;
import uz.pdp.mockaroo.dto.DataDto;
import uz.pdp.mockaroo.dto.LoginDto;
import uz.pdp.mockaroo.dto.SessionDto;
import uz.pdp.mockaroo.entity.User;
import uz.pdp.mockaroo.payload.response.ResponseEntity;
import uz.pdp.mockaroo.property.ServerProperties;
import uz.pdp.mockaroo.repository.UserRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Slf4j
@Service
public class AuthService implements UserDetailsService {

    private final UserRepository userRepository;
    private final ServerProperties serverProperties;
    private final ObjectMapper objectMapper;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository repository1, ServerProperties properties,
                       ObjectMapper objectMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = repository1;
        this.serverProperties = properties;
        this.objectMapper = objectMapper;
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(()->new UsernameNotFoundException(email+" not found!"));
    }

    public ResponseEntity<DataDto<SessionDto>> getToken(LoginDto dto) {

        try {
            HttpClient httpclient = HttpClientBuilder.create().build();
            HttpPost httppost = new HttpPost(serverProperties.getServerUrl() + "/api/login");
            byte[] bytes = objectMapper.writeValueAsBytes(dto);
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
            httppost.addHeader("Content-Type", "application/x-www-form-urlencoded");
            httppost.setEntity(new InputStreamEntity(byteArrayInputStream));
            HttpResponse response = httpclient.execute(httppost);
            JsonNode json_auth = objectMapper.readTree(EntityUtils.toString(response.getEntity()));
            if (json_auth.has("success") && json_auth.get("success").asBoolean()) {
                JsonNode node = json_auth.get("data");
                SessionDto sessionDto = objectMapper.readValue(node.toString(), SessionDto.class);
                return new ResponseEntity<>(new DataDto<>(sessionDto), HttpStatus.OK);
            }
            return new ResponseEntity<>(new DataDto<>(objectMapper.readValue(json_auth.get("error").toString(),
                    AppErrorDto.class)), HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(new DataDto<>(AppErrorDto.builder()
                    .message(e.getLocalizedMessage())
                    .message(e.getMessage())
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build()), HttpStatus.OK);
        }
    }

    public ResponseEntity<DataDto<SessionDto>> getRefreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {

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
                String phoneNumber = decodedJWT.getSubject();

                // gets phone from db
                User user = getUserByPhone(phoneNumber);

                //creates new access token
                String access_token = JWT.create()
                        .withSubject(user.getPhoneNumber())
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

                SessionDto sessionDto = SessionDto.builder()
                        .refreshToken(refresh_token)
                        .accessToken(access_token)
                        .build();

                return new ResponseEntity<>(new DataDto<>(sessionDto));

            } catch (Exception exception) {
                response.setHeader("error", exception.getMessage());
                response.setStatus(HttpStatus.FORBIDDEN.value());
                //response.sendError(FORBIDDEN.value());
                Map<String, String> error = new HashMap<>();
                error.put("error_message", exception.getMessage());
                response.setContentType(MimeTypeUtils.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }
        } else {
            return new ResponseEntity<>(new DataDto<>(new AppErrorDto(HttpStatus.NOT_FOUND, "Not Found")));
        }
        return null;
    }


    public User getUserByPhone(String phone) {
        log.info("Getting user by phone : {}", phone);
        return userRepository.findAuthUserByPhoneNumber(phone);
    }


}
