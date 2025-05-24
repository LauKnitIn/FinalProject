package presenter;

import view.View;

public class Presenter {
    private View view;

    public Presenter(View view) {
        this.view = view;
    }

    public void showPanelStart() {
        view.showPanelStart();
    }
}