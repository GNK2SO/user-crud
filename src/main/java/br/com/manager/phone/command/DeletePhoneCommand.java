package br.com.manager.phone.command;

import br.com.manager.core.CommandResult;

import static br.com.manager.util.HttpStatus.BAD_REQUEST;
import static br.com.manager.util.HttpStatus.NO_CONTENT;
import static br.com.manager.util.HttpStatus.INTERNAL_SERVER_ERROR;

import br.com.manager.core.Command;
import br.com.manager.core.ErrorResponse;
import br.com.manager.core.ValidRequest;
import br.com.manager.phone.command.request.DeletePhoneRequest;
import br.com.manager.phone.repository.PhoneRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DeletePhoneCommand implements Command<Long> {

    private final PhoneRepository repository;

    @Override
    public CommandResult execute(ValidRequest<Long> request) {
        
         try {
            if( request.isValid() ) {
                Long userID = ((DeletePhoneRequest) request).getUserId();
                repository.deleteById(request.getData(), userID);
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
