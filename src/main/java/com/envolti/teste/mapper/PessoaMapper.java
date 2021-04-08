package com.envolti.teste.mapper;

import com.envolti.teste.dto.PessoaDTO;
import com.envolti.teste.entity.Pessoa;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PessoaMapper {

    PessoaMapper INSTANCE = Mappers.getMapper(PessoaMapper.class);

    Pessoa toModel(PessoaDTO pessoaDTO);

    PessoaDTO toDTO(Pessoa beer);
}
