package mateus.votos.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import mateus.votos.dto.ResultadoDTO;
import mateus.votos.service.ResultadoService;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(ResultadoController.class)
class ResultadoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ResultadoService resultadoService;

    @Test
    @DisplayName("Deve retornar o resultado de uma pauta com sucesso")
    void getResultadoByPautaId_success() throws Exception {
        Long idPauta = 1L;
        ResultadoDTO resultadoDTO = new ResultadoDTO(idPauta, 10, 5);

        Mockito.when(resultadoService.getResultadoByIdPauta(idPauta)).thenReturn(resultadoDTO);

        mockMvc.perform(get("/resultado/{idPauta}", idPauta)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idPauta").value(idPauta))
                .andExpect(jsonPath("$.votosSim").value(10))
                .andExpect(jsonPath("$.votosNao").value(5));
    }

    @Test
    @DisplayName("Deve retornar erro ao tentar obter resultado de pauta inexistente")
    void getResultadoByPautaId_notFound() throws Exception {
        Long idPauta = 999L;

        Mockito.when(resultadoService.getResultadoByIdPauta(idPauta))
                .thenThrow(new RuntimeException("Pauta não encontrada"));

        mockMvc.perform(get("/resultado/{idPauta}", idPauta)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
