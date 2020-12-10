package woo.core;

import java.util.Map;
import java.util.TreeMap;

public class Order extends Transaction{
    
    private static final long serialVersionUID = -7654239663652491878L;
    
    private Supplier _supplier;
    private Map<Product, Integer> _products = new TreeMap<Product, Integer>();
    private double _totalCost;
    private int _date;

    public Order(Supplier s, TreeMap<Product, Integer> products, int date) {
        super();
        _supplier = s;
        for(Map.Entry<Product, Integer> e : products.entrySet()) {
            _totalCost += e.getKey().getPrice() * e.getValue();
        }
        _date = date;
    }
    public double getTotalCost() { return _totalCost; }
    public String toString() {
        return super.getId() + " | " + _supplier.getId() + " | " + Math.round(_totalCost) + " | " + _date;
    }

    public Map<Product, Integer> getAllProducts() { return _products; }
}
