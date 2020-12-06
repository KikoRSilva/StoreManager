package woo.core;

public class Container extends Box{
    
    /**
     *
     */
    private static final long serialVersionUID = -2746154656755255275L;

    private String _qualityLevel;

    public Container(String id, int price, int criticalValue, Supplier s, String servType, String qualityLevel) {

        super(id, price, criticalValue, s, servType);
        _qualityLevel = qualityLevel;
    }

    /////////////////////////////////////////////// GETTERS FUNCTIONS ////////////////////////////////////////////////////
    public String getQualitylevel() { return _qualityLevel; }

    /////////////////////////////////////////////// OTHER FUNCTIONS ////////////////////////////////////////////////////
    public String toString() {
    	return "CONTAINER" + " | " + super.getId() + " | " + super.getSupplier().getId() + " | " + super.getPrice() + " | " + super.getCriticalValue() + " | " + super.getStock() + " | " + super.getType() + " | " + getQualitylevel();

    }
}
