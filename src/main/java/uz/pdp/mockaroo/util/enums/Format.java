package uz.pdp.mockaroo.util.enums;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.mockaroo.payload.dto.FormatField;

import java.util.List;

public enum Format {
    CSV(
            new FormatField(
                    FormatType.SELECTBOX.name(),
                    List.of("Unix(LF)", "Windows(Crlf"),
                    "Line ending"),
            new FormatField(
                    FormatType.CHECKBOX.name(),
                    null,
                    "Header"),
            new FormatField(
                    FormatType.CHECKBOX.name(),
                    null,
                    "Boom")
    ),
    JSON(
            new FormatField(
                    FormatType.CHECKBOX.name(),
                    null,
                    "Array"),
            new FormatField(
                    FormatType.CHECKBOX.name(),
                    null,
                    "Include null values")),
    TAB_DELIMITED,
    SQL,
    COSSANDRA_CQL,
    FIREBASE,
    INFLUXDB,
    CUSTOM,
    EXCEL,
    XML,
    DBUNIT_XML;

     List<FormatField> list;

    Format(FormatField... formatFields) {
        this.list = List.of(formatFields);
    }

}
