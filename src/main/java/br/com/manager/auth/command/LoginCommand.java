package br.com.manager.auth.command;

import static br.com.manager.util.HttpStatus.BAD_REQUEST;
import static br.com.manager.util.HttpStatus.NO_CONTENT;
import static br.com.manager.util.HttpStatus.UNAUTHORIZED;
import static br.com.manager.util.HttpStatus.INTERNAL_SERVER_ERROR;

import java.util.Optional;

import br.com.manager.core.CommandResult;
import br.com.manager.core.Command;
import br.com.manager.core.ErrorResponse;
import br.com.manager.core.ValidRequest;
import br.com.manager.user.model.User;
import br.com.manager.user.repository.UserRepository;
import br.com.manager.util.Encoder;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LoginCommand implements Command<User> {
	
	private final UserRepository repository;

	@Override
	public CommandResult execute(ValidRequest<User> request) {
		try {
			if( request.isValid() ) {
				User user = request.getData();
				Optional<User> gettedUser = repository.findByEmail(user.getEmail());
				if(matchPassword(user, gettedUser)) {
					return new CommandResult(NO_CONTENT.value(), gettedUser.get());
				}
				return error(UNAUTHORIZED.value(), "Invalid Credentials");
			} else {
				return error(BAD_REQUEST.value(), "Invalid Body Request");
			}
		} catch (Exception e) {
            return error(INTERNAL_SERVER_ERROR.value(), "Internal Server Error");
		}
	}
	
	
	private boolean matchPassword(User actual, Optional<User> expected) {
		try {
			if(expected.isPresent()) {
				String actualPassword = actual.getPassword();
				String expectedPassword = expected.get().getPassword();
				return Encoder.match(actualPassword, expectedPassword);
			}
			return false;
		} catch (IllegalArgumentException e) {
			return false;
		}
	}
	
	
	private CommandResult error(int status, String message) {
        return new CommandResult(status, new ErrorResponse(status, message));
    }

}
