package co.edu.uptc.presenter;

import co.edu.uptc.view.View;

//hola

public class Runner {
    public static void main(String[] args) {
        View view = new View(null);
        Presenter presenter = new Presenter(view);
        view.setVisible(true);
    }
}
