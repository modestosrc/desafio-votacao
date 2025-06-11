package mateus.votos.dto;

public class VotoDTO {
    private String cpf;
    private Long idPauta;
    private String voto;

    public VotoDTO(String cpf, Long idPauta, String voto) {
        this.cpf = cpf;
        this.idPauta = idPauta;
        this.voto = voto;
    }

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
