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

import co.edu.uptc.view.constants.ColorPalette;
import co.edu.uptc.view.constants.FontPalette;

public class PanelDifficulty extends JPanel {

    private Image backgroundImage;

    public PanelDifficulty() {
        setSize(1300, 800);
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        backgroundImage = new ImageIcon("frontend-proyecto\\resources\\FondoMenus.png").getImage();
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
        int frameWidth = 1300;
        int labelWidth = 1000;
        int labelHeight = 80;
        int xPosition = (frameWidth - labelWidth) / 2;

        JLabel title = createLabel("Dificultad", xPosition, 150, labelWidth, labelHeight);
        add(title);
    }

    private JLabel createLabel(String text, int x, int y, int width, int height) {
        JLabel label = new JLabel(text);
        label.setBounds(x, y, width, height);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setFont(FontPalette.TITLE_3_FONT);
        return label;
    }

    private void addOptions() {
        addButton("Fácil", 420, 250, e -> sendDifficulty("1"));
        addButton("Normal", 420, 370, e -> sendDifficulty("2"));
        addButton("Difícil", 420, 480, e -> sendDifficulty("3"));
        addButton("Extremo", 420, 590, e -> sendDifficulty("4"));
    }

    // MOD para obtener el texto de la opcion seleccionada y enviar informacion por
    // medio del cliente al servidor
    private String addButton(String text, int x, int y, ActionListener actionListener) {
        RoundedButton button = new RoundedButton(text, 20);
        button.setBounds(x, y, 460, 80);
        button.setBackground(ColorPalette.COLOR_BUTTON);
        button.setForeground(Color.WHITE);
        button.setFont(FontPalette.BUTTON_FONT);
        button.addActionListener(actionListener);
        add(button);
        return button.getText().toLowerCase();
    }

    private void sendDifficulty(String difficulty) {//MOD Se agrega metodo para configurar el juego en el servidor
        boolean isMultiplayer = ((View) SwingUtilities.getWindowAncestor(this)).isMultiplayer();
        if (!isMultiplayer) {
            ((View) SwingUtilities.getWindowAncestor(this)).getClient().sendGameData(difficulty, isMultiplayer);
            showPanelOnePlayer();

        } else {
            showPanelChooseWord();//MANEJAR MULTIPLES CLIENTES
        }
    }

    private void showPanelChooseWord() {
        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(PanelDifficulty.this);
        if (topFrame instanceof View) {
            ((View) topFrame).showPanelChooseWord();
        }
    }

    private void showPanelOnePlayer() {
        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(PanelDifficulty.this);
        if (topFrame instanceof View) {
            ((View) topFrame).showPanelOnePlayer();
        }
    }

}