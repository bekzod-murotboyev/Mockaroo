package uz.pdp.mockaroo.payload.filed;

import lombok.*;
import lombok.experimental.FieldDefaults;
import uz.pdp.mockaroo.util.enums.FieldType;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Field {
    String name;
    FieldType type;
    Integer min;
    Integer max;
}
