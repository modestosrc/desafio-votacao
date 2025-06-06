package mateus.votos.dto;

public class SessaoDTO {
    private int idPauta;
    private String dataInicio;
    private String dataFim;

    public SessaoDTO(int idPauta, String dataInicio, String dataFim) {
        this.idPauta = idPauta;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
    }

    public int getIdPauta() {
        return idPauta;
    }

    public String getDataInicio() {
        return dataInicio;
    }

    public String getDataFim() {
        return dataFim;
    }
}
