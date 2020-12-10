package woo.app.transactions;

import java.util.TreeMap;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import woo.app.exception.UnauthorizedSupplierException;
import woo.app.exception.UnknownProductKeyException;
import woo.app.exception.UnknownSupplierKeyException;
import woo.app.exception.WrongSupplierException;
import woo.core.Product;
import woo.core.StoreManager;
import woo.core.exception.UnauthorizedSupException;
import woo.core.exception.UnknownProductException;
import woo.core.exception.UnknownSupplierException;
import woo.core.exception.WrongSupException;

/**
 * Register order.
 */
public class DoRegisterOrderTransaction extends Command<StoreManager> {

  private Input<String> _supplierID;
  private Input<String> _productID;
  private Input<Integer> _amount;
  private Input<Boolean> _wantMore;
  private TreeMap<Product, Integer> _products = new TreeMap<Product, Integer>();

  public DoRegisterOrderTransaction(StoreManager receiver) {
    super(Label.REGISTER_ORDER_TRANSACTION, receiver);
    _supplierID = _form.addStringInput(Message.requestSupplierKey());
    ask();
  }

  @Override
  public final void execute() throws DialogException {
  
    while (_wantMore.value()) {
      ask();
      _form.parse();
      try {
        Product p = _receiver.getProduct(_productID.value());
        _products.put(p, _amount.value());
      } catch (UnknownProductException e) {
        throw new UnknownProductKeyException(_productID.value());
      }
    }
      
    _form.parse();

    try {
      _receiver.registerOrder(_supplierID.value(), _products);
    } catch (UnknownSupplierException e) {
      throw new UnknownSupplierKeyException(_supplierID.value());
    } catch (WrongSupException e) {
      throw new WrongSupplierException(_supplierID.value(), e.getProduct().getId());
    } catch (UnauthorizedSupException e) {
      throw new UnauthorizedSupplierException(_supplierID.value());
    }
  }

  public void ask() {
    _productID = _form.addStringInput(Message.requestProductKey());
    _amount = _form.addIntegerInput(Message.requestAmount());
    _wantMore = _form.addBooleanInput(Message.requestMore());
  }
}
