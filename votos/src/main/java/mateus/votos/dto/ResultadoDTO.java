package mateus.votos.dto;

public class ResultadoDTO {
    private int idPauta;
    private int votosSim;
    private int votosNao;

    public ResultadoDTO(int idPauta, int votosSim, int votosNao) {
        this.idPauta = idPauta;
        this.votosSim = votosSim;
        this.votosNao = votosNao;
    }

    public int getIdPauta() {
        return idPauta;
    }

    public int getVotosSim() {
        return votosSim;
    }

    public int getVotosNao() {
        return votosNao;
    }
}
