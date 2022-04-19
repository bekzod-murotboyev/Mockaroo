package uz.pdp.mockaroo.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.javafaker.Faker;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.apache.tomcat.jni.Local;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import uz.pdp.mockaroo.entity.base.BaseEntity;

import javax.persistence.Entity;
import java.util.Arrays;
import java.util.Collection;
import java.util.Locale;
import java.util.Random;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity(name = "users")
public class User extends BaseEntity<Long> implements UserDetails {

    String email;
    String password;


    boolean accountNonExpired = true;
    boolean accountNonLocked = true;
    boolean credentialsNonExpired = true;
    boolean enabled = true;

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

}
