package mateus.votos.service;

import mateus.votos.dto.SessaoDTO;
import mateus.votos.model.Sessao;
import mateus.votos.repository.SessaoRepository;

import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SessaoService {

    @Autowired
    private SessaoRepository sessaoRepository;

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

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

    public Sessao updateSessao(Sessao sessao) {
        try {
            return sessaoRepository.update(sessao);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Sessao getSessaoById(Long id) {
        try {
            return sessaoRepository.findById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<Sessao> getSessaoAll() {
        try {
            return sessaoRepository.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public void deleteSessao(Long id) {
        try {
            sessaoRepository.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
