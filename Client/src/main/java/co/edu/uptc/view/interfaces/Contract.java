package co.edu.uptc.view.interfaces;

public interface Contract {
    public interface Model{
        public void setPresenter(Presenter presenter);
        public void initSocket();
    }

    public interface Presenter {
        public void setModel(Model model);
        public void setView(View view);
        public void initSocket();
    }

    public interface View{
        public void setPresenter(Presenter presenter);
        public void showInterface();
    }
}
