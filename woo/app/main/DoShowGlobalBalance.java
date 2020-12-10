package woo.app.main;

import pt.tecnico.po.ui.Command;
import woo.core.StoreManager;

/**
 * Show global balance.
 */
public class DoShowGlobalBalance extends Command<StoreManager> {

  public DoShowGlobalBalance(StoreManager receiver) {
    super(Label.SHOW_BALANCE, receiver);
  }

  @Override
  public final void execute() {
    double available = _receiver.getAvailableBalance();
    double accounting = _receiver.getAccountingBalance();
    _display.addLine(Message.currentBalance(Math.toIntExact(Math.round(available)), Math.toIntExact(Math.round(accounting))));
    _display.display();
  }
}
