package mateus.votos.repository;

import mateus.votos.dto.ResultadoDTO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Repository;

/**
 * Repositório para gerenciar as operações de persistência relacionadas ao
 * Resultado.
 *
 * Este repositório utiliza JDBC para interagir com um banco de dados SQLite.
 */
@Repository
public class ResultadoRepository {

    private Connection connection;
    private PreparedStatement preparedStatement;

    /**
     * Construtor para inicializar a conexão com o banco de dados e criar a tabela
     * resultado se não existir.
     */
    public ResultadoRepository() {
        try {
            this.connection = DriverManager.getConnection("jdbc:sqlite:src/main/resources/votos.db");
            createTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Cria a tabela resultado no banco de dados se ela não existir.
     *
     * @throws SQLException Caso ocorra um erro ao executar a criação da tabela.
     */
    private void createTable() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS resultado (" +
                "id_pauta INTEGER PRIMARY KEY, " +
                "votos_sim INTEGER NOT NULL, " +
                "votos_nao INTEGER NOT NULL" +
                ")";
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.executeUpdate();
    }

    /**
     * Insere um novo Resultado no banco de dados.
     *
     * @param resultadoDTO O objeto ResultadoDTO contendo os detalhes do resultado a
     *                     ser salvo.
     * @return O objeto ResultadoDTO com os detalhes inseridos.
     * @throws SQLException Caso ocorra um erro ao executar a inserção.
     */
    public ResultadoDTO save(ResultadoDTO resultadoDTO) throws SQLException {
        String sql = "INSERT INTO resultado (id_pauta, votos_sim, votos_nao) VALUES (?, ?, ?)";
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setLong(1, resultadoDTO.getIdPauta());
        preparedStatement.setInt(2, resultadoDTO.getVotosSim());
        preparedStatement.setInt(3, resultadoDTO.getVotosNao());
        preparedStatement.executeUpdate();
        return resultadoDTO;
    }

    /**
     * Busca um Resultado pelo ID da Pauta.
     *
     * @param idPauta O ID da Pauta para a qual se deseja obter o resultado.
     * @return Um objeto ResultadoDTO contendo os detalhes do resultado, ou null se
     *         não encontrado.
     * @throws SQLException Caso ocorra um erro ao executar a consulta.
     */
    public ResultadoDTO findByIdPauta(Long idPauta) throws SQLException {
        String sql = "SELECT * FROM resultado WHERE id_pauta = ?";
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setLong(1, idPauta);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            return new ResultadoDTO(
                    resultSet.getLong("id_pauta"),
                    resultSet.getInt("votos_sim"),
                    resultSet.getInt("votos_nao"));
        }
        return null;
    }

    /**
     * Atualiza os resultados de votos no banco de dados.
     *
     * Este método calcula a soma dos votos "SIM" e "NÃO" para cada pauta e atualiza
     * a tabela resultado com esses valores.
     *
     * NOTE: Isso deve ser chamado de tempo em tempo, e não a cada operação.
     * Porque ele faz uma varredura completa na tabela de votos.
     *
     * @throws SQLException Caso ocorra um erro ao executar a atualização.
     */
    public void update() throws SQLException {
        String sql = "SELECT id_pauta, " +
                "SUM(CASE WHEN voto = 'SIM' THEN 1 ELSE 0 END) AS votos_sim, " +
                "SUM(CASE WHEN voto = 'NAO' THEN 1 ELSE 0 END) AS votos_nao " +
                "FROM voto GROUP BY id_pauta";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();

        String updateSql = "UPDATE resultado SET votos_sim = ?, votos_nao = ? WHERE id_pauta = ?";
        PreparedStatement updateStatement = connection.prepareStatement(updateSql);

        while (resultSet.next()) {
            int idPauta = resultSet.getInt("id_pauta");
            int votosSim = resultSet.getInt("votos_sim");
            int votosNao = resultSet.getInt("votos_nao");

            updateStatement.setInt(1, votosSim);
            updateStatement.setInt(2, votosNao);
            updateStatement.setInt(3, idPauta);
            updateStatement.executeUpdate();
        }
    }
}
