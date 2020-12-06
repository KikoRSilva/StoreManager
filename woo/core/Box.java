package woo.core;

public class Box extends Product{
    
    /**
     *
     */
    private static final long serialVersionUID = 6523341675954208191L;

    private String _type;

    public Box(String id, int price, int criticalValue, Supplier s, String type) {
        super(id, price, criticalValue, s);
        _type = type;
    }

    /////////////////////////////////////////////// GETTERS FUNCTIONS ////////////////////////////////////////////////////
    public String getType() { return _type; }

    /////////////////////////////////////////////// OTHER FUNCTIONS ////////////////////////////////////////////////////
    public String toString() {
    	return "BOX" + " | " + super.getId() + " | " + super.getSupplier().getId() + " | " + super.getPrice() + " | " + super.getCriticalValue() + " | " + super.getStock() + " | " + getType();
    }
}
