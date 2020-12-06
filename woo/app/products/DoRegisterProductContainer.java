package woo.app.products;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;

import woo.core.StoreManager;
import woo.core.exception.InvalidPriceException;
import woo.core.exception.UnknownLevelException;
import woo.core.exception.UnknownTypeException;
import woo.core.exception.UnknownSupplierException;

import woo.app.exception.UnknownServiceLevelException;
import woo.app.exception.UnknownServiceTypeException;
import woo.app.exception.UnknownSupplierKeyException;
//FIXME import other classes

/**
 * Register container.
 */
public class DoRegisterProductContainer extends Command<StoreManager> {

  private Input<String> _containerId;
  private Input<Integer> _containerPrice;
  private Input<Integer> _containerCriticalValue;
  private Input<String> _supplierId;
  private Input<String> _containerServiceType;
  private Input<String> _containerServiceLevel;


  public DoRegisterProductContainer(StoreManager receiver) {

    super(Label.REGISTER_CONTAINER, receiver);
    _containerId = _form.addStringInput(Message.requestProductKey());
    _containerPrice = _form.addIntegerInput(Message.requestPrice());
    _containerCriticalValue = _form.addIntegerInput(Message.requestStockCriticalValue());
    _supplierId = _form.addStringInput(Message.requestSupplierKey());
    _containerServiceType = _form.addStringInput(Message.requestServiceType());
    _containerServiceLevel = _form.addStringInput(Message.requestServiceLevel());
  }

  @Override
  public final void execute() throws DialogException {
    
    _form.parse();

    try {

    	_receiver.registerContainer(_containerId.value(), _containerPrice.value(), _containerCriticalValue.value(), _supplierId.value(), _containerServiceType.value(), _containerServiceLevel.value());
    
    } catch (UnknownSupplierException e) {

    	throw new UnknownSupplierKeyException(_supplierId.value());
    	
    } catch (UnknownTypeException e) {

    	throw new UnknownServiceTypeException(_containerServiceType.value());

    } catch (UnknownLevelException e) {

      throw new UnknownServiceLevelException(_containerServiceLevel.value());
      
    } catch (InvalidPriceException e) {

      System.err.println("Error: " + e.getMessage());
    }
  }
}
