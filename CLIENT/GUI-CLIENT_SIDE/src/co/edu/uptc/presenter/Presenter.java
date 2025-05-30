package co.edu.uptc.presenter;

import co.edu.uptc.client.Client;
import co.edu.uptc.interfaces.Contract;
import co.edu.uptc.interfaces.Contract.Model;

public class Presenter implements Contract.Presenter{
    private Contract.Model model;
    private Contract.View view;
    public Presenter() {
    
    }

    public Client getClient(){
        return this.model.getClient();
    }

    @Override
    public void setModel(Model model) {
        this.model = model;
    }

    @Override
    public void setView(co.edu.uptc.interfaces.Contract.View view) {
        this.view = view;
    }

    @Override
    public void initSocket() {
        model.initSocket();
    }

    @Override
    public void showInterface() {
        view.showInterface();
    }

    @Override
    public void sendCommand(String command) {
        this.model.sendCommand(command);
    }

    @Override
    public String getStatus(String value) {
        return model.getStatus(value);
    }

    @Override
    public String getClientName() {
        return this.model.getClientName();
    }

    @Override
    public boolean isFull() {
        return this.model.isFull();
    }

    @Override
    public boolean isEmpty() {
        return this.model.isEmpty();
    }

    @Override
    public boolean isAvailable() {
        return this.model.isAvailable();
    }

    
}