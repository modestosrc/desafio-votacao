package mateus.votos.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.stereotype.Repository;

import mateus.votos.dto.PautaDTO;
import mateus.votos.model.Pauta;

@Repository
public class PautaRepository {

    private Connection connection;
    private PreparedStatement preparedStatement;

    /**
     * Constructor that initializes the database connection and creates the pauta
     * table if it does not exist.
     */
    public PautaRepository() {
        try {
            this.connection = DriverManager.getConnection("jdbc:sqlite:src/main/resources/votos.db");
            createTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates the pauta table if it does not exist.
     *
     * @throws SQLException If there is an error during the database operation.
     */
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

    /**
     * * Saves a new Pauta to the database.
     *
     * @param pautaDTO The DTO containing the details of the Pauta to be saved.
     * @return The saved Pauta object with its generated ID.
     * @throws SQLException If there is an error during the database operation.
     */
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
            return new Pauta(id, pautaDTO.getName(), pautaDTO.getConteudo(), pautaDTO.getStatus());
        } else {
            throw new SQLException("Failed to insert pauta, no ID obtained.");
        }
    }

    /**
     * Updates an existing Pauta in the database.
     *
     * @param pauta The Pauta object containing the updated details.
     * @return The updated Pauta object.
     * @throws SQLException If there is an error during the database operation.
     */
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

    /**
     * Retrieves all Pautas from the database that are not closed or deleted.
     *
     * @return A array of Pauta objects.
     * @throws SQLException If there is an error during the database operation.
     */
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

    /**
     * Retrieves a Pauta by its ID from the database.
     *
     * @param id The ID of the Pauta to be retrieved.
     * @return The Pauta object with the specified ID.
     * @throws SQLException If there is an error during the database operation or if
     *                      the Pauta is not found.
     */
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

    @Deprecated
    /**
     * Retrieves a Pauta by its name from the database.
     *
     * This method does not consider if there are multiple Pautas with the same
     * name. It will return the first Pauta found with the specified name that is
     * not closed or deleted.
     *
     * @param name The name of the Pauta to be retrieved.
     * @return The Pauta object with the specified name.
     * @throws SQLException If there is an error during the database operation or if
     *                      the Pauta is not found.
     */
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

    /**
     * Deletes a Pauta by its ID from the database.
     *
     * This method marks the Pauta as deleted by updating its status to 'DELETED'.
     * It does not physically remove the record from the database.
     *
     * @param id The ID of the Pauta to be deleted.
     * @throws SQLException If there is an error during the database operation or if
     *                      the Pauta is not found.
     */
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
