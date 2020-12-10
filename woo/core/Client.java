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
    private clientClassification _classification;
    private int _valueOfPurchaseDone;
    private int _valueOfPurchasePaid;
    private double _points;
    private List<Notification> _notifications;

    public Client(String key,String name, String address) {
        _name = name;
        _address = address;
        _id = key;
        _classification = clientClassification.NORMAL;
        _notifications = new ArrayList<Notification>();
    }


/////////////////////////////////////////////// GETTERS FUNCTIONS ////////////////////////////////////////////////////
    public String getId() {return _id;}

    public String getName() {return _name;}

    public String getAddress() {return _address;}

    public List<Notification> getNotifications() {return _notifications;}

    public String getClassification() {return _classification.toString();}

    public int getValueOfPurchaseDone() {return _valueOfPurchaseDone;}

    public int getValueOfPurchasePaid() {return _valueOfPurchasePaid;}

    public double getPoints() { return _points; }

/////////////////////////////////////////////// SETTERS FUNCTIONS ////////////////////////////////////////////////////
    public void setClassification(clientClassification classification) {
        _classification = classification;
    }
/////////////////////////////////////////////// OTHER FUNCTIONS ////////////////////////////////////////////////////
    public void addNotification(Notification n) {
        _notifications.add(n);
    }

    public void addPoints(double p) {
        _points += p;
        if (_points > 2000)
            setClassification(clientClassification.SELECTION);
        if (_points > 25000)
            setClassification(clientClassification.ELITE);
    }

    public void removePoints(double p) {
        _points -= p;
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
