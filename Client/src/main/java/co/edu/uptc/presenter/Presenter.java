package co.edu.uptc.presenter;

import co.edu.uptc.model.ClientModel;
import co.edu.uptc.view.View;
import co.edu.uptc.view.interfaces.Contract;
import co.edu.uptc.view.interfaces.Contract.Model;

public class Presenter implements Contract.Presenter{
    private Contract.View view;
    private Contract.Model model;
    public Presenter() {
        
    }

    public void showPanelStart() {
        view.showInterface();
    }

    @Override
    public void setModel(Model model) {
        this.model = model;
    }

    @Override
    public void setView(co.edu.uptc.view.interfaces.Contract.View view) {
        this.view = view;
    }

    @Override
    public void initSocket() {
        model.initSocket();
    }
}