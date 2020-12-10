package woo.app.main;

import java.io.FileNotFoundException;
import java.io.IOException;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.Input;

import woo.app.exception.FileOpenFailedException;

import woo.core.StoreManager;
import woo.core.exception.MissingFileAssociationException;

/**
 * Save current state to file under current name (if unnamed, query for name).
 */
public class DoSave extends Command<StoreManager> {

  private Input<String> _filename;

  /** @param receiver */
  public DoSave(StoreManager receiver) {
    super(Label.SAVE, receiver);
    _filename = _form.addStringInput(Message.newSaveAs());
    }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() throws FileOpenFailedException{

    try {

      _receiver.save();

    } catch (FileNotFoundException e) {

      throw new FileOpenFailedException(_filename.value());

    } catch (MissingFileAssociationException e) {

      _form.parse();

      try {

        _receiver.saveAs(_filename.value());

      } catch (MissingFileAssociationException f) {

        System.err.println("Error (MFA): " + f.getMessage());

      } catch (FileNotFoundException f) {

        System.err.println("Error (FNF): " + f.getMessage());

      } catch (IOException f) {
        
        System.err.println("Error (IO): " + f.getMessage());
      }
    } catch (IOException e) {

      throw new FileOpenFailedException(_filename.value());
    }
    
  }
}
