package mateus.votos.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.stereotype.Repository;

import mateus.votos.dto.SessaoDTO;
import mateus.votos.model.Sessao;

@Repository
public class SessaoRepository {

    private Connection connection;
    private PreparedStatement preparedStatement;

    public SessaoRepository() {
        try {
            // TODO:
            // Mudar o caminho do banco de dados para um local mais adequado
            this.connection = DriverManager.getConnection("jdbc:sqlite:src/main/resources/votos.db");
            createTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createTable() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS sessao (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "id_pauta INTEGER NOT NULL, " +
                "data_inicio TEXT NOT NULL, " +
                "data_fim TEXT NOT NULL" +
                ")";
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.executeUpdate();
    }

    public Sessao save(SessaoDTO sessaoDTO) throws SQLException {
        String sql = "INSERT INTO sessao (id_pauta, data_inicio, data_fim) VALUES (?, ?, ?)";
        preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
        preparedStatement.setLong(1, sessaoDTO.getIdPauta());
        preparedStatement.setString(2, sessaoDTO.getDataInicio());
        preparedStatement.setString(3, sessaoDTO.getDataFim());
        preparedStatement.executeUpdate();

        ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
        if (generatedKeys.next()) {
            return new Sessao(generatedKeys.getLong(1), sessaoDTO.getIdPauta(), sessaoDTO.getDataInicio(),
                    sessaoDTO.getDataFim());
        } else {
            throw new SQLException("Creating session failed, no ID obtained.");
        }
    }

    public ArrayList<Sessao> findAll() throws SQLException {
        String sql = "SELECT * FROM sessao";
        preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();

        ArrayList<Sessao> sessoes = new ArrayList<>();
        while (resultSet.next()) {
            sessoes.add(new Sessao(resultSet.getLong("id"), resultSet.getLong("id_pauta"),
                    resultSet.getString("data_inicio"), resultSet.getString("data_fim")));
        }
        return sessoes;
    }

    public Sessao findById(Long id) throws SQLException {
        String sql = "SELECT * FROM sessao WHERE id = ?";
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setLong(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            return new Sessao(resultSet.getLong("id"), resultSet.getLong("id_pauta"),
                    resultSet.getString("data_inicio"), resultSet.getString("data_fim"));
        } else {
            throw new SQLException("Session not found with ID: " + id);
        }
    }

    public void deleteById(Long id) throws SQLException {
        String sql = "DELETE FROM sessao WHERE id = ?";
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setLong(1, id);
        preparedStatement.executeUpdate();
    }

    public Sessao update(Sessao sessao) throws SQLException {
        String sql = "UPDATE sessao SET id_pauta = ?, data_inicio = ?, data_fim = ? WHERE id = ?";
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setLong(1, sessao.getIdPauta());
        preparedStatement.setString(2, sessao.getDataInicio());
        preparedStatement.setString(3, sessao.getDataFim());
        preparedStatement.setLong(4, sessao.getId());
        int rowsAffected = preparedStatement.executeUpdate();

        if (rowsAffected > 0) {
            return sessao;
        } else {
            throw new SQLException("Failed to update session with ID: " + sessao.getId());
        }
    }
}
