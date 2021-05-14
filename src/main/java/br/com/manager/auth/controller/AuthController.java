package br.com.manager.auth.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.manager.auth.command.LoginCommand;
import br.com.manager.auth.command.request.LoginRequest;
import br.com.manager.core.CommandResult;
import br.com.manager.core.Command;
import br.com.manager.user.model.User;
import br.com.manager.user.repository.UserRepository;
import br.com.manager.util.CharacterEncoding;
import br.com.manager.util.HttpStatus;

@WebServlet("/auth")
public class AuthController extends HttpServlet {
	
	private static final long serialVersionUID = -7931399543639508796L;
	
	private final UserRepository repository = new UserRepository();
	private final Command<User> commandLogin = new LoginCommand(repository);

	@Override
	protected void doPost(
		HttpServletRequest servletRequest, 
		HttpServletResponse servletResponse
	) throws ServletException, IOException {
		LoginRequest request = new LoginRequest(servletRequest);
		CommandResult result = commandLogin.execute(request);
		if(result.getStatus() == HttpStatus.NO_CONTENT.value()) {
			User user = (User) result.getData();
			HttpSession session = servletRequest.getSession();
			session.setAttribute("email", user.getEmail());
			servletResponse.setStatus(result.getStatus());
			servletResponse.sendRedirect("/manager/users");
		} else {
	        send(servletResponse, result);
		}
	}
	
	@Override
	protected void doDelete(
		HttpServletRequest servletRequest, 
		HttpServletResponse servletResponse
	) throws ServletException, IOException {
		HttpSession session = servletRequest.getSession();
		session.removeAttribute("email");
		session.invalidate();
	}
	
	private void send(HttpServletResponse resp, CommandResult response) throws IOException {
        resp.setCharacterEncoding(CharacterEncoding.UTF_8.value());
        resp.setContentType(MediaType.APPLICATION_JSON);
        resp.setStatus(response.getStatus());
        resp.getWriter().print(serialize(response.getData()));
    }


    private String serialize(Object obj) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(obj);
    }
}
