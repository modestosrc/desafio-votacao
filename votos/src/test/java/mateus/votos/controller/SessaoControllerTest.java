package mateus.votos.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import mateus.votos.dto.SessaoDTO;
import mateus.votos.model.Sessao;
import mateus.votos.service.SessaoService;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(SessaoController.class)
class SessaoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SessaoService sessaoService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Deve criar uma nova sessão com sucesso")
    void createSessao_success() throws Exception {
        SessaoDTO sessaoDTO = new SessaoDTO(1L, "2025-10-01T10:00:00", "2025-10-01T12:00:00");
        Sessao sessao = new Sessao(1L, sessaoDTO.getIdPauta(), sessaoDTO.getDataInicio(), sessaoDTO.getDataFim());

        Mockito.when(sessaoService.createSessao(any(SessaoDTO.class))).thenReturn(sessao);

        mockMvc.perform(post("/sessao")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(sessaoDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.idPauta").value(1L))
                .andExpect(jsonPath("$.dataInicio").value("2025-10-01T10:00:00"))
                .andExpect(jsonPath("$.dataFim").value("2025-10-01T12:00:00"));
    }

    @Test
    @DisplayName("Deve criar uma nova sessão com data de fim default (1 minuto após data de início)")
    void createSessao_withDefaultEndDate_success() throws Exception {
        SessaoDTO sessaoDTO = new SessaoDTO(1L, "2025-10-01T10:00:00");
        Sessao sessao = new Sessao(1L, sessaoDTO.getIdPauta(), sessaoDTO.getDataInicio(), "2025-10-01T10:01:00");

        Mockito.when(sessaoService.createSessao(any(SessaoDTO.class))).thenReturn(sessao);

        mockMvc.perform(post("/sessao")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(sessaoDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.idPauta").value(1L))
                .andExpect(jsonPath("$.dataInicio").value("2025-10-01T10:00:00"))
                .andExpect(jsonPath("$.dataFim").value("2025-10-01T10:01:00"));
    }

    @Test
    @DisplayName("Deve retornar uma sessão por ID")
    void getSessaoById_success() throws Exception {
        Long id = 1L;
        Sessao sessao = new Sessao(id, 1L, "2025-10-01T10:00:00", "2025-10-01T12:00:00");
        Mockito.when(sessaoService.getSessaoById(id)).thenReturn(sessao);

        mockMvc.perform(get("/sessao/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.idPauta").value(1L))
                .andExpect(jsonPath("$.dataInicio").value("2025-10-01T10:00:00"))
                .andExpect(jsonPath("$.dataFim").value("2025-10-01T12:00:00"));
    }

    @Test
    @DisplayName("Deve retornar todas as sessões")
    void getAllSessoes_success() throws Exception {
        ArrayList<Sessao> sessoes = new ArrayList<>();
        sessoes.add(new Sessao(1L, 1L, "2025-10-01T10:00:00", "2025-10-01T12:00:00"));
        sessoes.add(new Sessao(2L, 2L, "2025-10-01T13:00:00", "2025-10-01T15:00:00"));

        Mockito.when(sessaoService.getSessaoAll()).thenReturn(sessoes);

        mockMvc.perform(get("/sessao"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].idPauta").value(1L))
                .andExpect(jsonPath("$[0].dataInicio").value("2025-10-01T10:00:00"))
                .andExpect(jsonPath("$[0].dataFim").value("2025-10-01T12:00:00"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].idPauta").value(2L))
                .andExpect(jsonPath("$[1].dataInicio").value("2025-10-01T13:00:00"))
                .andExpect(jsonPath("$[1].dataFim").value("2025-10-01T15:00:00"));
    }

    @Test
    @DisplayName("Deve atualizar uma sessão com sucesso")
    void updateSessao_success() throws Exception {
        Long id = 1L;
        SessaoDTO sessaoDTO = new SessaoDTO(1L, "2025-10-01T10:00:00", "2025-10-01T12:00:00");
        Sessao updatedSessao = new Sessao(id, sessaoDTO.getIdPauta(), sessaoDTO.getDataInicio(),
                sessaoDTO.getDataFim());

        Mockito.when(sessaoService.updateSessao(any(Sessao.class))).thenReturn(updatedSessao);

        mockMvc.perform(put("/sessao/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(sessaoDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.idPauta").value(1L))
                .andExpect(jsonPath("$.dataInicio").value("2025-10-01T10:00:00"))
                .andExpect(jsonPath("$.dataFim").value("2025-10-01T12:00:00"));
    }

    @Test
    @DisplayName("Deve deletar uma sessão com sucesso")
    void deleteSessao_success() throws Exception {
        Long id = 1L;

        Mockito.doNothing().when(sessaoService).deleteSessao(id);

        mockMvc.perform(delete("/sessao/{id}", id))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Deve retornar 404 se sessão não encontrada")
    void getSessaoById_notFound() throws Exception {
        Long id = 99L;
        Mockito.when(sessaoService.getSessaoById(id)).thenThrow(new RuntimeException("Sessão não encontrada"));

        mockMvc.perform(get("/sessao/{id}", id))
                .andExpect(status().isNotFound())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Session not found")));
    }

    @Test
    @DisplayName("Deve retornar 500 se erro ao criar sessão")
    void createSessao_internalServerError() throws Exception {
        SessaoDTO sessaoDTO = new SessaoDTO(1L, "2025-10-01T10:00:00", "2025-10-01T12:00:00");
        Mockito.when(sessaoService.createSessao(any(SessaoDTO.class)))
                .thenThrow(new RuntimeException("Erro ao criar sessão"));

        mockMvc.perform(post("/sessao")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(sessaoDTO)))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Error creating session")));
    }

    @Test
    @DisplayName("Deve retornar 500 se erro ao atualizar sessão")
    void updateSessao_internalServerError() throws Exception {
        Long id = 1L;
        SessaoDTO sessaoDTO = new SessaoDTO(1L, "2025-10-01T10:00:00", "2025-10-01T12:00:00");
        Mockito.when(sessaoService.updateSessao(any(Sessao.class)))
                .thenThrow(new RuntimeException("Erro ao atualizar sessão"));

        mockMvc.perform(put("/sessao/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(sessaoDTO)))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Error updating session")));
    }

    @Test
    @DisplayName("Deve retornar 500 se erro ao deletar sessão")
    void deleteSessao_internalServerError() throws Exception {
        Long id = 1L;
        Mockito.doThrow(new RuntimeException("Erro ao deletar sessão")).when(sessaoService).deleteSessao(id);

        mockMvc.perform(delete("/sessao/{id}", id))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Error deleting session")));
    }
}
