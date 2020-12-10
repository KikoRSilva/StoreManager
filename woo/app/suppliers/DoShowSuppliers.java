package woo.app.suppliers;

import java.util.List;
import java.util.ArrayList;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;

import woo.core.StoreManager;
import woo.core.Supplier;

/**
 * Show all suppliers.
 */
public class DoShowSuppliers extends Command<StoreManager> {

  private List<Supplier> _allSuppliers;

  public DoShowSuppliers(StoreManager receiver) {
    super(Label.SHOW_ALL_SUPPLIERS, receiver);
    _allSuppliers = new ArrayList<Supplier>();
  }

  @Override
  public void execute() throws DialogException {
    
    _allSuppliers = _receiver.getAllSuppliers();

    for (Supplier s : _allSuppliers) {
      if (s.isAuthorized())
        _display.addLine(s.toString() + Message.yes());
      else
        _display.addLine(s.toString() + Message.no());
    }
    _display.display();
  }
}
