package com.envolti.teste.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PessoaNotFoundException extends Exception {

    public PessoaNotFoundException(String beerName) {
        super(String.format("Pessoa with name %s not found in the system.", beerName));
    }

    public PessoaNotFoundException(Long id) {
        super(String.format("Pessoa with id %s not found in the system.", id));
    }
}
