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
                .add(FIRSTNAME, (Field field) -> faker.name().firstName())
                .add(LASTNAME, (Field field) -> faker.name().lastName())
                .add(USERNAME, (Field field) -> faker.name().username());
    }

}
