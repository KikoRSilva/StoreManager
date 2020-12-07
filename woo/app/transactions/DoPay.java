package woo.app.transactions;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import woo.app.exception.UnknownProductKeyException;
import woo.app.exception.UnknownTransactionKeyException;
import woo.core.StoreManager;
import woo.core.exception.UnknownProductException;
//FIXME import other classes
import woo.core.exception.UnknownTransactionException;

/**
 * Pay transaction (sale).
 */
public class DoPay extends Command<StoreManager> {

  private Input<Integer> _saleID;

  public DoPay(StoreManager storefront) {
    super(Label.PAY, storefront);
    _saleID = _form.addIntegerInput(Message.requestTransactionKey());
  }

  @Override
  public final void execute() throws DialogException {
    _form.parse();
    try {
      _receiver.pay(_saleID.value());
    } catch (UnknownTransactionException e) {
      throw new UnknownTransactionKeyException(_saleID.value());
    } catch (UnknownProductException e) {
      String prodKey = _receiver.getTProductName(_saleID.value());
	    throw new UnknownProductKeyException(prodKey);
    }
  }
}
