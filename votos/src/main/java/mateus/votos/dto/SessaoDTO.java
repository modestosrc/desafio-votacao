package mateus.votos.dto;

public class SessaoDTO {
    private Long idPauta;
    private String dataInicio;
    private String dataFim;

    public SessaoDTO(Long idPauta, String dataInicio, String dataFim) {
        this.idPauta = idPauta;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
    }

    public SessaoDTO(Long idPauta, String dataInicio) {
        this.idPauta = idPauta;
        this.dataInicio = dataInicio;
        this.dataFim = null; // Caso seja chamada sem dataFim, para que possamos
                             // criar a sessão com tempo padrão de 1 minuto de duração
    }

    public SessaoDTO() {
        // Jackson...
    }

    public Long getIdPauta() {
        return idPauta;
    }

    public String getDataInicio() {
        return dataInicio;
    }

    public String getDataFim() {
        return dataFim;
    }
}
