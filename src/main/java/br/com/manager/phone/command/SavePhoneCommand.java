package br.com.manager.phone.command;

import br.com.manager.core.CommandResult;

import static br.com.manager.util.HttpStatus.BAD_REQUEST;
import static br.com.manager.util.HttpStatus.CREATED;
import static br.com.manager.util.HttpStatus.INTERNAL_SERVER_ERROR;

import br.com.manager.core.Command;
import br.com.manager.core.ErrorResponse;
import br.com.manager.core.ValidRequest;
import br.com.manager.phone.command.request.CreatePhoneRequest;
import br.com.manager.phone.model.Phone;
import br.com.manager.phone.repository.PhoneRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SavePhoneCommand implements Command<Phone> {
	
	private final PhoneRepository phoneRepository;

	@Override
	public CommandResult execute(ValidRequest<Phone> request) {
        try {
            if( request.isValid() ) {
                Long userID = ((CreatePhoneRequest) request).getId();
                phoneRepository.addPhone(userID,  request.getData());
                return new CommandResult(CREATED.value(), null);
            } else {
                return error(BAD_REQUEST.value(), "Invalid Body Request");
            }
        } catch(Exception e) {
            return error(INTERNAL_SERVER_ERROR.value(), "Internal Server Error");
        }
	}

    private CommandResult error(int status, String message) {
        return new CommandResult(status, new ErrorResponse(status, message));
    }

}
