package net.noxe.retroarchivebe.utils;

import io.jsonwebtoken.security.SignatureException;
import net.noxe.retroarchivebe.exceptions.AppUserLoggedException;
import net.noxe.retroarchivebe.exceptions.StorageLoggedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GenericControllerAdvice {

    @ExceptionHandler(StorageLoggedException.class)
    public ResponseEntity<GenericMessage> handleStorageLoggedException(StorageLoggedException e) {
        GenericMessage message = new GenericMessage(e.getMessage(), e.getStatus());
        return ResponseEntity.status(message.getStatus()).body(message);
    }

    @ExceptionHandler(AppUserLoggedException.class)
    public ResponseEntity<GenericMessage> handleAppUserLoggedException(AppUserLoggedException e) {
        GenericMessage message = new GenericMessage(e.getMessage(), e.getStatus());
        return ResponseEntity.status(message.getStatus()).body(message);
    }

    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<GenericMessage> handleSignatureException(SignatureException e) {
        GenericMessage message = new GenericMessage("Invalid or expired Token, please login again.", HttpStatus.UNAUTHORIZED);
        return ResponseEntity.status(message.getStatus()).body(message);
    }
}
