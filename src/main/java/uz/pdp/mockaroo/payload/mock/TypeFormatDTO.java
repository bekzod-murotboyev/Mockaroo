package uz.pdp.mockaroo.payload.mock;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TypeFormatDTO {
    List<String> types;
    List<String> formats;
}
