package uz.pdp.mockaroo.controller;

import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.mockaroo.payload.request.ApiRequestSql;
import uz.pdp.mockaroo.payload.response.ApiResponse;

@RestController
@RequestMapping("/mock")
public class MockController {

    // IT IS ONLY FOR SQL, FOR USING OTHER FORMAT YOU NEED TO CREATE ANOTHER METHOD WITH ANOTHER URL
    @GetMapping("/sql")
    public HttpEntity<ApiResponse> getSql(@RequestBody ApiRequestSql request){

        return null;
    }

}
