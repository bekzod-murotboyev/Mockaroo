package uz.pdp.mockaroo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.mockaroo.component.MockData;
import uz.pdp.mockaroo.payload.request.ApiRequestSql;

import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MockingService {

    private final MockData mockData;

    public StringBuffer getSql(ApiRequestSql request) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < request.getCount(); i++) {
            String insertQuery = "Insert into " + request.getTableName() + " ";
            List<String> strings = request.
                    getFields()
                    .stream()
                    .parallel()
                    .map(mockData::get).toList();

            String fields = "(";
            for (int j = 0; j < request.getFields().size(); j++) {
                if (i == request.getFields().size() - 1) fields = fields + request.getFields().get(i) + ")";
                else fields = fields + request.getFields().get(i) + ",";
            }

            String values = "(";

            for (int j = 0; j < strings.size(); j++) {
                if (i == strings.size() - 1) values = values + strings.get(i) + ") ";
                else values = values + strings.get(i) + ",";
            }

            stringBuffer.append(insertQuery + fields + " values " + values + "\n");
        }

        return stringBuffer;
    }


    public StringBuffer getCsv(ApiRequestSql request) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < request.getCount(); i++) {
            String csv = "";
            List<String> strings = request.
                    getFields()
                    .stream()
                    .parallel()
                    .map(mockData::get).toList();

            for (int j = 0; j < strings.size(); j++) {
                if (i == strings.size() - 1) csv = csv + strings.get(i);
                else csv = csv + strings.get(i) + ",";
            }

            stringBuffer.append(csv + "\n");
        }

        return stringBuffer;
    }

    public StringBuffer getJson(ApiRequestSql request) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[");
        for (int i = 0; i < request.getCount(); i++) {
            String json = "{";

            for (int j = 0; j < request.getFields().size(); j++) {
                if (j == request.getFields().size() - 1)
                    json = json + "\n"
                            + "\"" + request.getFields().get(i).getName() + "\":" + mockData.get(request.getFields().get(i));
                else
                    json = json + "\n"
                            + "\"" + request.getFields().get(i).getName() + "\":" + mockData.get(request.getFields().get(i)) + ",";
            }
            if (i ==  request.getCount()-1){
                json = json+"}";
            }else {
                json = json+"},";
            }
            stringBuffer.append(json);
        }
        stringBuffer.append("]");
        return stringBuffer;
    }
}
