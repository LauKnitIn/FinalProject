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
import java.awt.Toolkit;
import java.awt.Dimension;

import co.edu.uptc.view.constants.FontPalette;

public class PanelGameRulesP2 extends JPanel {

    private Image backgroundImage;
    private JLabel titleLabel;
    private JTextArea rulesTextArea;
    private JButton btnCerrar;
    private JButton btnRetroceder;

    public PanelGameRulesP2() {
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

    public void initComponents() {
        addTitle();
        addOptions();
    }

    private void addTitle() {
        int labelWidth = 1000;
        int labelHeight = 70;
        int yPosition = 150;
        titleLabel = createLabel("Reglas de Juego", 0, yPosition, labelWidth, labelHeight);
        add(titleLabel);
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

        btnCerrar = addButton(xIcon, 0, 30, e -> showPanelStart());
        btnRetroceder = addButton(arrowIcon, 0, 635, e -> showPanelGameRules());

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
            "  palabra, ese jugador pierde.");
    }

    private JButton addButton(ImageIcon icon, int x, int y, ActionListener actionListener) {
        JButton button = new JButton();
        button.setBounds(x, y, 150, 80);
        button.setIcon(icon);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.addActionListener(actionListener);
        add(button);
        return button;
    }

    private void addTextArea(String text) {
        int areaWidth = 950;
        int areaHeight = 380;
        int yPosition = 260;
        rulesTextArea = new JTextArea(text);
        rulesTextArea.setBounds(0, yPosition, areaWidth, areaHeight);
        rulesTextArea.setFont(FontPalette.TEXTAREA_FONT);
        rulesTextArea.setLineWrap(true);
        rulesTextArea.setWrapStyleWord(true);
        rulesTextArea.setEditable(false);
        rulesTextArea.setOpaque(false);
        rulesTextArea.setBorder(null);
        rulesTextArea.setForeground(Color.BLACK);

        add(rulesTextArea);
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
        if (rulesTextArea != null) {
            int panelWidth = getWidth();
            int areaWidth = rulesTextArea.getWidth();
            int yPosition = rulesTextArea.getY();
            int areaHeight = rulesTextArea.getHeight();
            int xPosition = (panelWidth - areaWidth) / 2;
            rulesTextArea.setBounds(xPosition, yPosition, areaWidth, areaHeight);
        }
        
        if (btnCerrar != null) {
            btnCerrar.setBounds(30, 30, 150, 80);
        }
        
        if (btnRetroceder != null) {
            int panelWidth = getWidth();
            btnRetroceder.setBounds(panelWidth - 150 - 250, 635, 150, 80);
        }
    }
}