package co.edu.uptc.view;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionListener;

import co.edu.uptc.view.constants.FontPalette;

public class PanelGameRulesP2 extends JPanel {

    private Image backgroundImage;

    public PanelGameRulesP2() {
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
        addTitle();
        addOptions();
    }

    private void addTitle() {
        int frameWidth = 1300;
        int labelWidth = 1000;
        int labelHeight = 70;
        int xPosition = (frameWidth - labelWidth) / 2;

        JLabel title = createLabel("Reglas de Juego", xPosition, 150, labelWidth, labelHeight);
        add(title);
    }

    private JLabel createLabel(String text, int x, int y, int width, int height) {
        JLabel label = new JLabel(text);
        label.setBounds(x, y, width, height);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setFont(FontPalette.TITLE_4_FONT);
        return label;
    }

    private void addOptions() {
        ImageIcon xIcon = new ImageIcon("frontend-proyecto\\resources\\image x.png"); 
        ImageIcon arrowIcon = new ImageIcon("frontend-proyecto\\resources\\image devolver.png"); 

        addButton(xIcon, 0, 30, e -> showPanelStart());
        addButton(arrowIcon, 900, 635, e -> showPanelGameRules());

        addTextArea("Comienza el juego: \n\n" +
            "• Cada jugador intenta adivinar la palabra del oponente.\n\n" +
            "Como Jugar:\n\n" +
            "• En cada turno, puedes ingresar una letra.\n" +
            "• Sila letra esta en la palabra oculta, se revelara\n" +
            "• en todas sus pociciones\n" +
            "• Si la letra no esta en la palabra, se añadira una \n"+
            "  parte al dibujo del ahorcado.\n\n" +
            "El juego termina cuando: \n\n" +
            "• Un jugador adivina la palabra completa antes de que se \n" +
            "  complete el ahorcado, ese jugador gana.\n" +
            "• Un jugador completa el ahorcado antes de adivinar la \n" +
            "  palabra, ese jugador pierde");
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

    private void addTextArea(String text) {
        JTextArea textArea = new JTextArea(text);
        textArea.setBounds(280, 260, 950, 350); 
        textArea.setFont(FontPalette.TEXTAREA_FONT); 
        textArea.setLineWrap(true); 
        textArea.setWrapStyleWord(true); 
        textArea.setEditable(false); 
        textArea.setOpaque(false); 
        textArea.setBorder(null); 
        textArea.setForeground(Color.BLACK); 

        add(textArea); 
    }



    private void showPanelStart() {
        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(PanelGameRulesP2.this);
        if (topFrame instanceof View) {
           ((View) topFrame).showPanelStart();
        }
    }

    private void showPanelGameRules() {
        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(PanelGameRulesP2.this);
        if (topFrame instanceof View) {
           ((View) topFrame).showPanelGameRules();
        }
    }

}