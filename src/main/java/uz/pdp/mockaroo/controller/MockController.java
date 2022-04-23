package uz.pdp.mockaroo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.mockaroo.payload.request.ApiRequestSql;
import uz.pdp.mockaroo.payload.request.base.ApiRequest;
import uz.pdp.mockaroo.payload.response.ApiResponse;
import uz.pdp.mockaroo.service.MockingService;
import uz.pdp.mockaroo.util.Data;

@RestController
@RequestMapping("/mock")
public record MockController(MockingService mockingService){

    // IT IS ONLY FOR SQL, FOR USING OTHER FORMAT YOU NEED TO CREATE ANOTHER METHOD WITH ANOTHER URL
    @PostMapping("/download")
    public ResponseEntity<Data> download(@RequestBody ApiRequest request) {
        ApiResponse response=mockingService.download(request);
        return ResponseEntity.ok(new Data(response));
    }

    @PostMapping("/check")
    public ResponseEntity<Data> check(@RequestBody ApiRequest request) {
        ApiResponse response=mockingService.check(request);
        return ResponseEntity.ok(new Data(response));
    }


}
