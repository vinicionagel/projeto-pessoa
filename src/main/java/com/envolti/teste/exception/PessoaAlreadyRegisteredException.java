package com.envolti.teste.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PessoaAlreadyRegisteredException extends Exception{

    public PessoaAlreadyRegisteredException(String cpf) {
        super(String.format("Pessoa with CPF %s already registered in the system.", cpf));
    }
}
