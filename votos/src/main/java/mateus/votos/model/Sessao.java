package mateus.votos.model;

import mateus.votos.dto.SessaoDTO;

public class Sessao {
    private Long id;
    private Long idPauta;
    private String dataInicio;
    private String dataFim;

    public Sessao(Long id, Long idPauta, String dataInicio, String dataFim) {
        this.id = id;
        this.idPauta = idPauta;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdPauta() {
        return idPauta;
    }

    public void setIdPauta(Long idPauta) {
        this.idPauta = idPauta;
    }

    public String getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(String dataInicio) {
        this.dataInicio = dataInicio;
    }

    public String getDataFim() {
        return dataFim;
    }

    public void setDataFim(String dataFim) {
        this.dataFim = dataFim;
    }

}
