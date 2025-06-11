package mateus.votos.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import mateus.votos.model.Pauta;

@Schema(description = "Data Transfer Object for Pauta")
public class PautaDTO {
    @Schema(description = "Name of the pauta", example = "Pauta 1")
    private String name;
    @Schema(description = "Content of the pauta", example = "Discuss the new project proposal")
    private String content;
    /**
     * NOTE: No momento o status está sendo usado pelo serviço da pauta,
     * para sinalizar como apagada, e no momento da leitura de uma pauta.
     * Mas além do momento em que a pauta é marcada como apagada, não hã
     * outras modificações no status.
     * A forma de modificar o status da pauta é através de
     * um request de PUT para o endpoint.
     */
    @Schema(description = "Status of the pauta, at the moment it doesn't matter", example = "PENDING")
    private String status;

    public PautaDTO() {
        // For jackson
    }

    /**
     * Construtor para uma PautaDTO com nome, conteúdo e status padrão "PENDING".
     *
     * @param name    Nome da pauta
     * @param content Conteúdo da pauta
     */
    public PautaDTO(String name, String content) {
        this.name = name;
        this.content = content;
        this.status = "PENDING";
    }

    /**
     * Construtor para uma PautaDTO com nome, conteúdo e status especificado.
     *
     * @param name    Nome da pauta
     * @param content Conteúdo da pauta
     * @param status  Status da pauta
     */
    public PautaDTO(String name, String content, String status) {
        this.name = name;
        this.content = content;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getConteudo() {
        return content;
    }

    public void setConteudo(String content) {
        this.content = content;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Pauta toModel(Long id) {
        return new Pauta(id, name, content, status);
    }
}
