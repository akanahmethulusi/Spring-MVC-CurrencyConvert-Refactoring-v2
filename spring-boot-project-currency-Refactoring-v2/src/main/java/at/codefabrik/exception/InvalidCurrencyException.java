package at.codefabrik.exception;

public class InvalidCurrencyException extends RuntimeException {
    public InvalidCurrencyException(String invalid_currecy) {
        super(invalid_currecy);
    }
}
