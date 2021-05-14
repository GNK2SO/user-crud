package br.com.manager.phone.command.request;

import javax.servlet.http.HttpServletRequest;

import br.com.manager.core.ValidRequest;
import lombok.Getter;


public class DeletePhoneRequest implements ValidRequest<Long> {

	@Getter
    private Long userId;
    private Long phoneId;
    
    public DeletePhoneRequest(HttpServletRequest request) {
		this.userId = Long.parseLong(request.getParameter("userId"));
		this.phoneId = Long.parseLong(request.getParameter("phoneId"));
	}

    @Override
    public boolean isValid() {
        return !(
        	userId == null || userId < 1 ||
        	phoneId == null || phoneId < 1
        );
    }

    @Override
    public Long getData() {
        return this.phoneId;
    }
    
}
