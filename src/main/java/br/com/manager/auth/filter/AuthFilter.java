package br.com.manager.auth.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

@WebFilter(
	urlPatterns = { "/users/*", "/phones/*" }, 
	servletNames = { "UserController", "PhoneController" }
)
public class AuthFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
	
		HttpServletRequest servletRequest = (HttpServletRequest) request;
		
		String email = (String) servletRequest.getSession().getAttribute("email");
		String path = servletRequest.getServletPath();
		String method = servletRequest.getMethod();
		
		if(email == null && !(path.equals("/users") && method.equals("POST"))) {
			servletRequest.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
		} else {			
			chain.doFilter(request, response);
		}
		
	}

}
