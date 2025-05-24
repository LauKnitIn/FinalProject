package view;

import javax.swing.BorderFactory;
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

import view.constants.ColorPalette;
import view.constants.FontPalette;

public class PanelLoginName extends JPanel {

    private Image backgroundImage;

    public PanelLoginName() {
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

        JLabel title = createLabel("Ingrese su nombre", xPosition, 150, labelWidth, labelHeight);
        add(title);
    }

    private JLabel createLabel(String text, int x, int y, int width, int height) {
        JLabel label = new JLabel(text);
        label.setBounds(x, y, width, height);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setFont(FontPalette.TITLE_2_FONT);
        return label;
    }

    private void addOptions() {
        addTextField("  _______________________________  ", 330, 350, e -> {});

        addButton("Continuar", 480, 500, e -> showPanelDifficulty());
    }

    private void addButton(String text, int x, int y, ActionListener actionListener) {
        RoundedButton button = new RoundedButton(text, 20); 
        button.setBounds(x, y, 370, 70);
        button.setBackground(ColorPalette.COLOR_BUTTON); 
        button.setForeground(Color.WHITE);
        button.setFont(FontPalette.BUTTON_2_FONT); 
        button.addActionListener(actionListener);
        add(button);
    }

        private void addTextField(String text, int x, int y, ActionListener actionListener) {
        RoundedTextField textField = new RoundedTextField(1, 30, 30, text); 
        textField.setBounds(x, y, 650, 70);
        textField.setFont(FontPalette.TEXTFIELD_FONT);
        textField.setBackground(ColorPalette.COLOR_TEXTFIELD); 
        textField.setForeground(Color.WHITE); 
        add(textField);
    }

    public void showPanelDifficulty() {
        JFrame topFrame =(JFrame) SwingUtilities.getWindowAncestor(PanelLoginName.this);
        if (topFrame instanceof View) {
         ((View) topFrame).showPanelDifficulty();
       }
     }
 
    
}