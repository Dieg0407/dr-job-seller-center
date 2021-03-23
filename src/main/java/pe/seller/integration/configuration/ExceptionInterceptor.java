package pe.seller.integration.configuration;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import pe.seller.integration.domain.model.dto.ErrorDTO;

import java.sql.Timestamp;

@Log4j2
@ControllerAdvice
public class ExceptionInterceptor {

    @ExceptionHandler(value = { Exception.class, RuntimeException.class })
    protected ResponseEntity<ErrorDTO> handleDefaultConflict(Exception ex, WebRequest request) {
        if (! (ex instanceof HttpRequestMethodNotSupportedException) && !(ex instanceof HttpMediaTypeNotSupportedException))
            log.error("Internal error: " + ex.getMessage(), ex);

        final var error = ErrorDTO.builder()
                .message(ex.getMessage())
                .stackMessage(ex.getMessage())
                .timestamp(new Timestamp(System.currentTimeMillis()).toString())
                .build();

        final var headers = new HttpHeaders();
        headers.add("content-type", MediaType.APPLICATION_JSON.toString() + ";charset=utf-8");

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .headers(headers)
                .body(error);
    }
}