package woo.app.main;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import woo.core.StoreManager;

/**
 * Show current date.
 */
public class DoDisplayDate extends Command<StoreManager> {

  // private int _date;

  public DoDisplayDate(StoreManager receiver) {
    super(Label.SHOW_DATE, receiver);
    
  }

  @Override
  public final void execute() throws DialogException {
    int _date= _receiver.getDate();
    _display.popup(Message.currentDate(_date));
  }
}
