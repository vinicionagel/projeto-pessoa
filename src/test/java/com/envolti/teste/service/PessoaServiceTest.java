package com.envolti.teste.service;

import com.envolti.teste.builder.PessoaDTOBuilder;
import com.envolti.teste.dto.PessoaDTO;
import com.envolti.teste.entity.Pessoa;
import com.envolti.teste.exception.PessoaAlreadyRegisteredException;
import com.envolti.teste.exception.PessoaNotFoundException;
import com.envolti.teste.mapper.PessoaMapper;
import com.envolti.teste.repository.PessoaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PessoaServiceTest {

    @Mock
    private PessoaRepository pessoaRepository;

    private PessoaMapper pessoaMapper = PessoaMapper.INSTANCE;

    @InjectMocks
    private PessoaService pessoaService;

    @Test
    void whenPessoaInformedThenItShouldBeCreated() throws PessoaAlreadyRegisteredException {
        // given
        PessoaDTO expectedPessoaDTO = PessoaDTOBuilder.builder().build().toPessoaDTO();
        Pessoa expectedSavedBeer = pessoaMapper.toModel(expectedPessoaDTO);

        // when
        when(pessoaRepository.findByCpf(expectedPessoaDTO.getCpf())).thenReturn(Optional.empty());
        when(pessoaRepository.save(expectedSavedBeer)).thenReturn(expectedSavedBeer);

        //then
        PessoaDTO createdPessoaDTO = pessoaService.createPessoa(expectedPessoaDTO);

        assertThat(createdPessoaDTO.getId(), is(equalTo(expectedPessoaDTO.getId())));
        assertThat(createdPessoaDTO.getNome(), is(equalTo(expectedPessoaDTO.getNome())));
    }

    @Test
    void whenPessoaInformedThenItShouldBeUpdate() throws PessoaAlreadyRegisteredException, PessoaNotFoundException {
        // given
        PessoaDTO expectedPessoaDTO = PessoaDTOBuilder.builder().build().toPessoaDTO();
        Pessoa expectedUpdateBeer = pessoaMapper.toModel(expectedPessoaDTO);

        // when
        when(pessoaRepository.findByCpf(expectedPessoaDTO.getCpf())).thenReturn(Optional.of(expectedUpdateBeer));
        when(pessoaRepository.findById(1l)).thenReturn(Optional.of(expectedUpdateBeer));
        when(pessoaRepository.save(expectedUpdateBeer)).thenReturn(expectedUpdateBeer);

        //then
        PessoaDTO createdPessoaDTO = pessoaService.update(expectedPessoaDTO, expectedPessoaDTO.getId());

        assertThat(createdPessoaDTO.getId(), is(equalTo(expectedPessoaDTO.getId())));
        assertThat(createdPessoaDTO.getNome(), is(equalTo(expectedPessoaDTO.getNome())));
    }

    @Test
    void whenAlreadyRegisteredBeerInformedThenAnExceptionShouldBeThrown() {
        PessoaDTO expectedPessoaDTO = PessoaDTOBuilder.builder().build().toPessoaDTO();
        Pessoa duplicatedPessoa = pessoaMapper.toModel(expectedPessoaDTO);
        duplicatedPessoa.setId(2L);

        when(pessoaRepository.findByCpf(expectedPessoaDTO.getCpf())).thenReturn(Optional.of(duplicatedPessoa));

        assertThrows(PessoaAlreadyRegisteredException.class, () -> pessoaService.createPessoa(expectedPessoaDTO));
    }

    @Test
    void whenValidBeerNameIsGivenThenReturnABeer() throws PessoaNotFoundException {
        PessoaDTO expectedFoundPessoaDTO = PessoaDTOBuilder.builder().build().toPessoaDTO();
        Pessoa expectedFoundBeer = pessoaMapper.toModel(expectedFoundPessoaDTO);

        when(pessoaRepository.findByCpf(expectedFoundBeer.getNome())).thenReturn(Optional.of(expectedFoundBeer));

        PessoaDTO foundPessoaDTO = pessoaService.findByCpf(expectedFoundPessoaDTO.getNome());

        assertThat(foundPessoaDTO, is(equalTo(expectedFoundPessoaDTO)));
    }

    @Test
    void whenNotRegisteredBeerNameIsGivenThenThrowAnException() {
        PessoaDTO expectedFoundPessoaDTO = PessoaDTOBuilder.builder().build().toPessoaDTO();

        when(pessoaRepository.findByCpf(expectedFoundPessoaDTO.getNome())).thenReturn(Optional.empty());

        assertThrows(PessoaNotFoundException.class, () -> pessoaService.findByCpf(expectedFoundPessoaDTO.getNome()));
    }

    @Test
    void whenListBeerIsCalledThenReturnAListOfBeers() {
        PessoaDTO expectedFoundPessoaDTO = PessoaDTOBuilder.builder().build().toPessoaDTO();
        Pessoa expectedFoundBeer = pessoaMapper.toModel(expectedFoundPessoaDTO);

        when(pessoaRepository.findAll()).thenReturn(Collections.singletonList(expectedFoundBeer));

        List<PessoaDTO> foundListBeersDTO = pessoaService.listAll();

        assertThat(foundListBeersDTO, is(not(empty())));
        assertThat(foundListBeersDTO.get(0), is(equalTo(expectedFoundPessoaDTO)));
    }

    @Test
    void whenListBeerIsCalledThenReturnAnEmptyListOfBeers() {
        when(pessoaRepository.findAll()).thenReturn(Collections.EMPTY_LIST);

        List<PessoaDTO> foundListBeersDTO = pessoaService.listAll();

        assertThat(foundListBeersDTO, is(empty()));
    }

    @Test
    void whenExclusionIsCalledWithValidIdThenABeerShouldBeDeleted() throws PessoaNotFoundException {
        PessoaDTO expectedDeletedPessoaDTO = PessoaDTOBuilder.builder().build().toPessoaDTO();
        Pessoa expectedDeletedBeer = pessoaMapper.toModel(expectedDeletedPessoaDTO);

        when(pessoaRepository.findById(expectedDeletedPessoaDTO.getId())).thenReturn(Optional.of(expectedDeletedBeer));
        doNothing().when(pessoaRepository).deleteById(expectedDeletedPessoaDTO.getId());

        pessoaService.deleteById(expectedDeletedPessoaDTO.getId());

        verify(pessoaRepository, times(1)).findById(expectedDeletedPessoaDTO.getId());
        verify(pessoaRepository, times(1)).deleteById(expectedDeletedPessoaDTO.getId());
    }
}
