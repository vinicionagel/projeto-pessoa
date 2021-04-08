package com.envolti.teste.controller;

import com.envolti.teste.dto.PessoaDTO;
import com.envolti.teste.exception.PessoaNotFoundException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import com.envolti.teste.exception.PessoaAlreadyRegisteredException;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Api("Manages pessoas")
public interface PessoaControllerDocs {

    @ApiOperation(value = "Pessoa creation operation")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Success pessoa creation"),
            @ApiResponse(code = 400, message = "Missing required fields or wrong field range value.")
    })
    PessoaDTO createBeer(PessoaDTO pessoaDTO) throws PessoaAlreadyRegisteredException;

    @ApiOperation(value = "Returns pessoa found by a given name")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success pessoa found in the system"),
            @ApiResponse(code = 404, message = "Beer with given name not found.")
    })
    PessoaDTO findByName(@PathVariable String name) throws PessoaNotFoundException;

    @ApiOperation(value = "Returns a list of all pessoa registered in the system")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "List of all pessoa registered in the system"),
    })
    List<PessoaDTO> listPessoas();

    @ApiOperation(value = "Delete a pessoa found by a given valid Id")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Success pessoa deleted in the system"),
            @ApiResponse(code = 404, message = "Pessoa with given id not found.")
    })
    void deleteById(@PathVariable Long id) throws PessoaNotFoundException;
}
