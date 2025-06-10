package net.noxe.retroarchivebe.utils;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
public class GenericMessage {

    private final String message;
    private final HttpStatus status;
    private final LocalDateTime timestamp;

    public GenericMessage(String message, HttpStatus status) {
        this.timestamp = LocalDateTime.now();
        this.message = message;
        this.status = status;
    }
}
