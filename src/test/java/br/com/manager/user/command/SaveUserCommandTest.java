package br.com.manager.user.command;

import static br.com.manager.util.HttpStatus.BAD_REQUEST;
import static br.com.manager.util.HttpStatus.CONFLICT;
import static br.com.manager.util.HttpStatus.CREATED;
import static br.com.manager.util.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import br.com.manager.core.CommandResult;
import br.com.manager.user.builder.UserBuilder;
import br.com.manager.user.command.request.CreateUserRequest;
import br.com.manager.user.repository.UserRepository;

@TestInstance(Lifecycle.PER_CLASS)
public class SaveUserCommandTest {

    private SaveUserCommand command; 
    private UserRepository repository;
    private CreateUserRequest request;

    @BeforeEach
    public void setup() {
        request = mock(CreateUserRequest.class);
        repository = mock(UserRepository.class);
        command = new SaveUserCommand(repository);
    }

    @Test
    @DisplayName("Should return CommandResult with status code CREATED when save user successfully")
    public void shouldReturnCommandResultWithStatusCreatedWhenSaveUserSuccessfully()  {
        when(request.isValid()).thenReturn(Boolean.TRUE);
        when(request.getData()).thenReturn(UserBuilder.build());
        when(repository.findByEmail(anyString())).thenReturn(Optional.empty());

        CommandResult response = command.execute(request);

        assertEquals(CREATED.value(), response.getStatus());
    }

    @Test
    @DisplayName("Should return CommandResult with status code BAD_REQUEST when request is not valid")
    public void shouldReturnCommandResultWithStatusBadRequestWhenRequestIsNotValid()  {
        when(request.isValid()).thenReturn(Boolean.FALSE);
        when(request.getData()).thenReturn(UserBuilder.build());
        when(repository.findByEmail(anyString())).thenReturn(Optional.empty());

        CommandResult response = command.execute(request);

        assertEquals(BAD_REQUEST.value(), response.getStatus());
    }

    @Test
    @DisplayName("Should return CommandResult with status code CONFLICT when request has already used email")
    public void shouldReturnCommandResultWithStatusConflictWhenRequestHasAlreadyUsedEmail()  {
        when(request.isValid()).thenReturn(Boolean.TRUE);
        when(request.getData()).thenReturn(UserBuilder.build());
        when(repository.findByEmail(anyString())).thenReturn(Optional.of(UserBuilder.build()));

        CommandResult response = command.execute(request);

        assertEquals(CONFLICT.value(), response.getStatus());
    }

    @Test
    @DisplayName("Should return CommandResult with status code INTERNAL_SERVER_ERROR when repository throw exception")
    public void shouldReturnCommandResultWithStatusInternalServerErrorWhenRepositoryThrowException()  {
        when(request.isValid()).thenReturn(Boolean.TRUE);
        when(request.getData()).thenReturn(UserBuilder.build());
        when(repository.findByEmail(anyString())).thenThrow(new RuntimeException());

        CommandResult response = command.execute(request);

        assertEquals(INTERNAL_SERVER_ERROR.value(), response.getStatus());
    }
}
