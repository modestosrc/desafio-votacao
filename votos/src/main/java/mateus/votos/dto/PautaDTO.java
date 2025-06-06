package mateus.votos.dto;

import mateus.votos.model.Pauta;

public class PautaDTO {
    private String name;
    private String content;
    private String status;

    public PautaDTO() {
        // For jackson
    }

    public PautaDTO(String name, String content) {
        this.name = name;
        this.content = content;
        this.status = "PENDING"; // Default status
    }

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
