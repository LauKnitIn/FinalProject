package co.edu.uptc.view;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;

import co.edu.uptc.view.constants.ColorPalette;
import co.edu.uptc.view.constants.FontPalette;

public class PanelChooseWord extends JPanel {

    private Image backgroundImage;
    private List<String> palabras = Arrays.asList("AFABLE", "VALIENTE", "CURIOSO", "HONESTO");
    private JLabel palabraLabel;
    private int palabraIndex = 0;

    public PanelChooseWord() {
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

    public void initComponents() {
        addListWords();
        addOptions();
    }

    private void addListWords() {
        palabraLabel = createLabel(palabras.get(palabraIndex), 400, 400, 515, 70);
        add(palabraLabel);
    }

    private JLabel createLabel(String text, int x, int y, int width, int height) {
        JLabel label = new JLabel(text);
        label.setBounds(x, y, width, height);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setFont(FontPalette.LISTWORDS_FONT);
        return label;
    }

    private void addOptions() {

        ImageIcon trianguloArriba = new ImageIcon("frontend-proyecto\\resources\\Triangulo arriba.png");
        ImageIcon trianguloAbajo = new ImageIcon("frontend-proyecto\\resources\\Triangulo abajo.png");

        addButton("Elige", 320, 220, e -> {});
        addButton("Continuar", 320, 550, e -> showPanelOnePlayerOrMultiplayer());

        addButton(trianguloArriba, 850, 330, e -> navigateUp());
        addButton(trianguloAbajo, 850, 450, e -> navigateDown());
    }

    private void navigateDown() {
        palabraIndex = (palabraIndex + 1) % palabras.size();
        palabraLabel.setText(palabras.get(palabraIndex));
    }

    private void navigateUp() {
        palabraIndex = (palabraIndex - 1 + palabras.size()) % palabras.size();
        palabraLabel.setText(palabras.get(palabraIndex));
    }

    private void addButton(String text, int x, int y, ActionListener actionListener) {
        RoundedButton button = new RoundedButton(text, 20);
        button.setBounds(x, y, 660, 90);
        button.setBackground(ColorPalette.COLOR_BUTTON);
        button.setForeground(Color.WHITE);
        button.setFont(FontPalette.BUTTON_3_FONT);
        button.addActionListener(actionListener);
        add(button);
    }

    private void addButton(ImageIcon icon, int x, int y, ActionListener actionListener) {
        JButton button = new JButton();
        button.setBounds(x, y, 150, 80);
        button.setIcon(icon);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.addActionListener(actionListener);
        add(button);
    }

        private void showPanelOnePlayerOrMultiplayer() {
        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(PanelChooseWord.this);
        if (topFrame instanceof View) {
            View view = (View) topFrame;
            if (view.isMultiplayer()) {
                view.showPanelMultiplayer();
            } else {
                view.showPanelOnePlayer();
            }
        }
    }

}