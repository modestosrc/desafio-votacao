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

/**
 * Repositório para gerenciar as operações de persistência relacionadas à Pauta.
 *
 * Este repositório utiliza JDBC para interagir com um banco de dados SQLite.
 */
@Repository
public class PautaRepository {

    private Connection connection;
    private PreparedStatement preparedStatement;

    /**
     * Construtor para inicializar a conexão com o banco de dados e criar a
     * tabela pauta se não existir.
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
     * Cria a tabela pauta no banco de dados se ela não existir.
     *
     * @throws SQLException Caso ocorra um erro ao executar a criação da tabela.
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
     * Insere uma nova Pauta no banco de dados.
     *
     * @param pautaDTO O objeto PautaDTO contendo os detalhes da pauta a ser salva.
     * @return O objeto Pauta com os detalhes inseridos, incluindo o ID gerado pelo
     *         banco de dados.
     * @throws SQLException Se ocorrer um erro durante a operação de inserção no
     *                      banco de
     *                      dados.
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
     * Atualiza os detalhes de uma Pauta existente no banco de dados.
     *
     * @param pauta O objeto Pauta contendo os detalhes a serem atualizados.
     * @return O objeto Pauta atualizado.
     * @throws SQLException Se ocorrer um erro durante a operação de atualização no
     *                      banco de
     *                      dados ou se a pauta não for encontrada.
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
     * Obtém todas as Pautas do banco de dados que não estão fechadas ou
     * deletadas.
     *
     * @return Uma lista de objetos Pauta que representam todas as pautas ativas.
     * @throws SQLException Se ocorrer um erro durante a operação de consulta no
     *                      banco de
     *                      dados.
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
     * Obtém uma Pauta pelo seu ID do banco de dados.
     *
     * @param id O id da pauta a ser recuperada.
     * @return The Pauta object with the specified ID.
     * @throws SQLException Se ocorrer um erro durante a operação de consulta no
     *                      banco de dados ou se a pauta não for encontrada.
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
     * Obtém uma Pauta pelo seu nome do banco de dados.
     *
     * Esse método não leva em consiferação que multiplas pautas podem ter o
     * mesmo nome.
     *
     * @param name O nome da pauta a ser recuperada.
     * @return O objeto Pauta correspondente ao nome fornecido.
     * @throws SQLException Se ocorrer um erro durante a operação de consulta no
     *                      banco de dados ou se a pauta não for encontrada.
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
     * Marca uma Pauta como deletada no banco de dados.
     * Não remove o registro.
     *
     * Talvez possa ser implementado um método que limpe os registros
     * de tempo em tempo.
     *
     * Ou mova para um armazenamento de backup.
     *
     * @param id O ID da pauta a ser deletada.
     * @throws SQLException Se ocorrer um erro durante a operação de atualização no
     *                      banco de dados ou se a pauta não for encontrada.
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
