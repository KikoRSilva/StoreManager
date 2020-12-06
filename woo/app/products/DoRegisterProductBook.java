package woo.app.products;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;

import woo.core.StoreManager;
import woo.core.exception.InvalidPriceException;
import woo.core.exception.UnknownSupplierException;

import woo.app.exception.UnknownSupplierKeyException;
//FIXME import other classes

/**
 * Register book.
 */
public class DoRegisterProductBook extends Command<StoreManager> {

  private Input<String> _bookId;
  private Input<String> _bookTitle;
  private Input<String> _bookAuthor;
  private Input<String> _bookISBN;
  private Input<Integer> _bookPrice;
  private Input<Integer> _bookCriticalValue;
  private Input<String> _supplierId;


  public DoRegisterProductBook(StoreManager receiver) {

    super(Label.REGISTER_BOOK, receiver);
    _bookId = _form.addStringInput(Message.requestProductKey());
    _bookTitle = _form.addStringInput(Message.requestBookTitle());
    _bookAuthor = _form.addStringInput(Message.requestBookAuthor());
    _bookISBN = _form.addStringInput(Message.requestISBN());
    _bookPrice = _form.addIntegerInput(Message.requestPrice());
    _bookCriticalValue = _form.addIntegerInput(Message.requestStockCriticalValue());
    _supplierId = _form.addStringInput(Message.requestSupplierKey());
  }

  @Override
  public final void execute() throws DialogException {

    _form.parse();

    try {

    	_receiver.registerBook(_bookId.value(), _bookTitle.value(), _bookAuthor.value(), _bookISBN.value(), _bookPrice.value(), _bookCriticalValue.value(), _supplierId.value());
    
    } catch (UnknownSupplierException e) {

      throw new UnknownSupplierKeyException(_supplierId.value());
      
    } catch (InvalidPriceException e) {

      System.err.println("Error: " + e.getMessage());
    }
  }
}
