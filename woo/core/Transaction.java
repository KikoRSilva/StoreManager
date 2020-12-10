package woo.core;

import java.io.Serializable;

public abstract class Transaction implements Serializable{

    private static final long serialVersionUID = -1962434046614834210L;
    
    private static int _instanceCounter = 0;
    private int _id;

    public Transaction() {
        _id = _instanceCounter++;
    }

    public int getId() { return _id; }

    public abstract String toString();
}
