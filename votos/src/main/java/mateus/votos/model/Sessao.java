package mateus.votos.model;

/**
 * Representa uma sessão de votação no sistema.
 *
 * Contém informações como o ID da pauta associada, data de início e data de fim da sessão.
 * E seus respectivos métodos de acesso e modificação.
 */
public class Sessao {
    // NOTE: O id é gerado pelo banco de dados
    private Long id;
    private Long idPauta;
    private String dataInicio;
    private String dataFim;

    public Sessao(Long id, Long idPauta, String dataInicio, String dataFim) {
        this.id = id;
        this.idPauta = idPauta;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
    }

    public Long getId() {
        return id;
    }

    public Long getIdPauta() {
        return idPauta;
    }

    public void setIdPauta(Long idPauta) {
        this.idPauta = idPauta;
    }

    public String getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(String dataInicio) {
        this.dataInicio = dataInicio;
    }

    public String getDataFim() {
        return dataFim;
    }

    public void setDataFim(String dataFim) {
        this.dataFim = dataFim;
    }

}
