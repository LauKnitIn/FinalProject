package co.edu.uptc.presenter;

import co.edu.uptc.view.View;

public class Runner {
    public static void main(String[] args) {
        System.out.println("comienzo del juego, preprandose");
        View view = new View(null);
        Presenter presenter = new Presenter(view);
        view.setVisible(true);
    }
}