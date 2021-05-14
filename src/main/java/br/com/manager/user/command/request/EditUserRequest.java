package br.com.manager.user.command.request;

import javax.servlet.http.HttpServletRequest;

import br.com.manager.core.ValidRequest;
import br.com.manager.user.model.User;

public class EditUserRequest implements ValidRequest<User> {

    private final Long id;
    private final String name;
    
    public EditUserRequest(HttpServletRequest request) {
		this.id = Long.parseLong(request.getParameter("id"));
		this.name = request.getParameter("name");
	}

    @Override
    public boolean isValid() {
        return !(
            id == null || id < 1 ||
            name == null || name.isBlank()
        );
    }

    @Override
    public User getData() {
        return  User.builder()
        	.id(id)
        	.name(name)
    		.build();
    }
    
}
