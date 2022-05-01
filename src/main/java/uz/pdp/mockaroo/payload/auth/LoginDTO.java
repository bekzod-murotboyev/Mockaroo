package uz.pdp.mockaroo.payload.auth;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginDTO {

    @NotNull
    private String email;

    @NotNull
    private String password;
}
