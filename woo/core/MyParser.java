package woo.core;

import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;

// add here more imports if needed
import woo.core.exception.*;


public class MyParser {

  private Store _store;  // ou outra entidade

  MyParser(Store s) {
    _store = s;
  }

  void parseFile(String fileName) throws IOException, BadEntryException /* may have other exceptions */ {

    try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
      String line;
      
      while ((line = reader.readLine()) != null)
        parseLine(line);
    } 
  }

  private void parseLine(String line) throws BadEntryException {

    String[] components = line.split("\\|");

    switch(components[0]) {
      case "SUPPLIER":
        parseSupplier(line, components);
        break;

      case "CLIENT":
        parseClient(line, components);
        break;

      case "BOX":
        parseBox(line, components);
        break;

      case "CONTAINER":
        parseContainer(line, components);
        break;

      case "BOOK":
        parseBook(line, components);
        break;

      default:
        throw new BadEntryException("Type of line not supported: " + line);
    }
  }

  // Format: SUPPLIER|id|nome|endereço
  private void parseSupplier(String line, String[] components)  throws BadEntryException {

    if (components.length != 4)
      throw new BadEntryException("Invalid number of fields in supplier description (4) " + line);

    String id = components[1];
    String name = components[2];
    String address = components[3];

    try {

      _store.registerSupplier(id, name, address);

    } catch (DuplicateSupplierException e) {
      
      throw new BadEntryException(e.getMessage());
    }
    
  }

  // Format: CLIENT|id|nome|endereço
  private void parseClient(String line, String[] components) throws BadEntryException {

    if (components.length != 4)
      throw new BadEntryException("Invalid number of fields (4) in client description: " + line);

    String id = components[1];
    String name = components[2];
    String address = components[3];

    try {

      _store.registerClient(id, name, address);

    } catch (DuplicateClientException e) {

      throw new BadEntryException(e.getMessage());
    }
  }

  // Format: BOX|id|tipo-de-serviço|id-fornecedor|preço|valor-crítico|exemplares
  private void parseBox(String line, String[] components) throws BadEntryException {

    if (components.length != 7)
      throw new BadEntryException("wrong number of fields in box description  (7) " + line);

    
    String id = components[1];
    String serviceType = components[2];
    String idSupplier = components[3];
    int price = Integer.parseInt(components[4]);
    int criticalValue = Integer.parseInt(components[5]);
    int stock = Integer.parseInt(components[6]);
    
    
    try {

      _store.addBox(id, price, criticalValue, idSupplier, serviceType, stock);

    } catch (UnknownSupplierException | UnknownTypeException | InvalidPriceException e) {

      throw new BadEntryException(e.getMessage());
    }
  }

  // Format: BOOK|id|título|autor|isbn|id-fornecedor|preço|valor-crítico|exemplares
  private void parseBook(String line, String[] components) throws BadEntryException {

   if (components.length != 9)
      throw new BadEntryException("Invalid number of fields (9) in box description: " + line);

   String id = components[1];
   String title = components[2];
   String author = components[3];
   String isbn = components[4];
   String idSupplier = components[5];
   int price = Integer.parseInt(components[6]);
   int criticalValue = Integer.parseInt(components[7]);
   int stock = Integer.parseInt(components[8]);

   try {

    _store.addBook(id, title, author, isbn, idSupplier, price, criticalValue, stock);

   } catch(UnknownSupplierException | InvalidPriceException e) {

    throw new BadEntryException(e.getMessage());
   }
  }

  // Format: CONTAINER|id|tipo-de-serviço|nível-de-serviço|id-fornecedor|preço|valor-crítico|exemplares
  private void parseContainer(String line, String[] components) throws BadEntryException {
    
    if (components.length != 8)
      throw new BadEntryException("Invalid number of fields (8) in container description: " + line);

    String id = components[1];
    String servType = components[2];
    String servLevel = components[3];
    String idSupplier = components[4];
    int price = Integer.parseInt(components[5]);
    int criticalValue = Integer.parseInt(components[6]);
    int stock = Integer.parseInt(components[7]);

    try {

      _store.addContainer(id, price, criticalValue, idSupplier, servType, servLevel, stock);

    } catch (UnknownSupplierException | InvalidPriceException | UnknownLevelException | UnknownTypeException e) {
      
      throw new BadEntryException(e.getMessage());
    }
  }
}
