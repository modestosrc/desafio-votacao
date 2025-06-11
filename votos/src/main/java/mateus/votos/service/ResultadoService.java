package mateus.votos.service;

import mateus.votos.dto.ResultadoDTO;
import mateus.votos.repository.ResultadoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.scheduling.annotation.Scheduled;

@Service
public class ResultadoService {

    @Autowired
    private ResultadoRepository resultadoRepository;

    public ResultadoDTO getResultadoByIdPauta(Long idPauta) throws Exception {
            return resultadoRepository.findByIdPauta(idPauta);
    }

    @Scheduled(fixedRate = 60000) // Executa a cada 60 segundos
    public void updateResultados() {
        try {
            resultadoRepository.update();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
