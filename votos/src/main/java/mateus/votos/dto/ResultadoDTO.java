package mateus.votos.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Data Transfer Object for Resultado, only being used to return the result of a voting session")
public class ResultadoDTO {
    @Schema(description = "ID of the pauta", example = "1")
    private Long idPauta;
    @Schema(description = "Number of votes for 'Sim'", example = "10")
    private int votosSim;
    @Schema(description = "Number of votes for 'Nao'", example = "5")
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
