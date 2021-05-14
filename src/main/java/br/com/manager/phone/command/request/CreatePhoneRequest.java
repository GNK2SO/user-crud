package br.com.manager.phone.command.request;

import javax.servlet.http.HttpServletRequest;

import br.com.manager.core.ValidRequest;
import br.com.manager.phone.model.Phone;
import br.com.manager.phone.model.Phone.PhoneType;
import lombok.Getter;

public class CreatePhoneRequest implements ValidRequest<Phone> {
	
	@Getter
	private final Long id;
	private final Integer ddd;
	private final String number;
	private final PhoneType type;

	public CreatePhoneRequest(HttpServletRequest request) {
		this.id = Long.parseLong(request.getParameter("id"));
		this.ddd = Integer.parseInt(request.getParameter("ddd"));
		this.number = request.getParameter("number");
		this.type = PhoneType.valueOf(request.getParameter("type"));
	}


    @Override
    public boolean isValid() {
        return !(
    		ddd == null ||
    		id == null || id < 1 ||
			number == null || number.isBlank()
        );
    }

    @Override
    public Phone getData() {
        return Phone.builder()
        	.ddd(ddd)
        	.number(number)
        	.type(type)
    		.build();
    }
    
}
