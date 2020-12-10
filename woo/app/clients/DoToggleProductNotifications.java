package woo.app.clients;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import woo.app.exception.UnknownClientKeyException;
import woo.app.exception.UnknownProductKeyException;
import woo.core.StoreManager;
//FIXME import other classes
import woo.core.exception.UnknownClientException;
import woo.core.exception.UnknownProductException;

/**
 * Toggle product-related notifications.
 */
public class DoToggleProductNotifications extends Command<StoreManager> {

  private Input<String> _productID;
  private Input<String> _clientID;

  public DoToggleProductNotifications(StoreManager storefront) {
    super(Label.TOGGLE_PRODUCT_NOTIFICATIONS, storefront);
    _clientID = _form.addStringInput(Message.requestClientKey());
    _productID = _form.addStringInput(Message.requestProductKey());
  }

  @Override
  public void execute() throws DialogException {
    _form.parse();
    try {
      boolean on = _receiver.toggleNotifications(_clientID.value(), _productID.value());
      if (!on)
        _display.addLine(Message.notificationsOn(_clientID.value(), _productID.value()));
      else {
        _display.addLine(Message.notificationsOff(_clientID.value(), _productID.value()));
      }
      } catch (UnknownClientException e) {
        throw new UnknownClientKeyException(_clientID.value());
      } catch (UnknownProductException e) {
        throw new UnknownProductKeyException(_productID.value());
      } finally {
        _display.display();
      }
  }

}
