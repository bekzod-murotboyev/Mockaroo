package uz.pdp.mockaroo.service;

import lombok.SneakyThrows;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import uz.pdp.mockaroo.component.MockData;
import uz.pdp.mockaroo.payload.request.ApiRequestSql;
import uz.pdp.mockaroo.payload.request.base.ApiRequest;
import uz.pdp.mockaroo.payload.response.ApiResponse;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.stream.IntStream;

@Service
public record MockingService(MockData mockData) {

    private static final String DIRECTORY = "../file";
    private static final String FILE = "file.txt";

    public ApiResponse<String> getData(ApiRequestSql request) {
        String result = switch (request.getFormat()) {
            case "SQL" -> dataSQl(request);
            case "CSV" -> dataCSV(request);
            case "JSON" -> dataJSON(request);
            default -> "Error";
        };

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


    public File checkFile(File file) throws IOException {
        file.mkdir();
        file = new File(file, FILE);
        file.createNewFile();
        return file;
    }
}
