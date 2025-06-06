package mateus.votos.model;

import mateus.votos.dto.PautaDTO;

public class Pauta {
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

    public void setId(Long id) {
        this.id = id;
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
