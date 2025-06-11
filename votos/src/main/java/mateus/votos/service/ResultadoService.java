package mateus.votos.service;

import mateus.votos.dto.ResultadoDTO;
import mateus.votos.repository.ResultadoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * Serviço responsável por gerenciar os resultados das pautas.
 *
 * Contém métodos para buscar resultados por ID de pauta e atualizar os resultados periodicamente.
 */
@Service
public class ResultadoService {

    @Autowired
    private ResultadoRepository resultadoRepository;

    /**
     * Busca o resultado de uma pauta pelo ID da pauta.
     *
     * @param idPauta ID da pauta cujo resultado será buscado.
     * @return ResultadoDTO contendo os resultados da pauta.
     * @throws Exception Se ocorrer algum erro ao buscar o resultado.
     */
    public ResultadoDTO getResultadoByIdPauta(Long idPauta) throws Exception {
            return resultadoRepository.findByIdPauta(idPauta);
    }

    /**
     * Atualiza os resultados das votacoes periodicamente.
     * Este método é agendado para ser executado a cada 60 segundos.
     */
    @Scheduled(fixedRate = 60000)
    private void updateResultados() {
        try {
            resultadoRepository.update();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
