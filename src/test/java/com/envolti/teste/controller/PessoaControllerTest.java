package com.envolti.teste.controller;

import com.envolti.teste.builder.PessoaDTOBuilder;
import com.envolti.teste.dto.PessoaDTO;
import com.envolti.teste.exception.PessoaNotFoundException;
import com.envolti.teste.service.PessoaService;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.Collections;

import static com.envolti.teste.utils.JsonConvertionUtils.asJsonString;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@TestPropertySource(properties = {"username = admin", "password = admin"})
public class PessoaControllerTest {

    private static final String PESSOA_API_URL_PATH = "/api/v1/pessoas";
    private static final long INVALID_PESSOA_ID = 2l;
    public static final String JSON_NOME = "$.nome";
    public static final String JSON_EMAIL = "$.email";
    public static final String JSON_SEXO = "$.sexo";

    private MockMvc mockMvc;

    @Mock
    private PessoaService pessoaService;

    @InjectMocks
    private PessoaController pessoaController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(pessoaController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setViewResolvers((s, locale) -> new MappingJackson2JsonView())
                .build();
    }

    @Test
    void whenPOSTIsCalledThenABeerIsCreated() throws Exception {
        // given
        PessoaDTO pessoaDTO = PessoaDTOBuilder.builder().build().toPessoaDTO();

        // when
        when(pessoaService.createPessoa(pessoaDTO)).thenReturn(pessoaDTO);

        // then
        mockMvc.perform(post(PESSOA_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(pessoaDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath(JSON_NOME, is(pessoaDTO.getNome())))
                .andExpect(jsonPath(JSON_EMAIL, is(pessoaDTO.getEmail())))
                .andExpect(MockMvcResultMatchers.jsonPath(JSON_SEXO, Is.is(pessoaDTO.getSexo().toString())));
    }

    @Test
    void whenPUTIsCalledThenAPessoaIsUpdate() throws Exception {
        // given
        PessoaDTO pessoaDTO = PessoaDTOBuilder.builder().build().toPessoaDTO();

        // when
        when(pessoaService.update(pessoaDTO, 1l)).thenReturn(pessoaDTO);

        // then
        MockHttpServletRequestBuilder bodyBuilder = MockMvcRequestBuilders.put(PESSOA_API_URL_PATH + "/" + 1).contentType(MediaType.APPLICATION_JSON).content(asJsonString(pessoaDTO));
        mockMvc.perform(bodyBuilder).andExpect(status().isOk()).andExpect(jsonPath(JSON_NOME, is(pessoaDTO.getNome()))).andExpect(jsonPath(JSON_NOME, is(pessoaDTO.getNome())))
                .andExpect(jsonPath(JSON_EMAIL, is(pessoaDTO.getEmail())));
    }

    @Test
    void whenPOSTIsCalledWithoutRequiredFieldThenAnErrorIsReturned() throws Exception {
        // given
        PessoaDTO pessoaDTO = PessoaDTOBuilder.builder().build().toPessoaDTO();
        pessoaDTO.setEmail(null);

        // then
        mockMvc.perform(post(PESSOA_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(pessoaDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenGETIsCalledWithValidNameThenOkStatusIsReturned() throws Exception {
        // given
        PessoaDTO pessoaDTO = PessoaDTOBuilder.builder().build().toPessoaDTO();

        //when
        when(pessoaService.findByCpf(pessoaDTO.getNome())).thenReturn(pessoaDTO);

        // then
        mockMvc.perform(MockMvcRequestBuilders.get(PESSOA_API_URL_PATH + "/" + pessoaDTO.getNome())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath(JSON_NOME, is(pessoaDTO.getNome())))
                .andExpect(jsonPath(JSON_EMAIL, is(pessoaDTO.getEmail())))
                .andExpect(MockMvcResultMatchers.jsonPath(JSON_SEXO, Is.is(pessoaDTO.getSexo().toString())));
    }

    @Test
    void whenGETIsCalledWithoutRegisteredCpfThenNotFoundStatusIsReturned() throws Exception {
        // given
        PessoaDTO pessoaDTO = PessoaDTOBuilder.builder().build().toPessoaDTO();

        //when
        when(pessoaService.findByCpf(pessoaDTO.getCpf())).thenThrow(PessoaNotFoundException.class);

        // then
        mockMvc.perform(MockMvcRequestBuilders.get(PESSOA_API_URL_PATH + "/" + pessoaDTO.getCpf())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void whenGETListWithPessoasIsCalledThenOkStatusIsReturned() throws Exception {
        // given
        PessoaDTO pessoaDTO = PessoaDTOBuilder.builder().build().toPessoaDTO();

        //when
        when(pessoaService.listAll()).thenReturn(Collections.singletonList(pessoaDTO));

        // then
        mockMvc.perform(MockMvcRequestBuilders.get(PESSOA_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome", is(pessoaDTO.getNome())))
                .andExpect(jsonPath("$[0].email", is(pessoaDTO.getEmail())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].sexo", Is.is(pessoaDTO.getSexo().toString())));
    }

    @Test
    void whenGETListWithoutBeersIsCalledThenOkStatusIsReturned() throws Exception {
        // given
        PessoaDTO pessoaDTO = PessoaDTOBuilder.builder().build().toPessoaDTO();

        //when
        when(pessoaService.listAll()).thenReturn(Collections.singletonList(pessoaDTO));

        // then
        mockMvc.perform(MockMvcRequestBuilders.get(PESSOA_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void whenDELETEIsCalledWithValidIdThenNoContentStatusIsReturned() throws Exception {
        // given
        PessoaDTO pessoaDTO = PessoaDTOBuilder.builder().build().toPessoaDTO();

        //when
        doNothing().when(pessoaService).deleteById(pessoaDTO.getId());

        // then
        mockMvc.perform(MockMvcRequestBuilders.delete(PESSOA_API_URL_PATH + "/" + pessoaDTO.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void whenDELETEIsCalledWithInvalidIdThenNotFoundStatusIsReturned() throws Exception {
        //when
        doThrow(PessoaNotFoundException.class).when(pessoaService).deleteById(INVALID_PESSOA_ID);

        // then
        mockMvc.perform(MockMvcRequestBuilders.delete(PESSOA_API_URL_PATH + "/" + INVALID_PESSOA_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
