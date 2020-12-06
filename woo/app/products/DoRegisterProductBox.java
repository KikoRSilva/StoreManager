package woo.app.products;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;

import woo.core.StoreManager;
import woo.core.exception.UnknownSupplierException;
import woo.core.exception.UnknownTypeException;
import woo.core.exception.InvalidPriceException;

import woo.app.exception.UnknownSupplierKeyException;
import woo.app.exception.UnknownServiceTypeException;
//FIXME import other classes

/**
 * Register box.
 */
public class DoRegisterProductBox extends Command<StoreManager> {

  private Input<String> _boxId;
  private Input<Integer> _boxPrice;
  private Input<Integer> _boxCriticalValue;
  private Input<String> _supplierId;
  private Input<String> _boxServiceType;

  public DoRegisterProductBox(StoreManager receiver) {

    super(Label.REGISTER_BOX, receiver);
    _boxId = _form.addStringInput(Message.requestProductKey());
    _boxPrice = _form.addIntegerInput(Message.requestPrice());
    _boxCriticalValue = _form.addIntegerInput(Message.requestStockCriticalValue());
    _supplierId = _form.addStringInput(Message.requestSupplierKey());
    _boxServiceType = _form.addStringInput(Message.requestServiceType());
  }

  @Override
  public final void execute() throws DialogException {
    
    _form.parse();

    try {

    	_receiver.registerBox(_boxId.value(), _boxPrice.value(), _boxCriticalValue.value(), _supplierId.value(), _boxServiceType.value());
    
    } catch (UnknownSupplierException e) {

    	throw new UnknownSupplierKeyException(_supplierId.value());

    } catch (UnknownTypeException e) {

      throw new UnknownServiceTypeException(_boxServiceType.value());
      
    } catch (InvalidPriceException e) {
      
      System.err.println("Error: " + e.getMessage());
    }
  }
}
