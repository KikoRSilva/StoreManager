package woo.app.suppliers;

import java.util.List;
import java.util.Map;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import woo.core.Order;
import woo.core.Product;
import woo.core.StoreManager;

/**
 * Show all transactions for specific supplier.
 */
public class DoShowSupplierTransactions extends Command<StoreManager> {

  private Input<String> _supplierID;

  public DoShowSupplierTransactions(StoreManager receiver) {
    super(Label.SHOW_SUPPLIER_TRANSACTIONS, receiver);
    _supplierID = _form.addStringInput(Message.requestSupplierKey());
  }

  @Override
  public void execute() throws DialogException {
    _form.parse();
    List<Order> orders = _receiver.getAllSupplierTransactions(_supplierID.value());
    for (Order t : orders) {
      _display.addLine(t.toString());
      for (Map.Entry<Product, Integer> e : t.getAllProducts().entrySet()) {
        _display.addLine(e.getKey().getId() + " | " + e.getValue());
      }
    }
    _display.display();
  }
}
