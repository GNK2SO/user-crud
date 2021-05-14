package br.com.manager.phone.command;

import static br.com.manager.util.HttpStatus.BAD_REQUEST;
import static br.com.manager.util.HttpStatus.CREATED;
import static br.com.manager.util.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import br.com.manager.core.CommandResult;
import br.com.manager.phone.builder.PhoneBuilder;
import br.com.manager.phone.command.request.CreatePhoneRequest;
import br.com.manager.phone.model.Phone;
import br.com.manager.phone.repository.PhoneRepository;

@TestInstance(Lifecycle.PER_CLASS)
public class SavePhoneCommandTest {

    private SavePhoneCommand command;
    private PhoneRepository repository;
    private CreatePhoneRequest request;

    @BeforeEach
    public void setup() {
        request = mock(CreatePhoneRequest.class);
        repository = mock(PhoneRepository.class);
        command = new SavePhoneCommand(repository);
    }

    @Test
    @DisplayName("Should return CommandResult with status code CREATED when save phone successfully")
    public void shouldReturnCommandResultWithStatusCreatedWhenSavePhoneSuccessfully() {
        when(request.isValid()).thenReturn(Boolean.TRUE);
        CommandResult response = command.execute(request);
        assertEquals(CREATED.value(), response.getStatus());
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
        when(request.getData()).thenReturn(PhoneBuilder.build());
        when(repository.addPhone(anyLong(), any(Phone.class))).thenThrow(new RuntimeException());

        CommandResult response = command.execute(request);

        assertEquals(INTERNAL_SERVER_ERROR.value(), response.getStatus());
    }
}
