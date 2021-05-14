package br.com.manager.user.command.request;

import javax.servlet.http.HttpServletRequest;

import br.com.manager.core.ValidRequest;
import br.com.manager.user.model.User;
import br.com.manager.util.Encoder;

public class CreateUserRequest implements ValidRequest<User> {
	
	private final String name;
	private final String email;
	private final String password;

	public CreateUserRequest(HttpServletRequest request) {
		this.name = request.getParameter("name");
		this.email = request.getParameter("email");
		this.password = request.getParameter("password");
	}


    @Override
    public boolean isValid() {
        return !(
            name == null || name.isBlank() ||
            email == null || email.isBlank() ||
            password == null || password.isBlank()
        );
    }

    @Override
    public User getData() {
        return  User.builder()
        	.name(name)
        	.email(email)
        	.password(encryptedPassword())
    		.build();
    }
    
    public String encryptedPassword() {
    	return Encoder.encode(password);
    }
}
