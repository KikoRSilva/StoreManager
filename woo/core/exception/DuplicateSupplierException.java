package woo.core.exception;

public class DuplicateSupplierException extends Exception{
    
    private static final long serialVersionUID = -9138093334784049746L;

    public DuplicateSupplierException(String e) {
        super(e);
    }
}
