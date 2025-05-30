package co.edu.uptc.interfaces;

import java.util.List;

import co.edu.uptc.client.Client;

public interface Contract {
    public interface Model {
        public void setPresenter(Presenter presenter);

        public void initSocket();

        public void sendCommand(String command);

        public String getStatus(String value);

        public String getClientName();

        public boolean isFull();

        public boolean isEmpty();

        public boolean isAvailable();

        public Client getClient();

        public List<String> getWordList();
    }

    public interface Presenter {
        public void setModel(Model model);

        public void setView(View view);

        public void initSocket();

        public void showInterface();

        public void sendCommand(String command);

        public String getStatus(String value);

        public String getClientName();

        public boolean isFull();

        public boolean isEmpty();

        public boolean isAvailable();

        public Client getClient();

        public void setClientState(boolean state);

        public void setRoomState(boolean state);

        public List<String> getWordList();

    }

    public interface View {
        public void setPresenter(Presenter presenter);

        public void sendCommand(String command);

        public void showInterface();

        public void setVisible(boolean b);

        public boolean isFull();

        public boolean isEmpty();

        public boolean isAvailable();

        public String getStatus(String value);

        public void setClientState(boolean state);

        public void setRoomState(boolean state);

        public List<String> getWordList();
    }
}
