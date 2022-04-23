package uz.pdp.mockaroo.service;

import org.springframework.stereotype.Service;
import uz.pdp.mockaroo.component.MockData;
import uz.pdp.mockaroo.payload.request.ApiRequestSql;
import uz.pdp.mockaroo.payload.request.base.ApiRequest;
import uz.pdp.mockaroo.payload.response.ApiResponse;

import java.util.stream.IntStream;

@Service
public record MockingService(MockData mockData) {

    public ApiResponse download(ApiRequest request) {
        String result = switch (request.getFormat()) {
//            case "SQL" -> downloadSQl(request);
//            case "CSV" -> downloadCSV(request);
//            case "JSON" -> downloadJSON(request);
            default -> "Error";
        };

        return new ApiResponse("Success", true, result);
    }

    public ApiResponse check(ApiRequestSql request) {
        String result = switch (request.getFormat()) {
            case "SQL" -> dataSQl(request);
            case "CSV" -> dataCSV(request);
            case "JSON" -> dataJSON(request);
            default -> "Error";
        };

        return new ApiResponse("Success", true, result);
    }

    private String dataSQl(ApiRequestSql request) {
        StringBuffer result = new StringBuffer("INSERT INTO " + request.getTableName() + '(');
        request.getFields().forEach(field -> result.append(field.getName()).append(','));
        result.setCharAt(result.length() - 1, ')');
        result.append(" \nVALUES\n");


        IntStream.range(0, request.getCount()).forEach(i -> {
            result.append('(');
            request.getFields().forEach(
                    field -> {
                        if (field.getType().isString()) result.append("'").append(mockData.get(field)).append("'").append(',');
                        else result.append(mockData.get(field)).append(',');
                    });
            result.insert(result.length() - 1, ')').append('\n');
            result.append("\n");
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
        stringBuffer.deleteCharAt(stringBuffer.length() - 1);
        stringBuffer.append("\n");

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
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[");

        IntStream.range(0, request.getCount()).forEach(
                i -> {
                    stringBuffer.append("{\n");
                    request.getFields().forEach(
                            field -> {
                                stringBuffer.append("\"").append(field.getName()).append("\":");
                                if(field.getType().isString())
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
        stringBuffer.deleteCharAt(stringBuffer.length() - 1);
        stringBuffer.append("]");
        return stringBuffer.toString();
    }

}
