package woo.core.exception;

import woo.core.Product;

public class WrongSupException extends Exception {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private final Product _product;
    
    public WrongSupException(Product p, String e) {
        super(e);
        _product = p;
    }

    public Product getProduct() { return _product; }
}
