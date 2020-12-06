package woo.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Notification implements Serializable{

    /**
     *
     */
    private static final long serialVersionUID = -4208213675941923452L;

    private String _idProduct;
    private Product _product;
    private List<Client> _clients;
    private List<String> _type;

    public Notification(Product p){
      _product = p;
      _idProduct = p.getId();
      _clients = new ArrayList<Client>();
      _type = new ArrayList<String>();
    }

    /////////////////////////////////////////////// GETTERS FUNCTIONS ////////////////////////////////////////////////////
    public String getIdProduct() { return _idProduct; }

    public Product getProduct() { return _product; }

    public List<Client> getClients() { return _clients; }

    /////////////////////////////////////////////// SETTERS FUNCTIONS ////////////////////////////////////////////////////
    public void setInterestedClients(List<Client> clients) {
      _clients = clients;
      for (Client c : _clients) {
        _type.add("APP");
      }
    }

    /////////////////////////////////////////////// OTHER FUNCTIONS ////////////////////////////////////////////////////
    public void sendNotification(String description, String id, int price) {
      for (Client c : _clients) {
        c._notifications.add(description + " | " + id  + " | " + price);
      }
    }
}
