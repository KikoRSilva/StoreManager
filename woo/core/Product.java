package woo.core;

import java.io.*;

public abstract class Product implements Serializable, Comparable<Product>{

    /**
     *
     */
    private static final long serialVersionUID = 4022894694315653903L;

    private String _id;
    private Supplier _supplier;
    private int _price;
    private int _criticalValue;
    private int _stock;

    public Product(String id, int price, int criticalValue, Supplier s) {

        _id = id;
        _price = price;
        _criticalValue = criticalValue;
        _supplier = s;
    }

/////////////////////////////////////////////// GETTERS FUNCTIONS ////////////////////////////////////////////////////

    public int getPrice() { return _price; }
    public String getId() { return _id; }
    public Supplier getSupplier() { return _supplier; }
    public int getCriticalValue() { return _criticalValue; }
    public int getStock() {return _stock; }
    public abstract String getNameMode();

/////////////////////////////////////////////// OTHER FUNCTIONS ////////////////////////////////////////////////////

    public void changePrice(int p) {
        _price = p;
    }

    public void addStock(int stock) {
        _stock += stock;
    }

    public void removeStock(int i) {
        _stock -= i;
    }

    public abstract String toString();
    
    @Override
    public int compareTo(Product obj) {
        return _id.compareTo(obj._id);
    }
}
