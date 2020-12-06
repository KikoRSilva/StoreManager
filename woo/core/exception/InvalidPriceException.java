package woo.core.exception;

public class InvalidPriceException extends Exception{
    
    private static final long serialVersionUID = -9138093334784049755L;

    public InvalidPriceException(String e) {
        super(e);
    }
}
