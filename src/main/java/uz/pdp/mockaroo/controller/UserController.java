package uz.pdp.mockaroo.controller;

import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.mockaroo.dto.DataDto;
import uz.pdp.mockaroo.dto.LoginDto;
import uz.pdp.mockaroo.dto.SessionDto;
import uz.pdp.mockaroo.payload.response.ResponseEntity;
import uz.pdp.mockaroo.service.AuthService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/auth")
public class UserController {

    private final AuthService authService;

    public UserController(AuthService authService) {
        this.authService = authService;
    }


    @RequestMapping(value = "/token", method = RequestMethod.POST)
    public ResponseEntity<DataDto<SessionDto>> getToken(@RequestBody LoginDto dto) {
        return authService.getToken(dto);
    }

    @SneakyThrows
    @RequestMapping(value = "/refresh-token", method = RequestMethod.GET)
    public ResponseEntity<DataDto<SessionDto>> getToken(HttpServletRequest request, HttpServletResponse response) {
        return new ResponseEntity<>(new DataDto<>(authService.getRefreshToken(request, response)
                .getData().getData()));
    }
}
