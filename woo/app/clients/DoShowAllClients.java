package woo.app.clients;

import java.util.List;
import java.util.ArrayList;
import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import woo.core.Client;
import woo.core.StoreManager;

/**
 * Show all clients.
 */
public class DoShowAllClients extends Command<StoreManager> {

  private List<Client> _allClients = new ArrayList<Client>();

  public DoShowAllClients(StoreManager storefront) {
    super(Label.SHOW_ALL_CLIENTS, storefront);
  }

  @Override
  public void execute() throws DialogException {
    _allClients = _receiver.getAllClients();
    for (Client i : _allClients) {
      _display.addLine(i.toString());
    }
    _display.display();
  }
}
