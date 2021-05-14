package br.com.manager.auth.command.request;

import javax.servlet.http.HttpServletRequest;

import br.com.manager.core.ValidRequest;
import br.com.manager.user.model.User;

public class LoginRequest implements ValidRequest<User> {

	private final String email;
	private final String password;

	public LoginRequest(HttpServletRequest request) {
		this.email = request.getParameter("email");
		this.password = request.getParameter("password");
	}


    @Override
    public boolean isValid() {
        return !(
            email == null || email.isBlank() ||
            password == null || password.isBlank()
        );
    }

    @Override
    public User getData() {
        return  User.builder()
        	.email(email)
        	.password(password)
    		.build();
    }
    

}
