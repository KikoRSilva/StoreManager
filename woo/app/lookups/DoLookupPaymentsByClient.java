package woo.app.lookups;

import java.util.List;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import woo.core.Sale;
import woo.core.StoreManager;

/**
 * Lookup payments by given client.
 */
public class DoLookupPaymentsByClient extends Command<StoreManager> {

  private Input<String> _clientID;

  public DoLookupPaymentsByClient(StoreManager storefront) {
    super(Label.PAID_BY_CLIENT, storefront);
    _clientID = _form.addStringInput(Message.requestClientKey());
  }

  @Override
  public void execute() throws DialogException {
    _form.parse();
    List<Sale> paidSales = _receiver.getPaidSales(_clientID.value());
    for (Sale s : paidSales)
      _display.addLine(s.toString());
    _display.display();
  }

}
