package uz.pdp.mockaroo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.mockaroo.component.MockData;
import uz.pdp.mockaroo.payload.request.ApiRequestSql;
import uz.pdp.mockaroo.payload.request.base.ApiRequest;
import uz.pdp.mockaroo.payload.response.ApiResponse;

import java.io.File;
import java.io.IOException;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class MockingService {

    private final MockData mockData;

    public ApiResponse<String> getData(ApiRequestSql request) {
        String result;
        switch (request.getFormat()) {
            case "SQL":
                result = dataSQl(request);
                break;
            case "CSV":
                result = dataCSV(request);
                break;
            case "JSON":
                result = dataJSON(request);
                break;
            default:
                result = "Error";
        }

        return new ApiResponse<>("Success", result);
    }

    private String dataSQl(ApiRequestSql request) {
        StringBuilder result = new StringBuilder("INSERT INTO " + request.getTableName() + '(');
        request.getFields().forEach(field -> result.append(field.getName()).append(','));
        result.setCharAt(result.length() - 1, ')');
        result.append(" \nVALUES\t");


        IntStream.range(0, request.getCount()).forEach(i -> {
            result.append('(');
            request.getFields().forEach(
                    field -> {
                        if (field.getType().isString())
                            result.append("'").append(mockData.get(field)).append("'").append(',');
                        else result.append(mockData.get(field)).append(',');
                    });
            result.insert(result.length() - 1, ')').append('\n');
        });
        return result.toString();
    }

    public String dataCSV(ApiRequest request) {
        StringBuffer stringBuffer = new StringBuffer();
        request.getFields().forEach(
                field -> {
                    stringBuffer.append(field.getName());
                    stringBuffer.append(",");
                }
        );
        stringBuffer.setCharAt(stringBuffer.length() - 1, '\n');

        IntStream.range(0, request.getCount()).forEach(
                i -> {
                    request.getFields().forEach(
                            field -> stringBuffer.append(mockData.get(field)).append(",")
                    );
                    stringBuffer.deleteCharAt(stringBuffer.length() - 1);
                    stringBuffer.append("\n");
                }
        );

        return stringBuffer.toString();
    }


    public String dataJSON(ApiRequest request) {
        StringBuffer stringBuffer = new StringBuffer("[");

        IntStream.range(0, request.getCount()).forEach(
                i -> {
                    stringBuffer.append("{\n");
                    request.getFields().forEach(
                            field -> {
                                stringBuffer.append("\"").append(field.getName()).append("\":");
                                if (field.getType().isString())
                                    stringBuffer.append("\"").append(mockData.get(field)).append("\"");
                                else
                                    stringBuffer.append(mockData.get(field));
                                stringBuffer.append(",\n");
                            }
                    );
                    stringBuffer.deleteCharAt(stringBuffer.length() - 1);
                    stringBuffer.append("\n},");
                }
        );
        stringBuffer.setCharAt(stringBuffer.length() - 1, ']');
        return stringBuffer.toString();
    }

}
