package uz.pdp.mockaroo.util.enums;

import uz.pdp.mockaroo.payload.mock.FormatFieldDTO;

import java.util.List;

public enum Format {
    CSV(
            new FormatFieldDTO(
                    FormatType.SELECTBOX.name(),
                    List.of("Unix(LF)", "Windows(Crlf"),
                    "Line ending"),
            new FormatFieldDTO(
                    FormatType.CHECKBOX.name(),
                    null,
                    "Header"),
            new FormatFieldDTO(
                    FormatType.CHECKBOX.name(),
                    null,
                    "Boom")
    ),
    JSON(
            new FormatFieldDTO(
                    FormatType.CHECKBOX.name(),
                    null,
                    "Array"),
            new FormatFieldDTO(
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

     List<FormatFieldDTO> list;

    Format(FormatFieldDTO... formatFieldDTOS) {
        this.list = List.of(formatFieldDTOS);
    }

}
