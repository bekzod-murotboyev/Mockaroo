package uz.pdp.mockaroo.config;

import com.github.javafaker.Faker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import uz.pdp.mockaroo.component.MockData;
import uz.pdp.mockaroo.payload.filed.Field;

import static uz.pdp.mockaroo.util.enums.FieldType.*;

@Configuration
public class AppConfig {


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public MockData mockData() {
        Faker faker = Faker.instance();
        return new MockData()
                //name()
                .add(FIRSTNAME, (Field field) -> faker.name().firstName())
                .add(FULLNAME, (Field field) -> faker.name().fullName())
                .add(TITLE, (Field field) -> faker.name().title())
                .add(LASTNAME, (Field field) -> faker.name().lastName())
                .add(USERNAME, (Field field) -> faker.name().username())
                .add(PREFFIX, (Field field) -> faker.name().prefix())
                .add(SUFFIX, (Field field) -> faker.name().suffix())
                .add(BLOOD_GROUP, (Field field) -> faker.name().bloodGroup())

                //Number
                .add(DIGIT, (Field field) -> faker.number().digit())
                //.add(USERNAME, (Field field) -> faker.number().digits())
                .add(NUMBER_BETWEEN, (Field field) -> String.valueOf(faker.number().numberBetween(field.getMin(), field.getMax())))
                .add(RANDOM_NUMBER, (Field field) -> String.valueOf(faker.number().randomNumber()))
                .add(RANDOM_DIGIT, (Field field) -> String.valueOf(faker.number().randomDigit()))
                .add(RANDOM_DIGIT_NON_ZERO, (Field field) -> String.valueOf(faker.number().randomDigitNotZero()))
               // .add(USERNAME, (Field field) -> faker.number().randomDouble())

                //PhoneNumber
                .add(CELL_PHONE, (Field field) -> faker.phoneNumber().cellPhone())
                .add(PHONE_NUMBER, (Field field) -> faker.phoneNumber().phoneNumber())
                .add(SUBSCRIBER_NUMBER, (Field field) -> faker.phoneNumber().subscriberNumber())

                //Address
                .add(COUNTRY, (Field field) -> faker.address().country())
                .add(CITY, (Field field) -> faker.address().city())
                .add(BUILDING_NUMBER, (Field field) -> faker.address().buildingNumber())
                .add(CITY_NAME, (Field field) -> faker.address().cityName())
                .add(COUNTRY_CODE, (Field field) -> faker.address().countryCode())
                .add(FULL_ADDRESS, (Field field) -> faker.address().fullAddress())
                .add(SECONDARY_ADDRESS, (Field field) -> faker.address().secondaryAddress())
                .add(STREET_ADDRESS, (Field field) -> faker.address().streetAddress())
                .add(STREET_ADDRESS_NUMBER, (Field field) -> faker.address().streetAddressNumber())
                .add(STREET_NAME, (Field field) -> faker.address().streetName())

                //COLOR
                .add(COLOR_NAME, (Field field) -> faker.color().name())

                //Food
                .add(DISH, (Field field) -> faker.food().dish())
                .add(FRUIT, (Field field) -> faker.food().fruit())
                .add(INGREDIENT, (Field field) -> faker.food().ingredient())
                .add(MEASURMENT, (Field field) -> faker.food().measurement())
                .add(SPICE, (Field field) -> faker.food().spice())
                .add(SUSHI, (Field field) -> faker.food().sushi())
                .add(VEGETABLE, (Field field) -> faker.food().vegetable())

                //Nation
                .add(CAPITAL_CITY, (Field field) -> faker.nation().capitalCity())
                .add(FLAG, (Field field) -> faker.nation().flag())
                .add(LANGUAGE, (Field field) -> faker.nation().language())
                .add(NATIONALITY, (Field field) -> faker.nation().nationality())

                //Weather
                .add(DESCRIPTION, (Field field) -> faker.weather().description())
                .add(TEMPERATURE_CELSIUS, (Field field) -> faker.weather().temperatureCelsius())
                .add(TEMPERATURE_CELSIUS_PARAM, (Field field) -> faker.weather().temperatureCelsius(field.getMin(), field.getMax()))
                .add(TEMPERATURE_FAHRENHEIT, (Field field) -> faker.weather().temperatureFahrenheit())
                .add(TEMPERATURE_FAHRENHEIT_PARAM, (Field field) -> faker.weather().temperatureFahrenheit(field.getMin(), field.getMax()))

                //Currency
                .add(CURRENCY_NAME, (Field field) -> faker.currency().name())
                .add(STRECURRENCY_CODE, (Field field) -> faker.currency().code())


                .add(ROW_NUMBER, (Field field) -> faker.idNumber().ssnValid())




                ;
    }

}
