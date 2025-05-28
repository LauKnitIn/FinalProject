package co.edu.uptc.model;

import co.edu.uptc.client.Client;
import co.edu.uptc.view.interfaces.Contract;
import co.edu.uptc.view.interfaces.Contract.Presenter;

public class ClientModel implements Contract.Model{
    Client client;
    Presenter presenter;
    @Override
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    public void initSocket(){
        client = new Client();
        client.makeConnecition();
    }
    
}
