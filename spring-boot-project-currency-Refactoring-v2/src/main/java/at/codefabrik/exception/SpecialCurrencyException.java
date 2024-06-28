package at.codefabrik.exception;

public class SpecialCurrencyException extends RuntimeException {
    public SpecialCurrencyException(String special_currency) {
        super(special_currency);
    }
}
