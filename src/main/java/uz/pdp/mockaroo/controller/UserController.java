package uz.pdp.mockaroo.controller;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.mockaroo.payload.auth.LoginDTO;
import uz.pdp.mockaroo.payload.auth.RegisterDTO;
import uz.pdp.mockaroo.payload.auth.SessionDTO;
import uz.pdp.mockaroo.payload.response.ApiResponse;
import uz.pdp.mockaroo.service.AuthService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping(value = "/user")
@RequiredArgsConstructor
public class UserController {

    private final AuthService authService;


    @PostMapping("/token")
    public ResponseEntity<ApiResponse<SessionDTO>> getToken(@Valid @RequestBody LoginDTO dto) {
        return authService.getToken(dto);
    }

    @SneakyThrows
    @GetMapping("/refresh-token")
    public ResponseEntity<ApiResponse<SessionDTO>> getToken(HttpServletRequest request, HttpServletResponse response) {
        return authService.getRefreshToken(request, response);
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<?>> doRegister(@Valid @RequestBody RegisterDTO registerDTO){
        return authService.registerUser(registerDTO);
    }
}
