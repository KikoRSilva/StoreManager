package woo.core.exception;

public class DuplicateClientException extends Exception{
    
    private static final long serialVersionUID = -6484101346430220099L;

    public DuplicateClientException(String a) {
        super(a);
    }
}
