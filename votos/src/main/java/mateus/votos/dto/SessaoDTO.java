package mateus.votos.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Data Transfer Object for Sessao")
public class SessaoDTO {
    @Schema(description = "ID of the pauta", example = "1")
    private Long idPauta;
    @Schema(description = "Start date of the session in ISO format", example = "2025-10-01T10:00:00Z")
    private String dataInicio;
    @Schema(description = "End date of the session in ISO format, can be omitted for default duration (1 minute)", example = "2025-10-01T11:00:00Z")
    private String dataFim;

    /**
     * Construtor para uma SessaoDTO com ID da pauta, data de início e data de fim.
     * 
     * @param idPauta    ID da pauta associada à sessão
     * @param dataInicio Data de início da sessão em formato ISO
     * @param dataFim    Data de fim da sessão em formato ISO, pode ser nulo para
     *                   usar a duração padrão de 1 minuto
     */
    public SessaoDTO(Long idPauta, String dataInicio, String dataFim) {
        this.idPauta = idPauta;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
    }

    /**
     * Construtor para uma SessaoDTO com ID da pauta e data de início.
     * A data de fim será nula, indicando que a sessão usará a duração padrão de 1 minuto.
     * 
     * @param idPauta    ID da pauta associada à sessão
     * @param dataInicio Data de início da sessão em formato ISO
     */
    public SessaoDTO(Long idPauta, String dataInicio) {
        this.idPauta = idPauta;
        this.dataInicio = dataInicio;
        this.dataFim = null;
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
