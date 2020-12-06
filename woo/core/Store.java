package woo.core;

//FIXME import classes (cannot import from pt.tecnico or woo.app)
import java.io.Serializable;


import java.util.List;

import java.util.ArrayList;

import woo.core.exception.*;

import java.io.IOException;
/**
 * Class Store implements a store.
 */
public class Store implements Serializable {

  /** Serial number for serialization. */
  private static final long serialVersionUID = 202009192006L;

  private int _availableBalance;
  private int accountingBalance;
  private List<Client> _clients;
  private List<Supplier> _suppliers;
  private List<Product> _products;
  private List<Sale> _sales;
  private int _date;
  private List<Notification> _notifications;

  public Store() {

    _clients = new ArrayList<Client>();
    _suppliers = new ArrayList<Supplier>();
    _products = new ArrayList<Product>();
    _notifications = new ArrayList<Notification>();
    _date = 0; // dispensavel por so para ter a certeza
  };


  /////////////////////////////////// REGISTER FUNCTIONS /////////////////////////////////////////////////
  /**
   * @param id of the client
   * @param name of the client
   * @param address of the client
   * @throws DuplicateClientException
   */
  public void registerClient(String id, String name, String address) throws DuplicateClientException {

    for (Client i : _clients){

      if (i.getId().equals(id)) 
        throw new DuplicateClientException("Client already exists.");
    }

    Client a = new Client(id, name, address);
    _clients.add(a);
    
    for(Product p : _products) {
      p.getNotification().getClients().add(a);
    }
  }

  /**
   * @param id of the supplier
   * @param name of the supplier
   * @param address of the supplier
   * @throws DuplicateSupplierException
   */
  public void registerSupplier(String id, String name, String address) throws DuplicateSupplierException {

    for (Supplier i : _suppliers) {

      if (i.getId().equals(id)) 
        throw new DuplicateSupplierException("Supplier already exists.");
    }

    Supplier a = new Supplier(id, name, address);
    _suppliers.add(a);
  }

  /**
   * @param id of the box
   * @param price of the box
   * @param criticalValue of the box
   * @param s supplier of the box
   * @param servType is the type of the delivery service
   * @param stock of boxes
   * @throws UnknownTypeException
   */
  public void registerBox(String id, int price, int criticalValue, String idSupplier, String servType) throws UnknownTypeException, UnknownSupplierException, InvalidPriceException {

    Supplier s = getSupplier(idSupplier);

    for (serviceType i : serviceType.values() ) {

      if (servType.equals(i.toString())) {

        if (price < 0)
          throw new InvalidPriceException("Price must be an Integer positive.");

        Box a = new Box(id, price, criticalValue, s, servType);
        _products.add(a);
        a.getNotification().setInterestedClients(getAllClients());
        return;
      }
    }
    throw new UnknownTypeException("That service type does not exists.");
  }

  public void registerBook(String id, String title, String author, String isbn, String idSupplier, int price, int criticalValue) throws UnknownSupplierException, InvalidPriceException{

    Supplier s = getSupplier(idSupplier);
    if (price < 0)
      throw new InvalidPriceException("Price must be an Integer positive.");
    Book a = new Book(id, price, criticalValue, s, title, author, isbn);
    _products.add(a);
    a.getNotification().setInterestedClients(getAllClients());
  }

  public void registerContainer(String id, int price, int criticalValue, String idSupplier, String servType, String servLevel) throws UnknownTypeException, UnknownLevelException, UnknownSupplierException, InvalidPriceException {

    int EXISTS = 0;
    Supplier s = getSupplier(idSupplier);

    if (price < 0)
      throw new InvalidPriceException("Price must be an Integer positive.");

    for (serviceType i : serviceType.values())

      if (servType == i.toString())

        EXISTS = 1;

    if (EXISTS == 1){

      for (serviceLevel j : serviceLevel.values())

        if (servLevel.equals(j.toString())) {

          Container a = new Container(id, price, criticalValue, s, servType, servLevel);
          _products.add(a);
          a.getNotification().setInterestedClients(getAllClients());
          return;
        }
      throw new UnknownLevelException("That service level does not exist.");
    }
    throw new UnknownTypeException("That service type does not exist.");
  }

  public void registerSale(String cid, int deadline, String pid, int amount) throws UnknownClientException, UnknownProductException, DeniedSaleException {
    Client c = getClient(cid);
    Product p = getProduct(pid);
    if (p.getStock() - amount < 0)
      throw new DeniedSaleException("Not enough stock!");
    else {
      Sale s = new Sale(c, deadline, p, amount);
      _sales.add(s);
    }
  }

  /////////////////////////////////////////////// GETTERS FUNCTIONS //////////////////////////////////////////////////////
  /**
   * @param id of the box
   * @throws UnknownClientException
   */
  public Client getClient(String id) throws UnknownClientException {

      for (Client i : _clients){

        if (i.getId().equals(id)) return i;
      }
      throw new UnknownClientException("Client does not exists.");
  }

  /**
   * @return all the clients registered
   */
  public List<Client> getAllClients() { return _clients; }

   /**
   * @return all the products registered
   */
  public List<Product> getAllProducts() { return _products; }

   /**
   * @return all the suppliers registered
   */
  public List<Supplier> getAllSuppliers() { return _suppliers; }

  /**
   * @param id of the supplier
   * @throws UnknownSupplierException
   */
  public Supplier getSupplier(String id) throws UnknownSupplierException {

    for (Supplier i : _suppliers) {

      if (i.getId().equals(id)) return i;
    }
    throw new UnknownSupplierException("Supplier does not exists.");
  }

  /**
   * @return the current date
   */
  public int getDate() {return _date;}

  /**
   * @param id of the product
   * @return the product
   * @throws UnknownProductException
   */
  public Product getProduct(String id) throws UnknownProductException{
    
    for (Product p : _products)

      if (p.getId().equals(id))

        return p;
    
    throw new UnknownProductException("That product does not exist.");
  }

  public int getProductStock(String id) throws UnknownProductException {
    return getProduct(id).getStock();
  }

  /////////////////////////////////////////////// PARSING FUNCTIONS ////////////////////////////////////////////////////
  /**
   * @param id of the box
   * @param price of the box
   * @param criticalValue of the box
   * @param idSupplier of the supplier
   * @param servType , type of delivery service 
   * @param stock of the box
   * @throws UnknownSupplierException
   * @throws UnknownTypeException
   * @throws InvalidPriceException
   */
  public void addBox(String id, int price, int criticalValue, String idSupplier, String servType, int stock) throws UnknownSupplierException, UnknownTypeException, InvalidPriceException{
    
    Supplier s = getSupplier(idSupplier);

    for (serviceType i : serviceType.values()) {

      if (servType.equals(i.toString())) {

        if (price < 0)
          throw new InvalidPriceException("Price must be an Integer positive.");

        Box a = new Box(id, price, criticalValue, s, servType);
        a.addStock(stock);
        _products.add(a);
        a.getNotification().setInterestedClients(getAllClients());
        return;
      }
    }
    throw new UnknownTypeException("That service type does not exists.");
  }

  /**
   * @param id of the book
   * @param title of the book
   * @param author of the book
   * @param isbn of the book
   * @param idSupplier of the supplier
   * @param price of the book
   * @param criticalValue of the book
   * @param stock of the book
   * @throws UnknownSupplierException
   * @throws InvalidPriceException
   */
  public void addBook(String id, String title, String author, String isbn, String idSupplier, int price, int criticalValue, int stock) throws UnknownSupplierException, InvalidPriceException{

    Supplier s = getSupplier(idSupplier);

    if (price < 0)

      throw new InvalidPriceException("Price must be an Integer positive.");

    Book a = new Book(id, price, criticalValue, s, title, author, isbn);
    a.addStock(stock);
    _products.add(a);
    a.getNotification().setInterestedClients(getAllClients());
  }

  /**
   * @param id of the container
   * @param price of the container
   * @param criticalValue of the container
   * @param idSupplier of the supplier
   * @param servType , type of delivery service 
   * @param servLevel , quality level of the delivery service
   * @param stock of the container
   * @throws UnknownSupplierException
   * @throws UnknownTypeException
   * @throws InvalidPriceException
   */
  public void addContainer(String id, int price, int criticalValue, String idSupplier, String servType, String servLevel, int stock) throws UnknownTypeException, UnknownLevelException, UnknownSupplierException, InvalidPriceException {

    int EXISTS = 0;
    Supplier s = getSupplier(idSupplier);

    if (price < 0)
      throw new InvalidPriceException("Price must be an Integer positive.");

    for (serviceType i : serviceType.values())

      if (servType.equals(i.toString()))

        EXISTS = 1;

    if (EXISTS == 1){

      for (serviceLevel j : serviceLevel.values())

        if (servLevel.equals(j.toString())) {

          Container a = new Container(id, price, criticalValue, s, servType, servLevel);
          a.addStock(stock);
          _products.add(a);
          a.getNotification().setInterestedClients(getAllClients());
          return;
        }
      throw new UnknownLevelException("That service level does not exist.");
    }
    throw new UnknownTypeException("That service type does not exist.");
  }

  /////////////////////////////////////////////// OTHER FUNCTIONS ////////////////////////////////////////////////////
  /**
   * @param numberOfDays to advance in date attribute
   * @throws InvalidCoreDateException
   */
  public void advanceDate(int numberOfDays) throws InvalidCoreDateException{

    if (numberOfDays < 0) throw new InvalidCoreDateException("Date must be a positive number.");
    _date += numberOfDays;
  }

  /**
   * @param numberOfDays to advance in date attribute
   * @throws InvalidCoreDateException
   */
  public void changePrice(String id, int price) throws InvalidPriceException, UnknownProductException{

    if (price < 0) 
      throw new InvalidPriceException("Price must be an integer positive.");

    for (Product i : _products) {

      if (i.getId().equals(id)) {
        if (i.getPrice() > price) 
          sendNotification("BARGAIN", id, price);
        i.changePrice(price);
      }
    }
    throw new UnknownProductException("That product does not exist.");
  }

  public void clearClientNotifications(String id) throws UnknownClientException {
    Client c = getClient(id);
    c.clearNotifications();
  }

  /**
   * @param description of the notification (NEW OR BARGAIN)
   * @throws IOException
   * @throws BadEntryException
   * @throws ImportFileException
   */
  public void sendNotification(String description, String idProduct, int price) throws UnknownProductException{

    Notification n = getProduct(idProduct).getNotification();
    n.sendNotification(description, idProduct, price);
  }




  // FIXME define methods

  /**
   * @param txtfile filename to be loaded.
   * @throws IOException
   * @throws BadEntryException
   * @throws ImportFileException
   */
  public void importFile(String txtfile) throws IOException, BadEntryException, ImportFileException {

    try {

      MyParser mp = new MyParser(this);
      mp.parseFile(txtfile);

    } catch (IOException | BadEntryException e) {
      e.printStackTrace();
      throw new ImportFileException(txtfile);
    }
  }
}
