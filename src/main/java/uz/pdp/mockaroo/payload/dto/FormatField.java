package uz.pdp.mockaroo.payload.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public  class FormatField{
    String type;
    List<String> typeValues;
    String name;
}