package uz.pdp.mockaroo.util.enums;

import java.util.HashMap;
import java.util.List;

public enum Format {
    CSV,
    JSON,
    TAB_DELIMITED,
    SQL,
    COSSANDRA_CQL,
    FIREBASE,
    INFLUXDB,
    CUSTOM,
    EXCEL,
    XML,
    DBUNIT_XML;

    static class FormatField{
        String type;
        String name;
    }

    List<FormatField> list;

    Format(FormatField... formatFields) {
        this.list = List.of(formatFields);
    }
}
