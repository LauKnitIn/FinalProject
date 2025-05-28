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
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionListener;

import view.constants.ColorPalette;
import view.constants.FontPalette;

public class PanelStart extends JPanel {

    private Image backgroundImage;

    public PanelStart() {
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
        int labelHeight = 100;
        int xPosition = (frameWidth - labelWidth) / 2;

        JLabel title = createLabel("El Ahorcado", xPosition, 180, labelWidth, labelHeight);
        add(title);
    }

    private void addOptions() {
        ImageIcon informacionIcon = new ImageIcon("frontend-proyecto\\resources\\informacion.png");
    
        addButton("Un jugador", 320, 350, e -> {
            setGameMode(false); 
            showPanelLoginName();
        });
        
        addButton("Multijugador", 320, 500, e -> {
            setGameMode(true); 
            showPanelLoginName();
        });
        addButton(informacionIcon, 970, 635, e -> showPanelGameRules());
    }

    private JLabel createLabel(String text, int x, int y, int width, int height) {
        JLabel label = new JLabel(text);
        label.setBounds(x, y, width, height);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setFont(FontPalette.TITLE_FONT); 
        return label;
    }

    private void addButton(String text, int x, int y, ActionListener actionListener) {
        RoundedButton button = new RoundedButton(text, 20); 
        button.setBounds(x, y, 660, 80);
        button.setBackground(ColorPalette.COLOR_BUTTON); 
        button.setForeground(Color.WHITE); 
        button.setFont(FontPalette.BUTTON_FONT); 
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
    
}