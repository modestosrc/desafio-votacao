package mateus.votos.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import mateus.votos.dto.VotoDTO;
import mateus.votos.model.Voto;
import mateus.votos.service.VotoService;
import mateus.votos.exception.AlreadyExistsException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(VotoController.class)
class VotoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private VotoService votoService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Deve criar um novo voto com sucesso")
    void createVoto_success() throws Exception {
        VotoDTO votoDTO = new VotoDTO("12345678901", 1L, "SIM");
        Voto voto = new Voto(1L, votoDTO.getCpf(), votoDTO.getIdPauta(), votoDTO.getVoto());

        Mockito.when(votoService.createVoto(any(VotoDTO.class))).thenReturn(voto);

        mockMvc.perform(post("/voto")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(votoDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.cpf").value("12345678901"))
                .andExpect(jsonPath("$.idPauta").value(1L))
                .andExpect(jsonPath("$.voto").value("SIM"));
    }

    @Test
    @DisplayName("Deve retornar conflito ao tentar criar um voto já existente")
    void createVoto_alreadyExists() throws Exception {
        VotoDTO votoDTO = new VotoDTO("12345678901", 1L, "NAO");

        Mockito.when(votoService.createVoto(any(VotoDTO.class)))
                .thenThrow(new AlreadyExistsException("Voto já existe"));

        mockMvc.perform(post("/voto")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(votoDTO)))
                .andExpect(status().isConflict())
                .andExpect(content().string("Voto já existe"));
    }

    @Test
    @DisplayName("Deve verificar se um voto existe para o CPF e Pauta especificados")
    void getVoto_exists() throws Exception {
        VotoDTO votoDTO = new VotoDTO("12345678901", 1L, null);

        Mockito.when(votoService.existsByCpfAndPauta(votoDTO.getCpf(), votoDTO.getIdPauta())).thenReturn(true);

        mockMvc.perform(get("/voto")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(votoDTO)))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    @DisplayName("Deve retornar erro ao buscar votos")
    void getVoto_error() throws Exception {
        VotoDTO votoDTO = new VotoDTO("12345678901", 1L, null);

        Mockito.when(votoService.existsByCpfAndPauta(votoDTO.getCpf(), votoDTO.getIdPauta()))
                .thenThrow(new RuntimeException("Erro ao buscar votos"));

        mockMvc.perform(get("/voto")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(votoDTO)))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Erro ao buscar votos: Erro ao buscar votos"));
    }
}
