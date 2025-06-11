package mateus.votos.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Data Transfer Object for Voto")
public class VotoDTO {
    @Schema(description = "CPF of the voter", example = "12345678901")
    private String cpf;
    @Schema(description = "ID of the pauta", example = "1")
    private Long idPauta;
    @Schema(description = "Vote value, can be 'Sim' or 'Nao'", example = "Sim")
    private String voto;

    /**
     * Construtor para um VotoDTO com CPF, ID da pauta e voto.
     * 
     * @param cpf     CPF do votante
     * @param idPauta ID da pauta associada ao voto
     * @param voto    Valor do voto, pode ser 'Sim' ou 'Nao'
     */
    public VotoDTO(String cpf, Long idPauta, String voto) {
        this.cpf = cpf;
        this.idPauta = idPauta;
        this.voto = voto;
    }

    /**
     * Construtor para um VotoDTO com CPF e ID da pauta, sem voto.
     *
     * Esta sendo utilizado para verificar se o votante já votou na pauta.
     * 
     * @param cpf     CPF do votante
     * @param idPauta ID da pauta associada ao voto
     */
    public VotoDTO(String cpf, Long idPauta) {
        this.cpf = cpf;
        this.idPauta = idPauta;
        this.voto = null;
    }

    public VotoDTO() {
        // For Jackson
    }

    public String getCpf() {
        return cpf;
    }

    public Long getIdPauta() {
        return idPauta;
    }

    public String getVoto() {
        return voto;
    }

}
