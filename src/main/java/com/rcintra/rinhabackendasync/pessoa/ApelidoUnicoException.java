package com.rcintra.rinhabackendasync.pessoa;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class ApelidoUnicoException extends RuntimeException {

    public ApelidoUnicoException(String message) {
        super(message);
    }

    public ApelidoUnicoException(String message, Throwable cause) {
        super(message, cause);
    }

}
