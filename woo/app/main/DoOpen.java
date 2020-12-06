package woo.app.main;

import java.io.IOException;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;

import woo.core.StoreManager;
import woo.core.exception.UnavailableFileException;

import woo.app.exception.FileOpenFailedException;

/**
 * Open existing saved state.
 */
public class DoOpen extends Command<StoreManager> {

  private Input<String> _filename;

  /** @param receiver */
  public DoOpen(StoreManager receiver) {
    super(Label.OPEN, receiver);
    _filename = _form.addStringInput(Message.openFile());
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() throws DialogException {

    _form.parse();

    try {

      _receiver.load(_filename.value());

    } catch (UnavailableFileException ufe) {
      ufe.printStackTrace();
      throw new FileOpenFailedException(ufe.getFilename());

    } catch (ClassNotFoundException e) {

      System.err.println("Error: " + e.getMessage());
      
    } catch (IOException e) {
      e.printStackTrace();
      throw new FileOpenFailedException(_filename.value());
    
    }
  }
}
