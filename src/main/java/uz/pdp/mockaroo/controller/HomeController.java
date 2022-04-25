package uz.pdp.mockaroo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.mockaroo.payload.mock.TypeFormatDTO;
import uz.pdp.mockaroo.payload.response.ApiResponse;
import uz.pdp.mockaroo.util.enums.FieldType;
import uz.pdp.mockaroo.util.enums.Format;

import java.util.Arrays;

@RestController
@RequestMapping("/home")
public class HomeController {

    @GetMapping("/init")
    public ResponseEntity<ApiResponse<TypeFormatDTO>> init() {
        return ResponseEntity.ok(
                new ApiResponse<>("Initial data successfully send",
                        new TypeFormatDTO(Arrays.stream(FieldType.values()).map(FieldType::name).toList(),
                                Arrays.stream(Format.values()).map(Format::name).toList())));
    }


}
