package mateus.votos.repository;

import mateus.votos.dto.ResultadoDTO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Repository;

@Repository
public class ResultadoRepository {

    private Connection connection;
    private PreparedStatement preparedStatement;

    public ResultadoRepository() {
        try {
            this.connection = DriverManager.getConnection("jdbc:sqlite:src/main/resources/votos.db");
            createTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createTable() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS resultado (" +
                "id_pauta INTEGER PRIMARY KEY, " +
                "votos_sim INTEGER NOT NULL, " +
                "votos_nao INTEGER NOT NULL" +
                ")";
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.executeUpdate();
    }

    public ResultadoDTO save(ResultadoDTO resultadoDTO) throws SQLException {
        String sql = "INSERT INTO resultado (id_pauta, votos_sim, votos_nao) VALUES (?, ?, ?)";
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setLong(1, resultadoDTO.getIdPauta());
        preparedStatement.setInt(2, resultadoDTO.getVotosSim());
        preparedStatement.setInt(3, resultadoDTO.getVotosNao());
        preparedStatement.executeUpdate();
        return resultadoDTO;
    }

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
