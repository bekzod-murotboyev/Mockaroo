package uz.pdp.mockaroo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.mockaroo.payload.dto.TypeFormat;
import uz.pdp.mockaroo.payload.response.ApiResponse;
import uz.pdp.mockaroo.util.enums.FieldType;
import uz.pdp.mockaroo.util.enums.Format;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/home")
public class HomeController {

    @GetMapping("/init")
    public ResponseEntity<?> init() {
        return ResponseEntity.ok(
                new ApiResponse("Initialize data",
                        true,
                        new TypeFormat(Arrays.stream(FieldType.values()).map(fieldType ->
                                               fieldType.name().contains("_") ? fieldType.name().replace("_", " ") : fieldType.name()
                                        ).toList(),
                                        Arrays.stream(FieldType.values()).map(fieldType ->
                                                fieldType.name().contains("_") ? fieldType.name().replace("_", " ") : fieldType.name()
                                        ).toList())));
    }



}
