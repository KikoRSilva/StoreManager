package woo.app.suppliers;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import woo.app.exception.DuplicateSupplierKeyException;
import woo.core.StoreManager;
import pt.tecnico.po.ui.Form;
import woo.core.Supplier;
import woo.core.exception.*;

/**
 * Register supplier.
 */
public class DoRegisterSupplier extends Command<StoreManager> {

  private Input<String> _supplierKey;
  private Input<String> _supplierName;
  private Input<String> _supplierAddress;

  public DoRegisterSupplier(StoreManager receiver) {
    
    super(Label.REGISTER_SUPPLIER, receiver);
    _supplierKey = _form.addStringInput(Message.requestSupplierKey());
    _supplierName = _form.addStringInput(Message.requestSupplierName());
    _supplierAddress = _form.addStringInput(Message.requestSupplierAddress());
  }

  @Override
  public void execute() throws DialogException {

    _form.parse();
    
    try {

      _receiver.registerSupplier(_supplierKey.value(), _supplierName.value(), _supplierAddress.value());

    } catch (DuplicateSupplierException e) {

      throw new DuplicateSupplierKeyException(_supplierKey.value());
    }
  }
}
