package uz.pdp.mockaroo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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


    // IT IS ONLY FOR SQL, FOR USING OTHER FORMAT YOU NEED TO CREATE ANOTHER METHOD WITH ANOTHER URL
    @GetMapping("/sql")
    public ResponseEntity<ApiResponse> getSql(@RequestBody ApiRequestSql request){
        return ResponseEntity.ok(new ApiResponse("Sql", true, mockingService.getSql(request)));
    }

}
