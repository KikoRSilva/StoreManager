package woo.core;

import java.util.ArrayList;
import java.util.List;

public class Order extends Transaction{
    
    private static final long serialVersionUID = -7654239663652491878L;
    
    private Supplier _supplier;
    private List<Product> _products;
    private List<Integer> _quantities;
    private int _totalCost;

    public Order(Supplier s, Product p, int quantity) {
        super();
        _supplier = s;
        List<Product> _products = new ArrayList<Product>();
        List<Integer> _quantities = new ArrayList<Integer>();

        for(int i = 0; i < _products.size(); i++) {
            _totalCost += _products.get(i).getPrice() * _quantities.get(i);
        }
    }
}
