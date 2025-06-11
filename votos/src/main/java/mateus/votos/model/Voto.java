package mateus.votos.model;

public class Voto {
    private Long id;
    private String cpf;
    private Long idPauta;
    private String voto;

    public Voto(Long id, String cpf, Long idPauta, String voto) {
        this.id = id;
        this.cpf = cpf;
        this.idPauta = idPauta;
        this.voto = voto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Long getIdPauta() {
        return idPauta;
    }

    public void setIdPauta(Long idPauta) {
        this.idPauta = idPauta;
    }

    public String getVoto() {
        return voto;
    }

    public void setVoto(String voto) {
        this.voto = voto;
    }
}
