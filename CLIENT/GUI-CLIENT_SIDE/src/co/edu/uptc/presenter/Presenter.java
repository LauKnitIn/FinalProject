package co.edu.uptc.presenter;

import co.edu.uptc.view.View;

public class Presenter {
    private View view;
    
    public Presenter(View view) {
        this.view = view;
    }

    public void showPanelStart() {
        view.showPanelStart();
    }
}