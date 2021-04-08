package com.envolti.teste.builder;

import com.envolti.teste.dto.PessoaDTO;
import com.envolti.teste.enums.Sexo;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public class PessoaDTOBuilder {

    @Builder.Default
    private Long id = 1L;

    @Builder.Default
    private String nome = "Jose";

    @Builder.Default
    private String email = "teste@teste.com";

    @Builder.Default
    private Sexo sexo = Sexo.MASCULINO;

    @Builder.Default
    private String naturalidade = "carioca";

    @Builder.Default
    private String nacionalidade = "brasileiro";

    @Builder.Default
    private LocalDate dataNascimento = LocalDate.now();

    @Builder.Default
    private String cpf = "08110629962";


    public PessoaDTO toPessoaDTO() {
        return new PessoaDTO(id,
                nome,
                email,
                sexo,
                dataNascimento,
                nacionalidade,
                naturalidade,
                cpf);
    }
}
