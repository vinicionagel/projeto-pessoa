package com.envolti.teste.service;

import com.envolti.teste.dto.PessoaDTO;
import com.envolti.teste.entity.Pessoa;
import com.envolti.teste.mapper.PessoaMapper;
import lombok.AllArgsConstructor;
import com.envolti.teste.exception.PessoaAlreadyRegisteredException;
import com.envolti.teste.exception.PessoaNotFoundException;
import com.envolti.teste.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PessoaService {

    private final PessoaRepository pessoaRepository;
    private final PessoaMapper pessoaMapper = PessoaMapper.INSTANCE;

    public PessoaDTO createPessoa(PessoaDTO pessoaDTO) throws PessoaAlreadyRegisteredException {
        verifyIfIsAlreadyRegistered(pessoaDTO.getNome());
        Pessoa beer = pessoaMapper.toModel(pessoaDTO);
        Pessoa savedBeer = pessoaRepository.save(beer);
        return pessoaMapper.toDTO(savedBeer);
    }

    public PessoaDTO findByCpf(String cpf) throws PessoaNotFoundException {
        Pessoa foundPessoa = pessoaRepository.findByCpf(cpf)
                .orElseThrow(() -> new PessoaNotFoundException(cpf));
        return pessoaMapper.toDTO(foundPessoa);
    }

    public List<PessoaDTO> listAll() {
        return pessoaRepository.findAll()
                .stream()
                .map(pessoaMapper::toDTO)
                .collect(Collectors.toList());
    }

    public void deleteById(Long id) throws PessoaNotFoundException {
        verifyIfExists(id);
        pessoaRepository.deleteById(id);
    }

    private void verifyIfIsAlreadyRegistered(String cpf) throws PessoaAlreadyRegisteredException {
        Optional<Pessoa> optSavedBeer = pessoaRepository.findByCpf(cpf);
        if (optSavedBeer.isPresent()) {
            throw new PessoaAlreadyRegisteredException(cpf);
        }
    }

    private Pessoa verifyIfExists(Long id) throws PessoaNotFoundException {
        return pessoaRepository.findById(id)
                .orElseThrow(() -> new PessoaNotFoundException(id));
    }
}
