package com.rcintra.rinhabackendasync.pessoa;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PessoaNotFoundException extends RuntimeException {

    public PessoaNotFoundException(String message) {
        super(message);
    }

    public PessoaNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
