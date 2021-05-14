package br.com.manager.auth.command;

import static br.com.manager.util.HttpStatus.BAD_REQUEST;
import static br.com.manager.util.HttpStatus.NO_CONTENT;
import static br.com.manager.util.HttpStatus.UNAUTHORIZED;
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

import br.com.manager.auth.command.request.LoginRequest;
import br.com.manager.core.CommandResult;
import br.com.manager.user.builder.UserBuilder;
import br.com.manager.user.repository.UserRepository;

@TestInstance(Lifecycle.PER_CLASS)
public class LoginCommandTest {

    private LoginCommand command; 
    private LoginRequest request;
    private UserRepository repository;

    @BeforeEach
    public void setup() {
        request = mock(LoginRequest.class);
        repository = mock(UserRepository.class);
        command = new LoginCommand(repository);
    }

    @Test
    @DisplayName("Should return CommandResult with status code NO_CONTENT when user authenticate successfully")
    public void shouldReturnCommandResultWithStatusNoContentWhenUserAuthenticateSuccessfully()  {
        when(request.isValid()).thenReturn(Boolean.TRUE);
        when(request.getData()).thenReturn(UserBuilder.build());
        when(repository.findByEmail(anyString())).thenReturn(Optional.of(UserBuilder.buildSecured()));

        CommandResult response = command.execute(request);

        assertEquals(NO_CONTENT.value(), response.getStatus());
    }


    @Test
    @DisplayName("Should return CommandResult with status code BAD_REQUEST when request is not valid")
    public void shouldReturnCommandResultWithStatusBadRequestWhenRequestIsNotValid()  {
        when(request.isValid()).thenReturn(Boolean.FALSE);
        when(request.getData()).thenReturn(UserBuilder.build());
        when(repository.findByEmail(anyString())).thenReturn(Optional.of(UserBuilder.buildSecured()));

        CommandResult response = command.execute(request);

        assertEquals(BAD_REQUEST.value(), response.getStatus());
    }

    @Test
    @DisplayName("Should return CommandResult with status code UNAUTHORIZED when user send wrong email")
    public void shouldReturnCommandResultWithStatusBadRequestWhenUserSendWrongEmail()  {
        when(request.isValid()).thenReturn(Boolean.TRUE);
        when(request.getData()).thenReturn(UserBuilder.build());
        when(repository.findByEmail(anyString())).thenReturn(Optional.empty());

        CommandResult response = command.execute(request);

        assertEquals(UNAUTHORIZED.value(), response.getStatus());
    }


    @Test
    @DisplayName("Should return CommandResult with status code UNAUTHORIZED when user send wrong password")
    public void shouldReturnCommandResultWithStatusBadRequestWhenUserSendWrongPassword()  {
        when(request.isValid()).thenReturn(Boolean.TRUE);
        when(request.getData()).thenReturn(UserBuilder.build());
        when(repository.findByEmail(anyString())).thenReturn(Optional.of(UserBuilder.build()));

        CommandResult response = command.execute(request);

        assertEquals(UNAUTHORIZED.value(), response.getStatus());
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

