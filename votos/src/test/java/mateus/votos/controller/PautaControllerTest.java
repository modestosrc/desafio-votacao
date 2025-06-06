package mateus.votos.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import mateus.votos.dto.PautaDTO;
import mateus.votos.model.Pauta;
import mateus.votos.service.PautaService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(PautaController.class)
class PautaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PautaService pautaService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Deve criar uma nova pauta com sucesso")
    void createPauta_success() throws Exception {
        PautaDTO pautaDTO = new PautaDTO("Nova Pauta", "Conteudo da pauta", "ABERTA");
        Pauta pauta = new Pauta(1L, pautaDTO.getName(), pautaDTO.getConteudo(), pautaDTO.getStatus());

        Mockito.when(pautaService.createPauta(any(PautaDTO.class))).thenReturn(pauta);

        mockMvc.perform(post("/pautas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pautaDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Nova Pauta"))
                .andExpect(jsonPath("$.content").value("Conteudo da pauta"))
                .andExpect(jsonPath("$.status").value("ABERTA"));
    }

    @Test
    @DisplayName("Deve retornar uma pauta por ID")
    void getPautaById_success() throws Exception {
        Long id = 1L;
        Pauta pauta = new Pauta(id, "Pauta Teste", "Conteudo Teste", "ABERTA");
        Mockito.when(pautaService.getPautaById(id)).thenReturn(pauta);

        mockMvc.perform(get("/pautas/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value("Pauta Teste"));
    }

    @Test
    @DisplayName("Deve retornar 404 se pauta não encontrada")
    void getPautaById_notFound() throws Exception {
        Long id = 99L;
        Mockito.when(pautaService.getPautaById(id)).thenThrow(new RuntimeException("Pauta não encontrada"));

        mockMvc.perform(get("/pautas/{id}", id))
                .andExpect(status().isNotFound())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Pauta not found")));
    }

    @Test
    @DisplayName("Deve listar todas as pautas")
    void getAllPautas_success() throws Exception {
        Pauta pauta = new Pauta(1L, "Pauta1", "Conteudo1", "ABERTA");
        Mockito.when(pautaService.getPautaAll()).thenReturn(new ArrayList<>(List.of(pauta)));

        mockMvc.perform(get("/pautas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("Pauta1"));
    }

    @Test
    @DisplayName("Deve atualizar uma pauta")
    void updatePauta_success() throws Exception {
        Long id = 1L;
        PautaDTO pautaDTO = new PautaDTO("Atualizada", "Novo Conteudo", "FECHADA");
        Pauta updatedPauta = new Pauta(id, pautaDTO.getName(), pautaDTO.getConteudo(), pautaDTO.getStatus());

        Mockito.when(pautaService.updatePauta(any(Pauta.class))).thenReturn(updatedPauta);

        mockMvc.perform(put("/pautas/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pautaDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value("Atualizada"))
                .andExpect(jsonPath("$.status").value("FECHADA"));
    }

    @Test
    @DisplayName("Deve deletar uma pauta")
    void deletePauta_success() throws Exception {
        Long id = 1L;
        Mockito.doNothing().when(pautaService).deletePauta(id);

        mockMvc.perform(delete("/pautas/{id}", id))
                .andExpect(status().isNoContent());
    }
}
