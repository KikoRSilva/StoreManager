package woo.app.transactions;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import woo.app.exception.UnknownTransactionKeyException;
import woo.core.exception.UnknownTransactionException;
import woo.core.StoreManager;
//FIXME import other classes
import woo.core.Transaction;

/**
 * Show specific transaction.
 */
public class DoShowTransaction extends Command<StoreManager> {

  private Input<Integer> _transactionID;

  public DoShowTransaction(StoreManager receiver) {
    super(Label.SHOW_TRANSACTION, receiver);
    _transactionID = _form.addIntegerInput(Message.requestTransactionKey());
  }

  @Override
  public final void execute() throws DialogException {
    _form.parse();
    try {
      Transaction t = _receiver.getTransaction(_transactionID.value());
      _display.addLine(t.toString());
      _display.display();
    } catch (UnknownTransactionException e) {
      e.printStackTrace();
      throw new UnknownTransactionKeyException(_transactionID.value());
    }
    
  }
}
