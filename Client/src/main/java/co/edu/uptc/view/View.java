package co.edu.uptc.view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.CardLayout;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.List;

import co.edu.uptc.client.Client;
import co.edu.uptc.interfaces.Contract;

public class View extends JFrame implements Contract.View {

        private PanelStart panelStart;
        private PanelLoginName panelLoginName;
        private PanelDifficulty panelDifficulty;
        private PanelGameRules panelGameRules;
        private PanelGameRulesP2 panelGameRulesP2;
        private PanelChooseWord panelChooseWord;
        private PanelOnePlayer panelOnePlayer;
        private PanelMultiplayer panelMultiplayer;

        private boolean isMultiplayer = false;
        private boolean isAdmin;
        private boolean isBusy;

        private Contract.Presenter presenter;

        private CardLayout cardLayout;
    private JPanel cardsContainer;

    public View(Contract.Presenter presenter) {
        this.presenter = presenter;
        setTitle("El ahorcado");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(screenSize.width, screenSize.height);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        cardsContainer = new JPanel(cardLayout);

        initComponents();
        setContentPane(cardsContainer);
    }

    public void initComponents() {
        panelStart = new PanelStart();
        panelLoginName = new PanelLoginName();
        panelDifficulty = new PanelDifficulty();
        panelGameRules = new PanelGameRules();
        panelGameRulesP2 = new PanelGameRulesP2();
        panelMultiplayer = new PanelMultiplayer();

        cardsContainer.add(panelStart, "start");
        cardsContainer.add(panelLoginName, "login");
        cardsContainer.add(panelDifficulty, "difficulty");
        cardsContainer.add(panelGameRules, "rules");
        cardsContainer.add(panelGameRulesP2, "rulesP2");
        cardsContainer.add(panelMultiplayer, "multi");
        // panelChooseWord y panelOnePlayer se agregan dinámicamente si es necesario

        cardLayout.show(cardsContainer, "start");
    }

    // Métodos showPanel usando CardLayout
    public void showPanelLoginName() {
        panelLoginName.initComponents();
        cardLayout.show(cardsContainer, "login");
    }

    public void showPanelDifficulty() {
        cardLayout.show(cardsContainer, "difficulty");
    }

    public void showPanelStart() {
        cardLayout.show(cardsContainer, "start");
    }

    public void showPanelGameRules() {
        cardLayout.show(cardsContainer, "rules");
    }

    public void showPanelGameRulesP2() {
        cardLayout.show(cardsContainer, "rulesP2");
    }

    public void showPanelMultiplayer() {
        cardLayout.show(cardsContainer, "multi");
    }

    public void showPanelChooseWord(String difficulty) {
        if (panelChooseWord != null) {
            cardsContainer.remove(panelChooseWord);
        }
        panelChooseWord = new PanelChooseWord(difficulty);
        cardsContainer.add(panelChooseWord, "chooseWord");
        cardLayout.show(cardsContainer, "chooseWord");
        cardsContainer.revalidate();
        cardsContainer.repaint();
    }

    public void showPanelOnePlayer() {
        if (panelOnePlayer != null) {
            cardsContainer.remove(panelOnePlayer);
        }
        panelOnePlayer = new PanelOnePlayer(getClient());
        panelOnePlayer.addPlayerNamePanel();
        panelOnePlayer.addWordDisplayPanel();
        cardsContainer.add(panelOnePlayer, "onePlayer");
        cardLayout.show(cardsContainer, "onePlayer");
        cardsContainer.revalidate();
        cardsContainer.repaint();
    }

        public Client getClient() {
                return this.presenter.getClient();
        }

        public void setMultiplayer(boolean isMultiplayer) {
                this.isMultiplayer = isMultiplayer;
        }

        public boolean isMultiplayer() {
                return isMultiplayer;
        }

        @Override
        public void setPresenter(Contract.Presenter presenter) {
                this.presenter = presenter;
        }

        @Override
        public void showInterface() {
                this.presenter.initSocket();
                System.out.println("GUI mostrada");
        }

        @Override
        public void sendCommand(String command) {
                presenter.sendCommand(command);
        }

        public String getStatus(String value) {
                return presenter.getStatus(value);
        }

        public String getPlayerName() {
                return presenter.getClientName();
        }

        @Override
        public boolean isFull() {
                return this.presenter.isFull();
        }

        @Override
        public boolean isEmpty() {
                return this.presenter.isEmpty();
        }

        @Override
        public boolean isAvailable() {
                return this.presenter.isAvailable();
        }

        public void setClientState(boolean isAdmin) {
                this.isAdmin = isAdmin;
        }

        public boolean getIsAdmin() {
                return this.isAdmin;
        }

        public boolean isBusy() {
                return isBusy;
        }

        public void setRoomState(boolean isBusy) {
                this.isBusy = isBusy;
        }

        @Override
        public List<String> getWordList() {
                return this.presenter.getWordList();
        }

}