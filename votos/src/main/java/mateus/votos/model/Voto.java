package mateus.votos.model;

/**
 * Representa um voto no sistema de votação.
 *
 * Contém informações como o ID do voto, CPF do votante, ID da pauta associada e o voto em si.
 * E seus respectivos métodos de acesso e modificação.
 */
public class Voto {
    // NOTE: O id é gerado pelo banco de dados
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
