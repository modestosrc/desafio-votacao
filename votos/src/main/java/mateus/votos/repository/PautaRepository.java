package mateus.votos.repository;

import mateus.votos.dto.PautaDTO;
import mateus.votos.model.Pauta;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;

import org.springframework.stereotype.Repository;

@Repository
public class PautaRepository {

    private Connection connection;
    private PreparedStatement preparedStatement;

    public PautaRepository() {
        try {
            this.connection = DriverManager.getConnection("jdbc:sqlite:src/main/resources/votos.db");
            createTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createTable() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS pauta (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT NOT NULL, " +
                "content TEXT NOT NULL, " +
                "status TEXT NOT NULL" +
                ")";
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.executeUpdate();
    }

    public Pauta save(PautaDTO pautaDTO) throws SQLException {
        String sql = "INSERT INTO pauta (name, content, status) VALUES (?, ?, ?)";
        preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, pautaDTO.getName());
        preparedStatement.setString(2, pautaDTO.getConteudo());
        preparedStatement.setString(3, "PENDING");
        preparedStatement.executeUpdate();
        ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
        if (generatedKeys.next()) {
            Long id = generatedKeys.getLong(1);
            return new Pauta(id, pautaDTO.getName(), pautaDTO.getConteudo(), "PENDING");
        } else {
            throw new SQLException("Failed to insert pauta, no ID obtained.");
        }
    }

    public Pauta update(Pauta pauta) throws SQLException {
        String sql = "UPDATE pauta SET name = ?, content = ?, status = ? WHERE id = ?";
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, pauta.getName());
        preparedStatement.setString(2, pauta.getContent());
        preparedStatement.setString(3, pauta.getStatus());
        preparedStatement.setLong(4, pauta.getId());
        int rowsAffected = preparedStatement.executeUpdate();

        if (rowsAffected > 0) {
            return pauta;
        } else {
            throw new SQLException("Failed to update pauta with id: " + pauta.getId());
        }
    }

    public ArrayList<Pauta> findAll() throws SQLException {
        String sql = "SELECT * FROM pauta WHERE status not in ('CLOSED', 'DELETED')";
        preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();

        ArrayList<Pauta> pautas = new ArrayList<>();
        while (resultSet.next()) {
            Long id = resultSet.getLong("id");
            String name = resultSet.getString("name");
            String content = resultSet.getString("content");
            String status = resultSet.getString("status");
            Pauta pauta = new Pauta(id, name, content, status);
            pautas.add(pauta);
        }

        return pautas;
    }

    public Pauta findById(Long id) throws SQLException {
        String sql = "SELECT * FROM pauta WHERE id = ? AND status not in ('CLOSED', 'DELETED')";
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setLong(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            String name = resultSet.getString("name");
            String content = resultSet.getString("content");
            String status = resultSet.getString("status");
            return new Pauta(id, name, content, status);
        } else {
            throw new SQLException("Pauta not found with id: " + id);
        }
    }

    // This doestn't handle the case where the name is not unique.
    // It will return the first match found.
    public Pauta findByName(String name) throws SQLException {
        String sql = "SELECT * FROM pauta WHERE name = ? AND status not in ('CLOSED', 'DELETED')";
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, name);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            Long id = resultSet.getLong("id");
            String content = resultSet.getString("content");
            String status = resultSet.getString("status");
            return new Pauta(id, name, content, status);
        } else {
            throw new SQLException("Pauta not found with name: " + name);
        }
    }

    public void deleteById(Long id) throws SQLException {
        String sql = "UPDATE pauta SET status = 'DELETED' WHERE id = ?";
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setLong(1, id);
        int rowsAffected = preparedStatement.executeUpdate();

        if (rowsAffected == 0) {
            throw new SQLException("Failed to delete pauta with id: " + id);
        }
    }
}
