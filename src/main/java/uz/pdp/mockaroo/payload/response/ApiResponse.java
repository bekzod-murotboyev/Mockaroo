package uz.pdp.mockaroo.payload.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class ApiResponse {
    String message;
    boolean success;
    Object data;
}
