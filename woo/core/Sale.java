package woo.core;

import woo.core.interfaces.PaymentMode;

public class Sale extends Transaction {

    private Client _client;
    private int _deadline;
    private Product _product;
    private int _amount;
    private double _baseValue;
    private int _date;
    private double _toPayValue;
    private boolean _wasPaid;
    private PaymentMode _PaymentMode;
    private static final long serialVersionUID = 7352783027728283802L;
    
    public Sale(Client c, int deadline, Product p, int amount) {
        super();
        _client = c;
        _deadline = deadline;
        _product = p;
        _amount = amount;
        _baseValue = p.getPrice() * amount;
        _wasPaid = false;
    }

    public void setMode(PaymentMode pm) {
        _PaymentMode = pm;
    }

    public double computePayment(Client c, double basevalue, int date, int deadline) {
        return _PaymentMode.computePayment(c, basevalue, date, deadline);
    }

    public double pay() {
        if (!_wasPaid) {
            _toPayValue = computePayment(_client, _baseValue, _date, _deadline);
            _wasPaid = true;
        }
        return _toPayValue;
    }

    public Product getProduct() { return _product; }
    public PaymentMode getPaymentMode() { return _PaymentMode; }
    public Client getClient() { return _client; }
    public int getDeadline() { return _deadline; }
    public int getDate() { return _date; }
    public double getBaseValue() { return _baseValue; }
    public boolean wasPaid() { return _wasPaid; }
    public double getToPayValue() { return _toPayValue; }
    
    public void setToPayValue(double v) {  _toPayValue = v; }
    
    public String toString() {
        if (_wasPaid)
            return super.getId() + " | " + _client.getId() + " | " + _product.getId() + " | " + _amount + " | " + _baseValue + " | " + _toPayValue + " | " + _deadline + " | " + _date;
        return super.getId() + " | " + _client.getId() + " | " + _product.getId() + " | " + _amount + " | " + _baseValue + " | " + _toPayValue + " | " + _deadline;
        }
}
