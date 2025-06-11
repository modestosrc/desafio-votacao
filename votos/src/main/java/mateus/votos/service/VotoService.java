package mateus.votos.service;

import mateus.votos.dto.VotoDTO;
import mateus.votos.model.Voto;
import mateus.votos.repository.VotoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Serviço responsável por gerenciar os votos.
 *
 * Contém métodos para criar votos e verificar se um voto já existe para um determinado CPF e Pauta.
 */
@Service
public class VotoService {

    @Autowired
    private VotoRepository votoRepository;

    /**
     * Cria um novo voto a partir de um VotoDTO.
     *
     * @param votoDTO Objeto DTO contendo os dados do voto a ser criado.
     * @return O voto criado.
     * @throws Exception Se ocorrer algum erro ao criar o voto.
     */
    public Voto createVoto(VotoDTO votoDTO) throws Exception {
        // Passo a exceção para o repositório, para que ele envie a resposta correta
        return votoRepository.save(votoDTO);
    }

    /**
     * Verifica se já existe um voto para o CPF e Pauta especificados.
     *
     * @param cpf O CPF do votante.
     * @param idPauta O ID da pauta.
     * @return true se já existir um voto, false caso contrário.
     * @throws Exception Se ocorrer algum erro ao verificar a existência do voto.
     */
    public boolean existsByCpfAndPauta(String cpf, Long idPauta) throws Exception {
        // Verifica se já existe um voto para o CPF e Pauta especificados
        return votoRepository.findByCpfAndPauta(cpf, idPauta) != null;
    }
}
