package co.edu.uptc.App;

import co.edu.uptc.interfaces.*;
import co.edu.uptc.model.ClientModel;
import co.edu.uptc.presenter.Presenter;
import co.edu.uptc.view.View;

public class App {
    public static void main(String[] args) {
        Contract.Presenter presenter = new Presenter();
        Contract.View view = new View();
        Contract.Model model = new ClientModel();

        presenter.setView(view);
        presenter.setModel(model);
        view.setPresenter(presenter);
        model.setPresenter(presenter);
        view.showInterface();
        view.setVisible(true);
        System.out.println("cliente conectado");
    }
}
