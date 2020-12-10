package woo.core;

import java.io.Serializable;


public class Notification implements Serializable{

    /**
     *
     */
    private static final long serialVersionUID = -4208213675941923452L;

    private Product _product;
    private String _description;
    private int _price;

    public Notification(Product p, String description, int price){
      _product = p;
      _description = description;
      _price = price;
    }

    /////////////////////////////////////////////// GETTERS FUNCTIONS ////////////////////////////////////////////////////
    public Product getProduct() { return _product; }
    public String getDescription() { return _description; }
    public int getPrice() { return _price; }
    /////////////////////////////////////////////// OTHER FUNCTIONS ////////////////////////////////////////////////////
    public String toString() { return _description + " | " + _product.getId() + " | " + _price; }
}
