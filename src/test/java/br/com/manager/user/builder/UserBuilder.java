package br.com.manager.user.builder;

import java.util.ArrayList;

import br.com.manager.user.model.User;
import br.com.manager.util.Encoder;

public class UserBuilder {
    public static User build() {
        return User.builder()
            .name("User")
            .email("user@email.com")
            .password("P@ssw0rd")
            .phones(new ArrayList<>())
            .build();
    }

    public static User buildSecured() {
        User user = build();
        String password = user.getPassword();
        user.setPassword(Encoder.encode(password));
        return user;
    }
}
