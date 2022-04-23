package uz.pdp.mockaroo.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;
import uz.pdp.mockaroo.payload.request.base.ApiRequest;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ApiRequestSql extends ApiRequest {

    String tableName;

    Boolean createTable;
}
