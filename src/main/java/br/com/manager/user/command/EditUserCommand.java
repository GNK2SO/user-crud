package br.com.manager.user.command;

import static br.com.manager.util.HttpStatus.BAD_REQUEST;
import static br.com.manager.util.HttpStatus.INTERNAL_SERVER_ERROR;
import static br.com.manager.util.HttpStatus.NO_CONTENT;

import br.com.manager.core.CommandResult;
import br.com.manager.core.Command;
import br.com.manager.core.ErrorResponse;
import br.com.manager.core.ValidRequest;
import br.com.manager.user.model.User;
import br.com.manager.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EditUserCommand implements Command<User> {
	
	private final UserRepository repository;

	@Override
	public CommandResult execute(ValidRequest<User> request) {
        try {
            if( request.isValid() ) {
                repository.save(request.getData());
                return new CommandResult(NO_CONTENT.value(), null);
            } else {
                return error(BAD_REQUEST.value(), "Invalid Body Request");
            }
        } catch (Exception e) {
            return error(INTERNAL_SERVER_ERROR.value(), "Internal Server Error");
        }
	}

    private CommandResult error(int status, String message) {
        return new CommandResult(status, new ErrorResponse(status, message));
    }
    
}
