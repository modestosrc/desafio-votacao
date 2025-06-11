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

/**
 * Repositório para gerenciar as operações de persistência relacionadas ao Voto.
 *
 * Este repositório utiliza JDBC para interagir com um banco de dados SQLite.
 */
@Repository
public class VotoRepository {

    private Connection connection;
    private PreparedStatement preparedStatement;

    /**
     * Construtor para inicializar a conexão com o banco de dados e criar a
     * tabela voto se não existir.
     */
    public VotoRepository() {
        try {
            this.connection = DriverManager.getConnection("jdbc:sqlite:src/main/resources/votos.db");
            createTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Cria a tabela voto no banco de dados se ela não existir.
     *
     * @throws SQLException Caso ocorra um erro ao executar a criação da tabela.
     */
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

    /**
     * Insere um novo Voto no banco de dados.
     *
     * @param votoDTO O objeto VotoDTO contendo os detalhes do voto a ser salvo.
     * @return O objeto Voto com os detalhes inseridos, incluindo o ID gerado pelo
     *         banco de dados.
     * @throws Exception Se já existir um voto para o mesmo CPF e Pauta ID.
     */
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

    /**
     * Busca um voto pelo CPF e ID da Pauta.
     *
     * @param cpf     O CPF do votante.
     * @param idPauta O ID da pauta.
     * @return O objeto Voto correspondente ou null se não encontrado.
     * @throws SQLException Se ocorrer um erro ao executar a consulta.
     */
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
