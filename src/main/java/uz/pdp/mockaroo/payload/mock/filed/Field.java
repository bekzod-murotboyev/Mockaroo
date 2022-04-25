package uz.pdp.mockaroo.payload.mock.filed;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;
import uz.pdp.mockaroo.util.enums.FieldType;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_ABSENT)
public class Field {
    String name;
    FieldType type;
    Integer min;
    Integer max;
}
