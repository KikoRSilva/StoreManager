package woo.app.clients;

import java.util.List;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import woo.app.lookups.Message;
import woo.core.Sale;
import woo.core.StoreManager;

/**
 * Show all transactions for a specific client.
 */
public class DoShowClientTransactions extends Command<StoreManager> {

  private Input<String> _clientID;

  public DoShowClientTransactions(StoreManager storefront) {
    super(Label.SHOW_CLIENT_TRANSACTIONS, storefront);
    _clientID = _form.addStringInput(Message.requestClientKey());
  }

  @Override
  public void execute() throws DialogException {
    _form.parse();
    List<Sale> sales = _receiver.getAllClientTransactions(_clientID.value());
    for (Sale s : sales)
      _display.addLine(s.toString());
    _display.display();
  }
}
