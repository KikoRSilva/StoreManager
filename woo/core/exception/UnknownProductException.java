package woo.core.exception;

public class UnknownProductException extends Exception{
    /**
     *
     */
    private static final long serialVersionUID = -7936317989715057271L;
    private final String _key;
    public UnknownProductException(String key, String a) {
        super(a);
        _key = key;
    }
    public String getKey() { return _key; }
}
