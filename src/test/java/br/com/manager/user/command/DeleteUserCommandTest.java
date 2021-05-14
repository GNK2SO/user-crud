package br.com.manager.user.command;

import static br.com.manager.util.HttpStatus.BAD_REQUEST;
import static br.com.manager.util.HttpStatus.NO_CONTENT;
import static br.com.manager.util.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import br.com.manager.core.CommandResult;
import br.com.manager.user.command.request.DeleteUserRequest;
import br.com.manager.user.repository.UserRepository;

@TestInstance(Lifecycle.PER_CLASS)
public class DeleteUserCommandTest {
     
    private DeleteUserCommand command; 
    private DeleteUserRequest request;
    private UserRepository repository;

    @BeforeEach
    public void setup() {
        request = mock(DeleteUserRequest.class);
        repository = mock(UserRepository.class);
        command = new DeleteUserCommand(repository);
    }


    @Test
    @DisplayName("Should return CommandResult with status code NO_CONTENT when delete user successfully")
    public void shouldReturnCommandResultWithStatusNoContentWhenDeleteUserSuccessfully()  {
        when(request.isValid()).thenReturn(Boolean.TRUE);
        CommandResult response = command.execute(request);
        assertEquals(NO_CONTENT.value(), response.getStatus());
    }


    @Test
    @DisplayName("Should return CommandResult with status code BAD_REQUEST when request is not valid")
    public void shouldReturnCommandResultWithStatusBadRequestWhenRequestIsNotValid()  {
        when(request.isValid()).thenReturn(Boolean.FALSE);
        CommandResult response = command.execute(request);
        assertEquals(BAD_REQUEST.value(), response.getStatus());
    }


    @Test
    @DisplayName("Should return CommandResult with status code INTERNAL_SERVER_ERROR when repository throw exception")
    public void shouldReturnCommandResultWithStatusInternalServerErrorWhenRepositoryThrowException()  {
        when(request.isValid()).thenReturn(Boolean.TRUE);
        doThrow(new RuntimeException()).when(repository).deleteById(anyLong());

        CommandResult response = command.execute(request);

        assertEquals(INTERNAL_SERVER_ERROR.value(), response.getStatus());
    }
}
