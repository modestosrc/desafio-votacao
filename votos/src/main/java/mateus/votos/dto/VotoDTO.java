package mateus.votos.dto;

public class VotoDTO {
    private String cpf;
    private int idPauta;
    private String voto;

    public VotoDTO(String cpf, int idPauta, String voto) {
        this.cpf = cpf;
        this.idPauta = idPauta;
        this.voto = voto;
    }

    public String getCpf() {
        return cpf;
    }

    public int getIdPauta() {
        return idPauta;
    }

    public String getVoto() {
        return voto;
    }

}
