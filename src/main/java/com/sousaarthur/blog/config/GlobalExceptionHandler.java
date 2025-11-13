package com.sousaarthur.blog.config;

import com.sousaarthur.blog.exception.EventNotFoundException;
import com.sousaarthur.blog.exception.EventSizeException;
import com.sousaarthur.blog.exception.RestErrorMessage;
import com.sousaarthur.blog.exception.UserNotAuthorizedException;
import org.apache.coyote.Response;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.security.core.AuthenticationException;

import java.util.Locale;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    MessageSource messageSource;

    public GlobalExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(EventNotFoundException.class)
    public ResponseEntity<RestErrorMessage> handleEventNotFound(EventNotFoundException ex) {
        var error = RestErrorMessage.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .message(ex.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }


    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<RestErrorMessage> handleBadCredentials(BadCredentialsException ex) {
        var error = RestErrorMessage.builder()
                .status(HttpStatus.UNAUTHORIZED.value())
                .message(ex.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<RestErrorMessage> handleAuthException(AuthenticationException ex) {
        var error = RestErrorMessage.builder()
                .status(HttpStatus.UNAUTHORIZED.value())
                .message("Falha na autenticação")
                .build();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<RestErrorMessage> handleGenericException(Exception ex){
        var error = RestErrorMessage.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message("Erro interno no servidor")
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<RestErrorMessage> handleAccessDenied(AccessDeniedException ex) {
        var error = RestErrorMessage.builder()
                .status(HttpStatus.FORBIDDEN.value())
                .message("Acesso negado: você não tem permissão para acessar este recurso")
                .build();

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
    }

    @ExceptionHandler(EventSizeException.class)
    public ResponseEntity<RestErrorMessage> hadleEventSizeException(EventSizeException ex){
        var error = RestErrorMessage.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(ex.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(UserNotAuthorizedException.class)
    public ResponseEntity<RestErrorMessage> handleUserNotAuthorizedException(UserNotAuthorizedException ex) {
        var error = RestErrorMessage.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(ex.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
}
