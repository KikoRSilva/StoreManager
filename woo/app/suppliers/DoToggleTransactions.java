package woo.app.suppliers;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import woo.app.exception.UnknownSupplierKeyException;
import woo.core.StoreManager;
//FIXME import other classes
import woo.core.exception.UnknownSupplierException;

/**
 * Enable/disable supplier transactions.
 */
public class DoToggleTransactions extends Command<StoreManager> {

  private Input<String> _supplierID;

  public DoToggleTransactions(StoreManager receiver) {
    super(Label.TOGGLE_TRANSACTIONS, receiver);
    _supplierID = _form.addStringInput(Message.requestSupplierKey());
  }

  @Override
  public void execute() throws DialogException {
    _form.parse();
    try {
    boolean on = _receiver.toggleTransactions(_supplierID.value());
    if (on)
      _display.addLine(Message.transactionsOn(_supplierID.value()));
    else {
      _display.addLine(Message.transactionsOff(_supplierID.value()));
    }
    } catch (UnknownSupplierException e) {
      throw new UnknownSupplierKeyException(_supplierID.value());
    } finally {
      _display.display();
    }
  }

}
