package mateus.votos.dto;

public class ResultadoDTO {
    private Long idPauta;
    private int votosSim;
    private int votosNao;

    public ResultadoDTO(Long idPauta, int votosSim, int votosNao) {
        this.idPauta = idPauta;
        this.votosSim = votosSim;
        this.votosNao = votosNao;
    }

    public Long getIdPauta() {
        return idPauta;
    }

    public int getVotosSim() {
        return votosSim;
    }

    public int getVotosNao() {
        return votosNao;
    }
}
