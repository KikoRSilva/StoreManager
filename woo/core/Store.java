package woo.core;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.ArrayList;

import woo.core.Payments.*;
import woo.core.exception.*;

import java.io.IOException;
/**
 * Class Store implements a store.
 */
public class Store implements Serializable {

  /** Serial number for serialization. */
  private static final long serialVersionUID = 202009192006L;

  private double _availableBalance;
  private double _accountingBalance;
  private List<Client> _clients;
  private List<Supplier> _suppliers;
  private List<Product> _products;
  private Map<String, List<Sale>> _sales;
  private Map<String, List<Order>> _orders;
  private int _date;
  private Map<Product, List<Client>> _interestedClients;
  private List<Notification> _notifications;

  public Store() {

    _clients = new ArrayList<Client>();
    _suppliers = new ArrayList<Supplier>();
    _products = new ArrayList<Product>();
    _notifications = new ArrayList<Notification>();
    _interestedClients = new TreeMap<Product, List<Client>>();
    _sales = new TreeMap<String, List<Sale>>(String.CASE_INSENSITIVE_ORDER);
    _orders = new TreeMap<String, List<Order>>(String.CASE_INSENSITIVE_ORDER);
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

    for (Map.Entry<Product, List<Client>> e : _interestedClients.entrySet()) 
      e.getValue().add(a);
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

    if (price < 0)
      throw new InvalidPriceException("Price must be an Integer positive.");

    for (serviceType i : serviceType.values() ) {
      if (servType.equals(i.toString())) {
        Box a = new Box(id, price, criticalValue, s, servType);
        _products.add(a);
        _interestedClients.put(a, _clients);
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
    _interestedClients.put(a, _clients);
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
          _interestedClients.put(a, _clients);
          return;
        }
      throw new UnknownLevelException("That service level does not exist.");
    }
    throw new UnknownTypeException("That service type does not exist.");
  }

  public void registerSale(String cid, int deadline, String pid, int amount) throws UnknownClientException, UnknownProductException, DeniedSaleException {
    
    Client c = getClient(cid);
    Product p = getProduct(pid);

    // check if is there sufficient product to sell
    if (p.getStock() - amount < 0)
     throw new DeniedSaleException("Not enough stock!");

     // update stock
    p.removeStock(amount);

    // store the sale
    Sale s = new Sale(c, deadline, p, amount);
    if (_sales.containsKey(cid))
      _sales.get(cid).add(s);
    else {
      List<Sale> t = new ArrayList<Sale>();
      t.add(s);
      _sales.put(cid, t);
    }

    // update contabilistic balance
    
  }

  public void registerOrder(String sid, TreeMap<Product, Integer> products) throws UnknownSupplierException, WrongSupException, UnauthorizedSupException {
    Supplier s = getSupplier(sid);
    // check if is authorized and the correct one
    if (s.isAuthorized()) {
      for (Map.Entry<Product, Integer> e : products.entrySet()) {
        if (e.getKey().getSupplier() != s)
          throw new WrongSupException(e.getKey(), "This product does not belong to this supplier.");
      }
      // store the order
      Order o = new Order(s, products, _date);
      if (_orders.containsKey(sid))
        _orders.get(sid).add(o);
      else {
        List<Order> t = new ArrayList<Order>();
        t.add(o);
        _orders.put(sid, t);
      }
      // update available balance
      _availableBalance -= o.getTotalCost();
      // update stock and send notification if necessary
      for (Product p : _products) {
        if (products.containsKey(p))
          if (p.getStock() == 0)
            sendNotification("NEW", p, p.getPrice());
          p.addStock(products.get(p));
      } 
    }
    throw new UnauthorizedSupException("This supplier is not authorized for transactions.");
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
    throw new UnknownProductException(id, "That product does not exist.");
  }

  public int getProductStock(String id) {
    for (Product p : _products)
      if (p.getId().equals(id))
        return p.getStock();
    return -1;
  }

  public Transaction getTransaction(int id) throws UnknownTransactionException {
    for (Map.Entry<String, List<Order>> e : _orders.entrySet()) {
      for (Order t : e.getValue())
        if (t.getId() == id)
          return t;
    }
    for (Map.Entry<String, List<Sale>> e : _sales.entrySet()) {
      for (Sale t : e.getValue())
        if (t.getId() == id)
          return t;
    }
    throw new UnknownTransactionException("That transaction does not exists.");
  }

  public List<Order> getAllSupplierTransactions(String id) { return _orders.get(id); }

  public Sale getSale(int id) throws UnknownTransactionException {
    for (Map.Entry<String, List<Sale>> e : _sales.entrySet()) {
      for (Sale t : e.getValue())
        if (t.getId() == id)
          return t;
    }
    throw new UnknownTransactionException("This sale does not exists!");
  }

  public List<Sale> getAllClientTransactions(String id) { return _sales.get(id); }

  public List<Sale> getPaidSales(String id) {
    List<Sale> paidSales = new ArrayList<Sale>();
    for (Sale s : _sales.get(id))
      if (s.wasPaid())
        paidSales.add(s);
    return paidSales;
  }

  public List<Product> getProductsUnderLimitOf(int price) {
    List<Product> underlimit = new ArrayList<Product>();
    for (Product p : _products)
      if (p.getPrice() < price)
        underlimit.add(p);
    return underlimit;
  }

  public double getAvailableBalance() { return _availableBalance; }

  public double getAccountingBalance() {
    updateAccountingBalance();
    return _accountingBalance;
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
        _interestedClients.put(a, _clients);
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
    _interestedClients.put(a, _clients);
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
          _interestedClients.put(a, _clients);
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

    if (numberOfDays < 0) 
      throw new InvalidCoreDateException("Date must be a positive number.");
    _date += numberOfDays;
  }

  /**
   * @param numberOfDays to advance in date attribute
   * @throws InvalidCoreDateException
   */
  public void changePrice(String id, int price) throws InvalidPriceException, UnknownProductException{

    if (price < 0) 
      throw new InvalidPriceException("Price must be an integer positive.");

    for (Product p : _products)
      if (p.getId().equals(id)) {
        if (p.getPrice() > price)
          sendNotification("BARGAIN", p, price);
        p.changePrice(price);
      }
    throw new UnknownProductException(id, "That product does not exist.");
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
  public void sendNotification(String description, Product product, int price) {
    if (_interestedClients.containsKey(product)) {
      Notification n = new Notification(product, description, price);
      for (Client c : _interestedClients.get(product))
        c.addNotification(n);
      _notifications.add(n);
    }
  }

  public void pay(int id) throws UnknownTransactionException, UnknownProductException {

    Sale s = getSale(id);
    Product p = s.getProduct();

    switch(p.getNameMode()) {
      case "BookMode":
        s.setMode(new BookMode());
        break;
      case "BoxMode":
        s.setMode(new BoxMode());
        break;
      case "ContainerMode":
        s.setMode(new ContainerMode());
        break;
      default:
        throw new UnknownProductException(p.getId(), "This product does not exists.");
    }
    double toPay = s.pay();
    _availableBalance += toPay;
  }

  public boolean toggleTransactions(String id) throws UnknownSupplierException {
    Supplier s = getSupplier(id);
    s.toggleAuthorization();
    if (s.isAuthorized())
      return true;
    return false;
  }

  public boolean toggleNotifications(String clientID, String productID) throws UnknownClientException, UnknownProductException {
    Client c = getClient(clientID);
    Product p = getProduct(productID);
    if (_interestedClients.get(p).contains(c)) {
      _interestedClients.get(p).remove(c);
      return true;
    }
    _interestedClients.get(p).add(c);
    return false;
  }

  public void updateAccountingBalance() {

    double counter = 0;

    for (Map.Entry<String, List<Sale>> e : _sales.entrySet()) {
      for(Sale s : e.getValue()) {
        if (s.wasPaid())
          counter += s.getToPayValue();
        else 
          counter += s.computePayment(s.getClient(), s.getBaseValue(), _date, s.getDeadline());
      }
    }

    for (Map.Entry<String, List<Order>> e : _orders.entrySet()) {
      for (Order o : e.getValue()) {
        counter -= o.getTotalCost();
      }
    }

    _accountingBalance = counter;
  }

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
