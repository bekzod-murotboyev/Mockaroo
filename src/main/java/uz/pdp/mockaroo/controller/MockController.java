package uz.pdp.mockaroo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.mockaroo.payload.request.ApiRequestSql;
import uz.pdp.mockaroo.payload.response.ApiResponse;
import uz.pdp.mockaroo.service.MockingService;

@RestController
@RequestMapping("/mock")
@RequiredArgsConstructor
public class MockController {

    private final MockingService mockingService;
    @PostMapping(path = "/download")
    public ResponseEntity<ApiResponse<String>> downloadFile(@RequestBody ApiRequestSql request) {
        ApiResponse<String> response = mockingService.getData(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/check")
    public ResponseEntity<ApiResponse<String>> check(@RequestBody ApiRequestSql request) {
        ApiResponse<String> response = mockingService.getData(request);
        return ResponseEntity.ok(response);
    }


}
