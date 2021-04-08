package com.envolti.teste.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Sexo {

    MASCULINO("Masculino"),
    FEMININO("Feminino");

    private final String description;
}
