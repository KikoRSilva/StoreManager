package woo.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class Transaction implements Serializable{
    
    /**
     *
     */
    private static final long serialVersionUID = -1962434046614834210L;
    
    private static int _id;
    private int _cost;
    private List<Sale> _paidSales = new ArrayList<Sale>();
    private List<Order> _paidOrders = new ArrayList<Order>();

    public Transaction() {
        _id++;
    }

}
