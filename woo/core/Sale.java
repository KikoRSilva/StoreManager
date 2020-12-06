package woo.core;

import woo.core.interfaces.Payment;

public class Sale implements Payment extends Transaction{

    private Client _client;
    private int _deadline;
    private Product _product;
    private int _amount;
    private int _cost;
    private int _date;
    /**
     *
     */
    private static final long serialVersionUID = 7352783027728283802L;
    
    public Sale(Client c, int deadline, Product p, int amount) {
        super();
        _client = c;
        _deadline = deadline;
        _product = p;
        _amount = amount;
        _cost = p.getPrice() * amount;
        
    }

    public void pay() {
        
    }
}
