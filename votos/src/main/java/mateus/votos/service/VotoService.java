package mateus.votos.service;

import mateus.votos.dto.VotoDTO;
import mateus.votos.model.Voto;
import mateus.votos.repository.VotoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VotoService {

    @Autowired
    private VotoRepository votoRepository;

    public Voto createVoto(VotoDTO votoDTO) throws Exception {
        // Passo a exceção para o repositório, para que ele envie a resposta correta
        return votoRepository.save(votoDTO);
    }

    public boolean existsByCpfAndPauta(String cpf, Long idPauta) throws Exception {
        // Verifica se já existe um voto para o CPF e Pauta especificados
        return votoRepository.findByCpfAndPauta(cpf, idPauta) != null;
    }
}
