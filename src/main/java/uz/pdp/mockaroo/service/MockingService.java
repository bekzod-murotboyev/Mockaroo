package uz.pdp.mockaroo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.mockaroo.component.MockData;
import uz.pdp.mockaroo.payload.request.ApiRequestSql;

@Service
@RequiredArgsConstructor
public class MockingService {

    private final MockData mockData;


    public String getSql(ApiRequestSql request) {
         request.
                getFields()
                .stream()
                .parallel()
                .map(mockData::get).toList();
         return null;
    }

}
