package woo.app.lookups;

import java.util.List;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import woo.core.Product;
import woo.core.StoreManager;
//FIXME import other classes

/**
 * Lookup products cheaper than a given price.
 */
public class DoLookupProductsUnderTopPrice extends Command<StoreManager> {

  private Input<Integer> _price;

  public DoLookupProductsUnderTopPrice(StoreManager storefront) {
    super(Label.PRODUCTS_UNDER_PRICE, storefront);
    _price = _form.addIntegerInput(Message.requestPriceLimit());
  }

  @Override
  public void execute() throws DialogException {
    List<Product> products = _receiver.getProductsUnderLimitOf(_price.value());
    for (Product p : products)
      _display.addLine(p.toString());
    _display.display();
  }
}
