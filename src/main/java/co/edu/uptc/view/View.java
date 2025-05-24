package co.edu.uptc.view;

import javax.swing.JFrame;
import java.awt.BorderLayout;

import co.edu.uptc.presenter.Presenter;

public class View extends JFrame {

    private PanelStart panelStart;
    private PanelLoginName panelLoginName;
    private PanelDifficulty panelDifficulty;
    private PanelGameRules panelGameRules;
    private PanelGameRulesP2 panelGameRulesP2;
    private PanelChooseWord panelChooseWord;
    private PanelOnePlayer panelOnePlayer;
    private PanelMultiplayer panelMultiplayer;

    private boolean isMultiplayer = false;

    private Presenter presenter;

    public View(Presenter presenter) {
        this.presenter = presenter;
        setTitle("El ahorcado");
        setSize(1300, 800);
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
        panelOnePlayer = new PanelOnePlayer();
        panelMultiplayer = new PanelMultiplayer();

        add(panelStart, BorderLayout.CENTER);
        add(panelLoginName, BorderLayout.CENTER);
        add(panelDifficulty, BorderLayout.CENTER);
        add(panelGameRules, BorderLayout.CENTER);
        add(panelGameRulesP2, BorderLayout.CENTER);
        add(panelChooseWord, BorderLayout.CENTER);
        add(panelOnePlayer, BorderLayout.CENTER);
        add(panelMultiplayer, BorderLayout.CENTER);

        panelStart.setVisible(true);
        panelLoginName.setVisible(false);
        panelDifficulty.setVisible(false);
        panelGameRules.setVisible(false);
        panelGameRulesP2.setVisible(false);
        panelChooseWord.setVisible(false);
        panelOnePlayer.setVisible(false);
        panelMultiplayer.setVisible(false);
    }

    public void showPanelLoginName() {
        panelStart.setVisible(false);
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

    public void showPanelOnePlayer(){
        panelStart.setVisible(false);
        panelLoginName.setVisible(false);
        panelDifficulty.setVisible(false);
        panelGameRules.setVisible(false);
        panelGameRulesP2.setVisible(false);
        panelChooseWord.setVisible(false);
        panelOnePlayer.setVisible(true);
        panelMultiplayer.setVisible(false);
        panelOnePlayer.revalidate();
        panelOnePlayer.repaint();
    }

    public void showPanelMultiplayer(){
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

    public void setMultiplayer(boolean isMultiplayer) {
        this.isMultiplayer = isMultiplayer;
    }
    
    public boolean isMultiplayer() {
        return isMultiplayer;
    }
}
