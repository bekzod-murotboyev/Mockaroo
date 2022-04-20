package uz.pdp.mockaroo.component;

import uz.pdp.mockaroo.payload.filed.Field;
import uz.pdp.mockaroo.util.enums.FieldType;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class MockData {

    private Map<FieldType, Function<Field, String>> map;

    public MockData() {
        this.map = new HashMap<>();
    }

    public String get(Field field) {
        return map.get(field.getType()).apply(field);
    }


    public MockData add(FieldType type, Function<Field, String> function) {
        map.put(type, function);
        return this;
    }

}
