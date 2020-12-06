package woo.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Client implements Serializable{

    /**
     *
     */
    private static final long serialVersionUID = 2939000231126670024L;

    private String _id;
    private String _name;
    private String _address;
    private List<Transaction> _transactions;
    private clientClassification _classification;
    private int _valueOfPurchaseDone;
    private int _valueOfPurchasePaid;
    private int _points;
    protected List<String> _notifications;

    public Client(String key,String name, String address) {
        _name = name;
        _address = address;
        _id = key;
        _transactions = new ArrayList<Transaction>();
        _classification = clientClassification.NORMAL;
        _notifications = new ArrayList<String>();
    }


  /////////////////////////////////////////////// GETTERS FUNCTIONS ////////////////////////////////////////////////////
    public String getId() {return _id;}

    public String getName() {return _name;}

    public String getAddress() {return _address;}

    public List<String> getNotifications() {return _notifications;}

    public String getClassification() {return _classification.toString();}

    public int getValueOfPurchaseDone() {return _valueOfPurchaseDone;}

    public int getValueOfPurchasePaid() {return _valueOfPurchasePaid;}

    public void getTransactionsHistory() {
        // ...
    }

/////////////////////////////////////////////// OTHER FUNCTIONS ////////////////////////////////////////////////////
    public void buy() {
        // ...
    }

    public void pay() {
        // ...
    }

    public void toogleNotifications() {
        // ...
    }

    public void changeNotificationMethod() {
        // ...
    }

    public void clearNotifications() {
        _notifications.clear();
    }

    public String toString() {
        return _id + " | " + _name + " | " + _address + " | " + _classification + " | " + _valueOfPurchaseDone + " | " + _valueOfPurchasePaid;
    }

    public boolean equals(Object obj) {
        return obj != null && obj instanceof String &&
            _id.equals(((Client)obj)._id);
    }
}
