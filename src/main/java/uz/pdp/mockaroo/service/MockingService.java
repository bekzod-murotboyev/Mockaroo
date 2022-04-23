package uz.pdp.mockaroo.service;

import org.springframework.stereotype.Service;
import uz.pdp.mockaroo.component.MockData;
import uz.pdp.mockaroo.payload.request.ApiRequestSql;
import uz.pdp.mockaroo.payload.response.ApiResponse;

import java.util.stream.IntStream;

@Service
public record MockingService(MockData mockData) {

    public ApiResponse download(ApiRequestSql request) {
        String result = switch (request.getFormat()) {
            case "SQL" -> downloadSQl(request);
            case "CSV" -> downloadCSV(request);
            default -> "Error";
        };

        return new ApiResponse("Success", true, result);
    }

    private String downloadSQl(ApiRequestSql request) {
        StringBuffer result = new StringBuffer("INSERT INTO " + request.getTableName() + '(');
        request.getFields().forEach(field -> result.append(field.getName()).append(','));
        result.setCharAt(result.length() - 1, ')');
        result.append(" \nVALUES");


        IntStream.range(0, request.getCount()).forEach(i -> {
            result.append('(');
            request.getFields().forEach(field -> result.append(mockData.get(field)).append(','));
            result.insert(result.length() - 1, ')').append('\n');
        });
        result.setLength(result.length() - 1);
        return result.toString();
    }

    public String downloadCSV(ApiRequestSql request) {
        StringBuffer stringBuffer = new StringBuffer();
        request.getFields().forEach(
                field -> {
                    stringBuffer.append(field.getName());
                    stringBuffer.append(",");
                }
        );
        stringBuffer.deleteCharAt(stringBuffer.length() - 1);
        stringBuffer.append("\n");

        IntStream.range(0, request.getCount()).forEach(
                i -> {
                    request.getFields().forEach(
                            field -> {
                                stringBuffer.append(mockData.get(field));
                                stringBuffer.append(",");
                            }
                    );
                    stringBuffer.deleteCharAt(stringBuffer.length() - 1);
                    stringBuffer.append("\n");
                }
        );

        return stringBuffer.toString();
    }

    public ApiResponse check(ApiRequestSql request) {
        return null;
    }

}
