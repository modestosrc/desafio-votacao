package mateus.votos.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mateus.votos.dto.PautaDTO;
import mateus.votos.model.Pauta;
import mateus.votos.repository.PautaRepository;

/**
 * Serviço responsável por gerenciar as pautas.
 *
 * Contém métodos para criar, atualizar, buscar e deletar pautas.
 */
@Service
public class PautaService {

    @Autowired
    private PautaRepository pautaRepository;

    /**
     * Cria uma nova pauta a partir de um PautaDTO.
     *
     * @param pautaDTO Objeto DTO contendo os dados da pauta a ser criada.
     * @return A pauta criada ou null em caso de erro.
     */
    public Pauta createPauta(PautaDTO pautaDTO) {
        try {
            return pautaRepository.save(pautaDTO);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Atualiza uma pauta existente.
     *
     * @param pauta Objeto Pauta contendo os dados atualizados.
     * @return A pauta atualizada ou null em caso de erro.
     */
    public Pauta updatePauta(Pauta pauta) {
        try {
            return pautaRepository.update(pauta);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Busca uma pauta pelo ID.
     *
     * @param id ID da pauta a ser buscada.
     * @return A pauta encontrada ou null em caso de erro.
     */
    public Pauta getPautaById(Long id) {
        try {
            return pautaRepository.findById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Busca todas as pautas.
     *
     * @return Lista de todas as pautas ou uma lista vazia em caso de erro.
     */
    public ArrayList<Pauta> getPautaAll() {
        try {
            return pautaRepository.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    /**
     * Deleta uma pauta pelo ID.
     *
     * @param id ID da pauta a ser deletada.
     */
    public void deletePauta(Long id) {
        try {
            pautaRepository.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
