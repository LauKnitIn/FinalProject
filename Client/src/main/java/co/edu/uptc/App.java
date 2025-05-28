package co.edu.uptc;

import co.edu.uptc.model.ClientModel;
import co.edu.uptc.presenter.Presenter;
import co.edu.uptc.view.View;
import co.edu.uptc.view.interfaces.Contract;

public class App {

    public static void main(String[] args) {
        Contract.Presenter presenter = new Presenter();
        Contract.View view = new View();
        Contract.Model model = new ClientModel();
        presenter.setModel(model);
        presenter.setView(view);
        view.setPresenter(presenter);
        model.setPresenter(presenter);
        model.initSocket();
        
    }
}
