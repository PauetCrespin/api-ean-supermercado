package prueba.supermercado.ean.exceptions;

public class InvalidEanException extends RuntimeException {

    public InvalidEanException(String message) {
        super(message);
    }
}
