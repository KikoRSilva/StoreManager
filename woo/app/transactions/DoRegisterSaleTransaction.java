package woo.app.transactions;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import woo.app.exception.UnavailableProductException;
import woo.app.exception.UnknownClientKeyException;
import woo.app.exception.UnknownProductKeyException;
import woo.core.StoreManager;
import woo.core.exception.DeniedSaleException;
//FIXME import other classes
import woo.core.exception.UnknownClientException;
import woo.core.exception.UnknownProductException;

/**
 * Register sale.
 */
public class DoRegisterSaleTransaction extends Command<StoreManager> {

  //FIXME add input fields
  private Input<String> _clientID;
  private Input<Integer> _deadline;
  private Input<String> _productID;
  private Input<Integer> _amount;

  public DoRegisterSaleTransaction(StoreManager receiver) {
    super(Label.REGISTER_SALE_TRANSACTION, receiver);
    _clientID = _form.addStringInput(Message.requestClientKey());
    _deadline = _form.addIntegerInput(Message.requestPaymentDeadline());
    _productID = _form.addStringInput(Message.requestProductKey());
    _amount = _form.addIntegerInput(Message.requestAmount());
  }

  @Override
  public final void execute() throws DialogException {
    _form.parse();
    try {
      _receiver.registerSale(_clientID.value(), _deadline.value(), _productID.value(), _amount.value());
    } catch (DeniedSaleException e) {
      int available = _receiver.getProductAmountAvailable(_productID.value());
      throw new UnavailableProductException(_productID.value(), _amount.value(), available);
    } catch (UnknownClientException e) {
      throw new UnknownClientKeyException(_clientID.value());
    } catch (UnknownProductException e) {
      throw new UnknownProductKeyException(_productID.value());
    }
  }

}
