package co.edu.uptc.presenter;

import co.edu.uptc.view.View;

public class Presenter {
    private View view;
    
    public Presenter(co.edu.uptc.view.View view2) {
        this.view = view2;
    }

    public void showPanelStart() {
        view.showPanelStart();
    }
}