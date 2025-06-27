package net.noxe.retroarchivebe.exceptions;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.event.Level;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Arrays;

@Getter
public class SecurityLoggedException extends RuntimeException {

    private final transient Logger logger;

    private final String message;
    private final HttpStatus status;
    private final LocalDateTime timestamp;

    public SecurityLoggedException(String message, Logger logger, Level level) {
        this.timestamp = LocalDateTime.now();
        this.message = message;
        this.status = null;
        this.logger = logger;
        switch (level) {
            case ERROR -> logger.error(message);
            case WARN -> logger.warn(message);
            case INFO -> logger.info(message);
            case DEBUG -> logger.debug(message);
            case TRACE -> logger.trace(message);
            default -> logger.error("Unknown log level: {}", level);
        }
    }

    public SecurityLoggedException(String message, HttpStatus status, Logger logger, Level level) {
        this.timestamp = LocalDateTime.now();
        this.message = message;
        this.status = status;
        this.logger = logger;
        switch (level) {
            case ERROR -> logger.error(message);
            case WARN -> logger.warn(message);
            case INFO -> logger.info(message);
            case DEBUG -> logger.debug(message);
            case TRACE -> logger.trace(message);
            default -> throw new IllegalArgumentException("Unknown log level: " + level);
        }
    }

    public SecurityLoggedException(String message, HttpStatus status, Logger logger, Level level, Object... args) {
        this.timestamp = LocalDateTime.now();
        this.message = message.replace("{}", Arrays.toString(args));
        this.status = status;
        this.logger = logger;
        switch (level) {
            case ERROR -> logger.error(message, args);
            case WARN -> logger.warn(message, args);
            case INFO -> logger.info(message, args);
            case DEBUG -> logger.debug(message, args);
            case TRACE -> logger.trace(message, args);
            default -> throw new IllegalArgumentException("Unknown log level: " + level);
        }
    }

}
