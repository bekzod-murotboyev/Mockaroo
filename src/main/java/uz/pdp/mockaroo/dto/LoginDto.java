package uz.pdp.mockaroo.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginDto {

    private String phoneNumber;

    private String password;
}
