package woo.app.products;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;

import woo.app.exception.UnknownProductKeyException;

import woo.core.StoreManager;
import woo.core.exception.InvalidPriceException;
import woo.core.exception.UnknownProductException;

/**
 * Change product price.
 */
public class DoChangePrice extends Command<StoreManager> {

  private Input<Integer> _price;
  private Input<String> _productKey;
  
  public DoChangePrice(StoreManager receiver) {

    super(Label.CHANGE_PRICE, receiver);
    _productKey = _form.addStringInput(Message.requestProductKey());
    _price = _form.addIntegerInput(Message.requestPrice());

  }

  @Override
  public final void execute() throws DialogException {

    _form.parse();
    try {

      _receiver.changePrice(_productKey.value(), _price.value());

    } catch (InvalidPriceException e) {
      
      System.err.println("Error: " + e.getMessage());
      
    } catch (UnknownProductException e) {
      
      throw new UnknownProductKeyException(_productKey.value());
    }
    
  }
}
