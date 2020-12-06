package woo.app.products;

import java.util.List;
import java.util.ArrayList;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;

import woo.core.StoreManager;
import woo.core.Product;
//FIXME import other classes

/**
 * Show all products.
 */
public class DoShowAllProducts extends Command<StoreManager> {

  private List<Product> _products;

  public DoShowAllProducts(StoreManager receiver) {

    super(Label.SHOW_ALL_PRODUCTS, receiver);
    _products = new ArrayList<Product>();
  }

  @Override
  public final void execute() throws DialogException {
    
    _products = _receiver.getAllProducts();

    for (Product i : _products) {

    	_display.addLine(i.toString());

    }
    _display.display();
  }

}
