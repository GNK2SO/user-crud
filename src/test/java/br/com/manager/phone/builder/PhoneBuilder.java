package br.com.manager.phone.builder;

import br.com.manager.phone.model.Phone;
import br.com.manager.phone.model.Phone.PhoneType;

public class PhoneBuilder {

    public static Phone build() {
        return Phone.builder()
            .ddd(81)
            .number("99999-9999")
            .type(PhoneType.PERSONAL)
            .build();
    }
    
}
