package co.edu.uptc.view;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.awt.Toolkit;
import java.awt.Dimension;

import co.edu.uptc.view.constants.ColorPalette;
import co.edu.uptc.view.constants.FontPalette;

public class PanelDifficulty extends JPanel {

    private Image backgroundImage;
    private JLabel titleLabel;
    private List<RoundedButton> difficultyButtons = new ArrayList<>();

    public PanelDifficulty() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(screenSize.width, screenSize.height);
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        backgroundImage = new ImageIcon("resources\\FondoMenus.png").getImage();
        setLayout(null);
        initComponents();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }

    private void initComponents() {
        addTitle();
        addOptions();
    }

    private void addTitle() {
        int labelWidth = 1000;
        int labelHeight = 80;
        int yPosition = 150;

        titleLabel = createLabel("Dificultad", 0, yPosition, labelWidth, labelHeight);
        add(titleLabel);
    }

    private JLabel createLabel(String text, int x, int y, int width, int height) {
        JLabel label = new JLabel(text);
        label.setBounds(x, y, width, height);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setFont(FontPalette.TITLE_3_FONT);
        return label;
    }

    private void addOptions() {
        difficultyButtons.clear();
        addButton("Fácil", 420, 250, e -> sendDifficulty("1"));
        addButton("Normal", 420, 370, e -> sendDifficulty("2"));
        addButton("Difícil", 420, 480, e -> sendDifficulty("3"));
        addButton("Extremo", 420, 590, e -> sendDifficulty("4"));
    }

    private String addButton(String text, int x, int y, ActionListener actionListener) {
        RoundedButton button = new RoundedButton(text, 20);
        button.setBounds(x, y, 460, 80);
        button.setBackground(ColorPalette.COLOR_BUTTON);
        button.setForeground(Color.WHITE);
        button.setFont(FontPalette.BUTTON_FONT);
        button.addActionListener(actionListener);
        add(button);
        difficultyButtons.add(button);
        return button.getText().toLowerCase();
    }

    private void sendDifficulty(String difficulty) {
        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        if (topFrame instanceof View) {
            boolean isMultiplayer = ((View) topFrame).isMultiplayer();
            if (!isMultiplayer) {
                ((View) topFrame).getClient().sendGameData(difficulty, isMultiplayer);
                showPanelOnePlayer();
            } else {
                showPanelChooseWord();
            }
        }
    }

    private void showPanelOnePlayer() {
        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(PanelDifficulty.this);
        if (topFrame instanceof View) {
            ((View) topFrame).showPanelOnePlayer();
        }
    }

    private void showPanelChooseWord() {
        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(PanelDifficulty.this);
        if (topFrame instanceof View) {
            ((View) topFrame).showPanelChooseWord();
        }
    }

    @Override
    public void doLayout() {
        super.doLayout();
        if (titleLabel != null) {
            int panelWidth = getWidth();
            int labelWidth = titleLabel.getWidth();
            int yPosition = titleLabel.getY();
            int labelHeight = titleLabel.getHeight();
            int xPosition = (panelWidth - labelWidth) / 2;
            titleLabel.setBounds(xPosition, yPosition, labelWidth, labelHeight);
        }

        for (RoundedButton button : difficultyButtons) {
            int panelWidth = getWidth();
            int btnWidth = button.getWidth();
            int yPosition = button.getY();
            int btnHeight = button.getHeight();
            int xPosition = (panelWidth - btnWidth) / 2;
            button.setBounds(xPosition, yPosition, btnWidth, btnHeight);
        }

    }
}