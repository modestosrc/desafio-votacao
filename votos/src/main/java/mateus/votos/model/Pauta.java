package mateus.votos.model;

import mateus.votos.dto.PautaDTO;

/**
 * Representa uma pauta no sistema de votação.
 *
 * Contém informações como nome, conteúdo e status da pauta.
 * E seus respectivos métodos de acesso e modificação.
 */
public class Pauta {
    // NOTE: O id é gerado pelo banco de dados
    private Long id;
    private String name;
    private String content;
    private String status;

    public Pauta(Long id, String name, String content, String status) {
        this.id = id;
        this.name = name;
        this.content = content;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public PautaDTO toDTO() {
        return new PautaDTO(name, content, status);
    }
}
