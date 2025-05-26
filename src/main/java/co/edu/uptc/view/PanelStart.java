package co.edu.uptc.view;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionListener;

import co.edu.uptc.view.constants.ColorPalette;
import co.edu.uptc.view.constants.FontPalette;

public class PanelStart extends JPanel {

    private Image backgroundImage;
    private JLabel titleLabel;
    private RoundedButton btnUnJugador;
    private RoundedButton btnMultijugador;
    private JButton btnInformacion;

    public PanelStart() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(screenSize.width, screenSize.height);
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
        int labelWidth = 1000;
        int labelHeight = 100;
        int yPosition = 180;
        titleLabel = createLabel("El Ahorcado", 0, yPosition, labelWidth, labelHeight);
        add(titleLabel);
    }

    private void addOptions() {
        ImageIcon informacionIcon = new ImageIcon("frontend-proyecto\\resources\\informacion.png");

        btnUnJugador = addButton("Un jugador", 0, 350, e -> {
            setGameMode(false);
            showPanelLoginName();
        });

        btnMultijugador = addButton("Multijugador", 0, 500, e -> {
            setGameMode(true);
            showPanelLoginName();
        });
        btnInformacion = addButton(informacionIcon, 970, 635, e -> showPanelGameRules());
    }

    private RoundedButton addButton(String text, int x, int y, ActionListener actionListener) {
        RoundedButton button = new RoundedButton(text, 20);
        button.setBounds(x, y, 660, 80);
        button.setBackground(ColorPalette.COLOR_BUTTON);
        button.setForeground(Color.WHITE);
        button.setFont(FontPalette.BUTTON_FONT);
        button.addActionListener(actionListener);
        add(button);
        return button;
    }

    private JLabel createLabel(String text, int x, int y, int width, int height) {
        JLabel label = new JLabel(text);
        label.setBounds(x, y, width, height);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setFont(FontPalette.TITLE_FONT);
        return label;
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

    private void setGameMode(boolean isMultiplayer) {
        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(PanelStart.this);
        if (topFrame instanceof View) {
            ((View) topFrame).setMultiplayer(isMultiplayer);
        }
    }

    private void showPanelLoginName() {
        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(PanelStart.this);
        if (topFrame instanceof View) {
            ((View) topFrame).showPanelLoginName();
        }
    }

    private void showPanelGameRules() {
        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(PanelStart.this);
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
        if (btnUnJugador != null) {
            int panelWidth = getWidth();
            int btnWidth = btnUnJugador.getWidth();
            int yPosition = btnUnJugador.getY();
            int btnHeight = btnUnJugador.getHeight();
            int xPosition = (panelWidth - btnWidth) / 2;
            btnUnJugador.setBounds(xPosition, yPosition, btnWidth, btnHeight);
        }
        if (btnMultijugador != null) {
            int panelWidth = getWidth();
            int btnWidth = btnMultijugador.getWidth();
            int yPosition = btnMultijugador.getY();
            int btnHeight = btnMultijugador.getHeight();
            int xPosition = (panelWidth - btnWidth) / 2;
            btnMultijugador.setBounds(xPosition, yPosition, btnWidth, btnHeight);
        }

        if (btnInformacion != null) {
            int panelWidth = getWidth();
            int panelHeight = getHeight();
            int btnWidth = btnInformacion.getWidth();
            int btnHeight = btnInformacion.getHeight();
            int originalX = 900;
            int originalY = 600;
            int originalPanelWidth = 1300;
            int originalPanelHeight = 800;
            int xPosition = panelWidth - (originalPanelWidth - originalX);
            int yPosition = panelHeight - (originalPanelHeight - originalY);
            btnInformacion.setBounds(xPosition, yPosition, btnWidth, btnHeight);
        }
    }

}