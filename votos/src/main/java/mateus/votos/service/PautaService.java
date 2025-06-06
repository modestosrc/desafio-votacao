package mateus.votos.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mateus.votos.dto.PautaDTO;
import mateus.votos.model.Pauta;
import mateus.votos.repository.PautaRepository;

@Service
public class PautaService {

    @Autowired
    private PautaRepository pautaRepository;

    public Pauta createPauta(PautaDTO pautaDTO) {
        try {
            return pautaRepository.save(pautaDTO);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Pauta updatePauta(Pauta pauta) {
        try {
            return pautaRepository.update(pauta);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Pauta getPautaById(Long id) {
        try {
            return pautaRepository.findById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<Pauta> getPautaAll() {
        try {
            return pautaRepository.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public void deletePauta(Long id) {
        try {
            pautaRepository.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
