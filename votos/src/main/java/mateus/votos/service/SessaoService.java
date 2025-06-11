package mateus.votos.service;

import mateus.votos.dto.SessaoDTO;
import mateus.votos.model.Sessao;
import mateus.votos.repository.SessaoRepository;

import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Serviço responsável por gerenciar as sessões de votação.
 *
 * Contém métodos para criar, atualizar, buscar e deletar sessões.
 */
@Service
public class SessaoService {

    @Autowired
    private SessaoRepository sessaoRepository;

    /**
     * Formato de data utilizado para as sessões.
     * O formato é "yyyy-MM-dd'T'HH:mm:ss", que é o padrão ISO 8601.
     */
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

    /**
     * Cria uma nova sessão a partir de um SessaoDTO.
     *
     * Caso a data de fim não seja fornecida, ela será definida como 1 minuto após a
     * data de início.
     *
     * @param sessaoDTO Objeto DTO contendo os dados da sessão a ser criada.
     * @return A sessão criada ou null em caso de erro.
     */
    public Sessao createSessao(SessaoDTO sessaoDTO) {
        try {
            // Aqui, caso não tenha sido usada uma data de fim, a definimos
            // 1 minuto após a data de início
            // TODO:
            // Resto da validação de datas
            if (sessaoDTO.getDataFim() == "") {
                Date dataInicio = dateFormat.parse(sessaoDTO.getDataInicio());
                Date dataFim = new Date(dataInicio.getTime() + 60000);
                return sessaoRepository.save(
                        new SessaoDTO(sessaoDTO.getIdPauta(), sessaoDTO.getDataInicio(), dateFormat.format(dataFim)));
            } else
                return sessaoRepository.save(sessaoDTO);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Atualiza uma sessão existente.
     *
     * @param sessao Objeto Sessao contendo os dados atualizados.
     * @return A sessão atualizada ou null em caso de erro.
     */
    public Sessao updateSessao(Sessao sessao) {
        try {
            return sessaoRepository.update(sessao);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Busca uma sessão pelo ID.
     *
     * @param id ID da sessão a ser buscada.
     * @return A sessão encontrada ou null em caso de erro.
     */
    public Sessao getSessaoById(Long id) {
        try {
            return sessaoRepository.findById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Busca todas as sessões.
     *
     * @return Lista de todas as sessões ou uma lista vazia em caso de erro.
     */
    public ArrayList<Sessao> getSessaoAll() {
        try {
            return sessaoRepository.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    /**
     * Deleta uma sessão pelo ID.
     *
     * @param id ID da sessão a ser deletada.
     */
    public void deleteSessao(Long id) {
        try {
            sessaoRepository.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
