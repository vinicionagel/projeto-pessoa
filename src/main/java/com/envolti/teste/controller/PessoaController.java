package com.envolti.teste.controller;

import com.envolti.teste.dto.PessoaDTO;
import com.envolti.teste.exception.PessoaAlreadyRegisteredException;
import com.envolti.teste.exception.PessoaNotFoundException;
import com.envolti.teste.service.PessoaService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1/pessoas")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PessoaController implements PessoaControllerDocs {

    private final PessoaService pessoaService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PessoaDTO createBeer(@RequestBody @Valid PessoaDTO pessoaDTO) throws PessoaAlreadyRegisteredException {
        return pessoaService.createPessoa(pessoaDTO);
    }

    @PutMapping("/{id}")
    @ResponseBody
    public PessoaDTO update(@RequestBody @Valid PessoaDTO pessoaDTO, @PathVariable Long id) throws PessoaAlreadyRegisteredException, PessoaNotFoundException {
        return pessoaService.update(pessoaDTO, id);
    }

    @GetMapping("/{cpf}")
    public PessoaDTO findByName(@PathVariable String cpf) throws PessoaNotFoundException {
        return pessoaService.findByCpf(cpf);
    }

    @GetMapping
    public List<PessoaDTO> listPessoas() {
        return pessoaService.listAll();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) throws PessoaNotFoundException {
        pessoaService.deleteById(id);
    }
}
