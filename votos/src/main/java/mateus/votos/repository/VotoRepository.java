package mateus.votos.repository;

import mateus.votos.model.Voto;
import mateus.votos.dto.VotoDTO;
import mateus.votos.exception.AlreadyExistsException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Repository;

// Não precisamos mais do que isso para o repositório de votos, jã que os
// resultados serão obtidos atraves de outro serviço.
@Repository
public class VotoRepository {

    private Connection connection;
    private PreparedStatement preparedStatement;

    public VotoRepository() {
        try {
            this.connection = DriverManager.getConnection("jdbc:sqlite:src/main/resources/votos.db");
            createTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createTable() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS voto (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "cpf TEXT NOT NULL, " +
                "id_pauta INTEGER NOT NULL, " +
                "voto TEXT NOT NULL" +
                ")";
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.executeUpdate();
    }

    public Voto save(VotoDTO votoDTO) throws Exception {
        // Garantir que o voto não exista para o mesmo CPF e Pauta ID
        if (findByCpfAndPauta(votoDTO.getCpf(), votoDTO.getIdPauta()) != null) {
            throw new AlreadyExistsException("Voto já registrado para o CPF: " + votoDTO.getCpf() +
                    " e Pauta ID: " + votoDTO.getIdPauta());
        }

        String sql = "INSERT INTO voto (cpf, id_pauta, voto) VALUES (?, ?, ?)";
        preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, votoDTO.getCpf());
        preparedStatement.setLong(2, votoDTO.getIdPauta());
        preparedStatement.setString(3, votoDTO.getVoto());
        preparedStatement.executeUpdate();

        ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
        if (generatedKeys.next()) {
            return new Voto(generatedKeys.getLong(1), votoDTO.getCpf(), votoDTO.getIdPauta(), votoDTO.getVoto());
        } else {
            throw new SQLException("Creating vote failed, no ID obtained.");
        }
    }

    public Voto findByCpfAndPauta(String cpf, Long idPauta) throws SQLException {
        String sql = "SELECT * FROM voto WHERE cpf = ? AND id_pauta = ?";
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, cpf);
        preparedStatement.setLong(2, idPauta);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            return new Voto(resultSet.getLong("id"), resultSet.getString("cpf"),
                    resultSet.getLong("id_pauta"), resultSet.getString("voto"));
        } else {
            return null;
        }
    }
}
