package br.com.manager.user.command;

import static br.com.manager.util.HttpStatus.BAD_REQUEST;
import static br.com.manager.util.HttpStatus.CONFLICT;
import static br.com.manager.util.HttpStatus.CREATED;
import static br.com.manager.util.HttpStatus.INTERNAL_SERVER_ERROR;

import java.util.Optional;

import br.com.manager.core.CommandResult;
import br.com.manager.core.Command;
import br.com.manager.core.ErrorResponse;
import br.com.manager.core.ValidRequest;
import br.com.manager.user.model.User;
import br.com.manager.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SaveUserCommand implements Command<User> {
	
	private final UserRepository repository;

	@Override
	public CommandResult execute(ValidRequest<User> request) {
        try {
            if( request.isValid() ) {
                User user = request.getData();
                Optional<User> userOptinal = repository.findByEmail(user.getEmail());
                if(userOptinal.isEmpty()) {
                    User savedUser = repository.save(user);
                    return new CommandResult(CREATED.value(), savedUser);
                }
                return error(CONFLICT.value(), "Email already used");
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
