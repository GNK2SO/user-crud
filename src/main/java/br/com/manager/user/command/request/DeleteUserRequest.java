package br.com.manager.user.command.request;

import javax.servlet.http.HttpServletRequest;

import br.com.manager.core.ValidRequest;


public class DeleteUserRequest implements ValidRequest<Long> {

    private Long id;
    
    public DeleteUserRequest(HttpServletRequest request) {
		this.id = Long.parseLong(request.getParameter("id"));
	}

    @Override
    public boolean isValid() {
        return id != null && id > 0;
    }

    @Override
    public Long getData() {
        return this.id;
    }
    
}
