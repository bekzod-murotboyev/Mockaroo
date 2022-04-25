package uz.pdp.mockaroo.payload.auth;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterDTO {
    private String name;
    private String email;
    private String password;
}
