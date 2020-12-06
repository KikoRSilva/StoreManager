package woo.app.main;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;

import woo.core.StoreManager;
import woo.core.exception.InvalidCoreDateException;

import woo.app.exception.InvalidDateException;
//FIXME import other classes

/**
 * Advance current date.
 */
public class DoAdvanceDate extends Command<StoreManager> {
  
  private Input<Integer> _numberOfDays;

  public DoAdvanceDate(StoreManager receiver) {
    super(Label.ADVANCE_DATE, receiver);
    _numberOfDays = _form.addIntegerInput(Message.requestDaysToAdvance());
  }

  @Override
  public final void execute() throws DialogException {
    _form.parse();
    
    try {

      _receiver.advanceDate(_numberOfDays.value());

    } catch (InvalidCoreDateException e) {

      throw new InvalidDateException(_numberOfDays.value());
    }
  }
}
