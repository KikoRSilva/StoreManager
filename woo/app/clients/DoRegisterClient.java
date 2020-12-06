package woo.app.clients;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;

import woo.app.exception.DuplicateClientKeyException;

import woo.core.StoreManager;
import woo.core.exception.DuplicateClientException;

/**
 * Register new client.
 */
public class DoRegisterClient extends Command<StoreManager> {

  private Input<String> _clientKey;
  private Input<String> _clientName;
  private Input<String> _clientAddress;

  public DoRegisterClient(StoreManager storefront) {
    
    super(Label.REGISTER_CLIENT, storefront);
    _clientKey = _form.addStringInput(Message.requestClientKey());
    _clientName = _form.addStringInput(Message.requestClientName());
    _clientAddress = _form.addStringInput(Message.requestClientAddress());
  }

  @Override
  public void execute() throws DialogException {

    _form.parse();
    
    try{

      _receiver.registerClient(_clientKey.value(), _clientName.value(), _clientAddress.value());

    } catch (DuplicateClientException e) {

      throw new DuplicateClientKeyException(_clientKey.value());
    }
    
    
  }

}
