package br.com.manager.phone.controller;

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
import br.com.manager.phone.command.DeletePhoneCommand;
import br.com.manager.phone.command.SavePhoneCommand;
import br.com.manager.phone.command.request.CreatePhoneRequest;
import br.com.manager.phone.command.request.DeletePhoneRequest;
import br.com.manager.phone.model.Phone;
import br.com.manager.phone.repository.PhoneRepository;
import br.com.manager.util.CharacterEncoding;

@WebServlet(urlPatterns = "/phones", name = "PhoneController")
public class PhoneController extends HttpServlet {
	
	private static final long serialVersionUID = 6977555091779440235L;
	
	private final PhoneRepository phoneRepository = new PhoneRepository();
    private final Command<Phone> commandSavePhone = new SavePhoneCommand(phoneRepository);
    private final Command<Long> commandDeletePhone = new DeletePhoneCommand(phoneRepository);

	@Override
    protected void doPost(
        HttpServletRequest servletRequest, 
        HttpServletResponse response
    ) throws ServletException, IOException {
    	CreatePhoneRequest request = new CreatePhoneRequest(servletRequest);
        CommandResult result = commandSavePhone.execute(request);
        send(response, result);
    }
	
	@Override
    protected void doDelete(
        HttpServletRequest servletRequest, 
        HttpServletResponse response
    ) throws ServletException, IOException {
    	DeletePhoneRequest request = new DeletePhoneRequest(servletRequest);
        CommandResult result = commandDeletePhone.execute(request);
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
