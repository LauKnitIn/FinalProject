package co.edu.uptc.view;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import co.edu.uptc.client.Client;
import co.edu.uptc.interfaces.Contract;
import co.edu.uptc.presenter.Presenter;
import co.edu.uptc.view.sounds.BackgroundMusic;

public class View extends JFrame implements Contract.View {

        private PanelStart panelStart;
        private PanelLoginName panelLoginName;
        private PanelDifficulty panelDifficulty;
        private PanelGameRules panelGameRules;
        private PanelGameRulesP2 panelGameRulesP2;
        private PanelChooseWord panelChooseWord;
        private PanelOnePlayer panelOnePlayer;
        private PanelMultiplayer panelMultiplayer;
        private BackgroundMusic backgroundMusic;

        private boolean isMultiplayer = false;

        private Contract.Presenter presenter;

        public View(Contract.Presenter presenter) {
                this.presenter = presenter;
                setTitle("El Ahorcado");
                backgroundMusic = new BackgroundMusic();
                backgroundMusic.playFromFile("GUI-CLIENT_SIDE\\src\\co\\edu\\uptc\\resources\\Fly-Me-to-the-Moon.wav");
                backgroundMusic.setVolume(0.8f);

                Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                setSize(screenSize.width, screenSize.height);
                setLayout(new BorderLayout());
                setDefaultCloseOperation(EXIT_ON_CLOSE);
                setResizable(false);
                setLocationRelativeTo(null);
                initComponents();
        }

        public void initComponents() {
                panelStart = new PanelStart();
                panelLoginName = new PanelLoginName();
                panelDifficulty = new PanelDifficulty();
                panelGameRules = new PanelGameRules();
                panelGameRulesP2 = new PanelGameRulesP2();
                panelChooseWord = new PanelChooseWord();
                panelMultiplayer = new PanelMultiplayer();

                add(panelStart, BorderLayout.CENTER);
                add(panelLoginName, BorderLayout.CENTER);
                add(panelDifficulty, BorderLayout.CENTER);
                add(panelGameRules, BorderLayout.CENTER);
                add(panelGameRulesP2, BorderLayout.CENTER);
                add(panelChooseWord, BorderLayout.CENTER);
                add(panelMultiplayer, BorderLayout.CENTER);

                panelStart.setVisible(true);
                panelLoginName.setVisible(false);
                panelDifficulty.setVisible(false);
                panelGameRules.setVisible(false);
                panelGameRulesP2.setVisible(false);
                panelChooseWord.setVisible(false);
                panelMultiplayer.setVisible(false);
        }

        public void showPanelLoginName() {
                panelStart.setVisible(false);
                panelLoginName.initComponents();
                panelLoginName.setVisible(true);
                panelDifficulty.setVisible(false);
                panelGameRules.setVisible(false);
                panelGameRulesP2.setVisible(false);
                panelChooseWord.setVisible(false);
                panelOnePlayer.setVisible(false);
                panelMultiplayer.setVisible(false);
                panelLoginName.revalidate();
                panelLoginName.repaint();
        }

        public void showPanelDifficulty() {
                panelLoginName.setVisible(false);
                panelStart.setVisible(false);
                panelGameRules.setVisible(false);
                panelChooseWord.setVisible(false);
                panelGameRulesP2.setVisible(false);
                panelDifficulty.setVisible(true);
                panelOnePlayer.setVisible(false);
                panelMultiplayer.setVisible(false);
                panelDifficulty.revalidate();
                panelDifficulty.repaint();
        }

        public void showPanelStart() {
                panelStart.setVisible(true);
                panelLoginName.setVisible(false);
                panelDifficulty.setVisible(false);
                panelGameRules.setVisible(false);
                panelChooseWord.setVisible(false);
                panelOnePlayer.setVisible(false);
                panelGameRulesP2.setVisible(false);
                panelMultiplayer.setVisible(false);
                panelStart.revalidate();
                panelStart.repaint();
        }

        public void showPanelGameRules() {
                panelStart.setVisible(false);
                panelLoginName.setVisible(false);
                panelDifficulty.setVisible(false);
                panelGameRules.setVisible(true);
                panelChooseWord.setVisible(false);
                panelGameRulesP2.setVisible(false);
                panelOnePlayer.setVisible(false);
                panelMultiplayer.setVisible(false);
                panelGameRules.revalidate();
                panelGameRules.repaint();
        }

        public void showPanelChooseWord() {
                panelStart.setVisible(false);
                panelLoginName.setVisible(false);
                panelDifficulty.setVisible(false);
                panelGameRules.setVisible(false);
                panelGameRulesP2.setVisible(false);
                panelChooseWord.setVisible(true);
                panelOnePlayer.setVisible(false);
                panelMultiplayer.setVisible(false);
                panelChooseWord.revalidate();
                panelChooseWord.repaint();
        }

        public void showPanelGameRulesP2() {
                panelStart.setVisible(false);
                panelLoginName.setVisible(false);
                panelDifficulty.setVisible(false);
                panelGameRules.setVisible(false);
                panelGameRulesP2.setVisible(true);
                panelChooseWord.setVisible(false);
                panelOnePlayer.setVisible(false);
                panelGameRulesP2.revalidate();
                panelGameRulesP2.repaint();
        }

        public void showPanelOnePlayer() {
                panelOnePlayer = new PanelOnePlayer(getClient());
                add(panelOnePlayer, BorderLayout.CENTER);
                panelOnePlayer.setVisible(false);
                panelStart.setVisible(false);
                panelLoginName.setVisible(false);
                panelDifficulty.setVisible(false);
                panelGameRules.setVisible(false);
                panelGameRulesP2.setVisible(false);
                panelChooseWord.setVisible(false);
                panelOnePlayer.addPlayerNamePanel();
                panelOnePlayer.addWordDisplayPanel();
                panelOnePlayer.setVisible(true);
                panelMultiplayer.setVisible(false);
                panelOnePlayer.revalidate();
                panelOnePlayer.repaint();
        }

        public void showPanelMultiplayer() {
                panelStart.setVisible(false);
                panelLoginName.setVisible(false);
                panelDifficulty.setVisible(false);
                panelGameRules.setVisible(false);
                panelGameRulesP2.setVisible(false);
                panelChooseWord.setVisible(false);
                panelOnePlayer.setVisible(false);
                panelMultiplayer.setVisible(true);
                panelMultiplayer.revalidate();
                panelMultiplayer.repaint();
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

}