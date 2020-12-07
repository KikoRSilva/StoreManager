package woo.core;

import java.io.IOException;
import java.util.List;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import woo.core.exception.*;

/**
 * StoreManager: façade for the core classes.
 */
public class StoreManager {

  /** Current filename. */
  private String _filename = "";

  /** The actual store. */
  private Store _store = new Store();

  /**
   * @throws IOException
   * @throws FileNotFoundException
   * @throws MissingFileAssociationException
   */
  public void save() throws IOException, FileNotFoundException, MissingFileAssociationException {
    
    ObjectOutputStream obOut = null;

    if (_filename.equals(""))
      throw new MissingFileAssociationException();

    try {

      obOut = new ObjectOutputStream(new FileOutputStream(_filename));
      obOut.writeObject(_store);

    } finally {

      if(obOut != null)
        
      obOut.close();
    }
  }

  /**
   * @param filename
   * @throws MissingFileAssociationException
   * @throws IOException
   * @throws FileNotFoundException
   */
  public void saveAs(String filename) throws MissingFileAssociationException, FileNotFoundException, IOException {
    _filename = filename;
    save();
  }

  /**
   * @param filename
   * @throws UnavailableFileException
   */
  public void load(String filename) throws UnavailableFileException, IOException, ClassNotFoundException {
    
    _filename = filename;
    ObjectInputStream objIn = null;

    try {

      objIn = new ObjectInputStream(new FileInputStream(_filename));
      _store = (Store)objIn.readObject();
      
    
    } finally {

      if (objIn != null)

        objIn.close();
    }
  }

  /**
   * @param textfile
   * @throws ImportFileException
   */
  public void importFile(String textfile) throws ImportFileException {
    try {
      _store.importFile(textfile);
    } catch (IOException | BadEntryException /* FIXME maybe other exceptions */ e) {
      throw new ImportFileException(textfile);
    }
  }


  /////////////////////////////////////////////// GETTERS FUNCTIONS //////////////////////////////////////////////////////
  /**
   * @param id - id of the client
   * @param name - name of the client
   * @param address - address of the client
   * @throws DuplicateClientException
   */
  public void registerClient(String id, String name, String address) throws DuplicateClientException {
    _store.registerClient(id, name, address);
  }

   /**
   * @param id - id of the supplier
   * @param name - name of the supplier
   * @param address - address of the supplier
   * @throws DuplicateSupplierException
   */
  public void registerSupplier(String id, String name, String address) throws DuplicateSupplierException {
    _store.registerSupplier(id, name, address);
  }

  public void registerBook(String id, String title, String author, String isbn, int price, int criticalValue, String supplierId) throws UnknownSupplierException, InvalidPriceException{
    _store.registerBook(id, title, author, isbn, supplierId, price, criticalValue);
  }

  public void registerBox(String id, int price, int criticalValue, String idSupplier, String servType) throws UnknownSupplierException, UnknownTypeException, InvalidPriceException {
    _store.registerBox(id, price, criticalValue, idSupplier, servType);
  }

  public void registerContainer(String id, int price, int criticalValue, String idSupplier, String servType, String servLevel) throws UnknownSupplierException, UnknownLevelException, UnknownTypeException, InvalidPriceException {
    _store.registerContainer(id, price, criticalValue, idSupplier, servType, servLevel);
  }

  public void registerSale(String cid, int deadline, String pid, int amount) throws UnknownProductException, UnknownClientException, DeniedSaleException {
    _store.registerSale(cid, deadline, pid, amount);
  }


  /////////////////////////////////////////////////// GETTERS FUNCTIONS /////////////////////////////////////////////
  /**
   * @param id - id of the client
   * @throws UnknownClientException
   */
  public Client getClient(String id) throws UnknownClientException { return _store.getClient(id); }

  public List<Client> getAllClients() { return _store.getAllClients(); }

  public int getDate() { return _store.getDate(); }

  public List<Product> getAllProducts() { return _store.getAllProducts(); }

  public String getFileName() { return _filename; }
  
  public List<Supplier> getAllSuppliers() { return _store.getAllSuppliers(); }

  public int getProductAmountAvailable(String pid) { return _store.getProductStock(pid); }

  public Transaction getTransaction(int id) throws UnknownTransactionException { return _store.getTransaction(id); }
  
  public String getTProductName(int id) {return _store.getTProductName(id); }
  /////////////////////////////////////////////// OTHER FUNCTIONS ////////////////////////////////////////////////////
  /**
   * @param numberOfDays - days to advance to the current date
   * @throws InvalidCoreDateException
   */
  public void advanceDate(int numberOfDays) throws InvalidCoreDateException {
    _store.advanceDate(numberOfDays);
  }

  public void changePrice(String id, int price) throws InvalidPriceException, UnknownProductException{
    _store.changePrice(id, price);
  }

  public void clearClientNotifications(String id) throws UnknownClientException {
    _store.clearClientNotifications(id);
  }

  public void pay(int id) throws UnknownTransactionException, UnknownProductException {
    _store.pay(id);
  }
}
