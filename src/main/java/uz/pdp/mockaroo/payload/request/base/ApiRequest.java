package uz.pdp.mockaroo.payload.request.base;

import lombok.*;
import lombok.experimental.FieldDefaults;
import uz.pdp.mockaroo.payload.mock.filed.Field;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ApiRequest {
    List<Field> fields;

    String format;

    int count;

    String tableName;

    Boolean createTable;
}
