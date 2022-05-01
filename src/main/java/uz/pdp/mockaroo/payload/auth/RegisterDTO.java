package uz.pdp.mockaroo.payload.auth;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterDTO {

    @NotNull
    private String name;

    @NotNull
    private String email;

    @NotNull
    private String password;
}
