package woo.app.clients;

import java.util.List;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;

import woo.core.StoreManager;
import woo.core.Client;
import woo.core.Notification;
import woo.core.exception.UnknownClientException;

import woo.app.exception.UnknownClientKeyException;

/**
 * Show client.
 */
public class DoShowClient extends Command<StoreManager> {

  private Input<String> _clientKey;
  // private List<String> _notifications;


  public DoShowClient(StoreManager storefront) {

    super(Label.SHOW_CLIENT, storefront);
    _clientKey = _form.addStringInput(Message.requestClientKey());
  }

  @Override
  public void execute() throws DialogException {

    _form.parse();

    try {

      Client c = _receiver.getClient(_clientKey.value());
      List<String> notifications = c.getNotifications();

      _display.addLine(c.toString());
      for (String n : notifications) {
        _display.addLine(n);
      }
      _display.display();

      _receiver.clearClientNotifications(_clientKey.value());

    } catch (UnknownClientException e) {
      
      throw new UnknownClientKeyException(_clientKey.value());
    }
  }
}
