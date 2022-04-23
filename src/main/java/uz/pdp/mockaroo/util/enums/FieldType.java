package uz.pdp.mockaroo.util.enums;

public enum FieldType {
    FIRSTNAME(true),  LASTNAME(true),   USERNAME(true),  FULLNAME(true), TITLE(true), PREFFIX(true), SUFFIX(true), BLOOD_GROUP(true),

    DIGIT(false), NUMBER_BETWEEN(false), RANDOM_NUMBER(false), RANDOM_DIGIT(false), RANDOM_DIGIT_NON_ZERO(false),

    CELL_PHONE(true), PHONE_NUMBER(true), SUBSCRIBER_NUMBER(true),

    COUNTRY(true), CITY(true), BUILDING_NUMBER(true), CITY_NAME(true), COUNTRY_CODE(true), FULL_ADDRESS(true), SECONDARY_ADDRESS(true), STREET_ADDRESS(true), STREET_ADDRESS_NUMBER(true), STREET_NAME(true),

    COLOR_NAME(true),

    DISH(true), FRUIT(true), INGREDIENT(true), MEASURMENT(true), SPICE(true), SUSHI(true), VEGETABLE(true),

    CAPITAL_CITY(true), FLAG(true), LANGUAGE(true), NATIONALITY(true),

    DESCRIPTION(true), TEMPERATURE_CELSIUS(true), TEMPERATURE_CELSIUS_PARAM(true), TEMPERATURE_FAHRENHEIT(true), TEMPERATURE_FAHRENHEIT_PARAM(true), CURRENCY_NAME(true), STRECURRENCY_CODE(true), ROW_NUMBER(true);

    boolean isString;

    FieldType(boolean isString) {
        this.isString = isString;
    }

    public boolean isString() {
        return this.isString;
    }
}
