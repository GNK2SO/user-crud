package br.com.manager.user.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.manager.core.CommandResult;
import br.com.manager.core.Command;
import br.com.manager.user.command.DeleteUserCommand;
import br.com.manager.user.command.EditUserCommand;
import br.com.manager.user.command.SaveUserCommand;
import br.com.manager.user.command.request.CreateUserRequest;
import br.com.manager.user.command.request.DeleteUserRequest;
import br.com.manager.user.command.request.EditUserRequest;
import br.com.manager.user.model.User;
import br.com.manager.user.repository.UserRepository;
import br.com.manager.util.CharacterEncoding;

@WebServlet(urlPatterns = "/users", name = "UserController")
public class UserController extends HttpServlet {

	private static final long serialVersionUID = 6661016739224225991L;

    private final UserRepository repository = new UserRepository();
    private final Command<User> commandSaveUser = new SaveUserCommand(repository);
    private final Command<User> commandEditUser = new EditUserCommand(repository);
    private final Command<Long> commandDeleteUser = new DeleteUserCommand(repository);
    
    @Override
    protected void doGet(
        HttpServletRequest req, 
        HttpServletResponse resp
    ) throws ServletException, IOException {
    	req.setAttribute("users", repository.findAll());
        req.getRequestDispatcher("/WEB-INF/users/list.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(
        HttpServletRequest servletRequest, 
        HttpServletResponse response
    ) throws ServletException, IOException {
    	CreateUserRequest request = new CreateUserRequest(servletRequest);
        CommandResult result = commandSaveUser.execute(request);
        send(response, result);
    }

    @Override
    protected void doPut(
        HttpServletRequest servletRequest, 
        HttpServletResponse response
    ) throws ServletException, IOException {
    	EditUserRequest request = new EditUserRequest(servletRequest);
        CommandResult result = commandEditUser.execute(request);
        send(response, result);
    }
    
    @Override
    protected void doDelete(
        HttpServletRequest servletRequest, 
        HttpServletResponse response
    ) throws ServletException, IOException {
    	DeleteUserRequest request = new DeleteUserRequest(servletRequest);
        CommandResult result = commandDeleteUser.execute(request);
        send(response, result);
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
