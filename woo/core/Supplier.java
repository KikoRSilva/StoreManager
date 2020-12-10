package woo.core;

import java.io.Serializable;

public class Supplier implements Serializable{
    /**
     *
     */
    private static final long serialVersionUID = 2128387125535311578L;

    private String _id;
    private String _name;
    private String _address;
    private Boolean _isAuthorized;

    public Supplier(String key, String name, String address){
        _id = key;
        _name = name;
        _address = address;
        _isAuthorized = true;
    }

//////////////////////////////////////////// GETTERS FUNCTION ////////////////////////////////////////////

    public String getId() { return _id; }

    public String getName() { return _name; }

    public String getAddress() { return _address; }

    public Boolean isAuthorized() { return _isAuthorized; }

///////////////////////////////////////////// OTHERS FUNCTION ////////////////////////////////////////////
    public String toString() { return getId() + " | " + getName() + " | " + getAddress() + " | "; }

    public void toggleAuthorization() { _isAuthorized = !_isAuthorized; }

}
