package woo.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import woo.core.interfaces.PaymentMode;

public abstract class Transaction implements Serializable{
    
    /**
     *
     */
    private static final long serialVersionUID = -1962434046614834210L;
    
    private static int _instanceCounter = 0;
    private int _id;
    private int _cost;
    private List<Sale> _paidSales = new ArrayList<Sale>();
    private List<Order> _paidOrders = new ArrayList<Order>();

    public Transaction() {
        _id = _instanceCounter++;
    }

    public int getId() { return _id; }

    public abstract Product getProduct();
    public abstract String toString();
}
