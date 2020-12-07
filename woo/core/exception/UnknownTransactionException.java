package woo.core.exception;

public class UnknownTransactionException extends Exception {
    /**
     *
     */
    private static final long serialVersionUID = 3799934238142727102L;

    public UnknownTransactionException(String e) {
        super(e);
    }
}
