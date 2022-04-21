package uz.pdp.mockaroo.payload.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TypeFormat {
    List<String> types;
    List<String> formats;
}
