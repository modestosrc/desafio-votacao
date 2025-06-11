package mateus.votos.exception;

// Crio essa exceção para indicar que um recurso já existe, para que o endpoint
// possa retornar a resposta correta para o usuário.
public class AlreadyExistsException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public AlreadyExistsException() {
        super("Resource already exists");
    }

    public AlreadyExistsException(String message) {
        super(message);
    }

    public AlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
