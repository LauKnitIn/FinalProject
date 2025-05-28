package co.edu.uptc.view;

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
import java.awt.Toolkit;
import java.awt.Dimension;

import co.edu.uptc.view.constants.ColorPalette;
import co.edu.uptc.view.constants.FontPalette;

public class PanelLoginName extends JPanel {

    private Image backgroundImage;
    private JLabel titleLabel;
    private RoundedTextField nameField;
    private RoundedButton continuarButton;

    public PanelLoginName() {
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
        int labelHeight = 100;
        int yPosition = 150;
        titleLabel = createLabel("Ingrese su nombre", 0, yPosition, labelWidth, labelHeight);
        add(titleLabel);
    }

    private JLabel createLabel(String text, int x, int y, int width, int height) {
        JLabel label = new JLabel(text);
        label.setBounds(x, y, width, height);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setFont(FontPalette.TITLE_2_FONT);
        return label;
    }

    private void addOptions() {//MOD para enviar nombre al cliente
        nameField = addTextField("  _______________________________  ", 0, 350, e -> {});
        continuarButton = addButton("Continuar", 480, 500, e -> {
            String name = nameField.getText().trim();
            if (!(name.isEmpty())) {
                System.out.println("NOMBRE INGRESADO -> " + name);
                ((View)SwingUtilities.getWindowAncestor(PanelLoginName.this)).getClient().sendName(name);//enviar nombre
                showPanelDifficulty();
            }
            
        });
    }

private RoundedButton addButton(String text, int x, int y, ActionListener actionListener) {
    RoundedButton button = new RoundedButton(text, 20);
    button.setBounds(x, y, 370, 70);
    button.setBackground(ColorPalette.COLOR_BUTTON);
    button.setForeground(Color.WHITE);
    button.setFont(FontPalette.BUTTON_2_FONT);
    button.addActionListener(actionListener);
    add(button);
    return button;
}

private RoundedTextField addTextField(String text, int x, int y, ActionListener actionListener) {
    RoundedTextField textField = new RoundedTextField(1, 30, 30, text);
    textField.setBounds(x, y, 650, 70);
    textField.setFont(FontPalette.TEXTFIELD_FONT);
    textField.setBackground(ColorPalette.COLOR_TEXTFIELD);
    textField.setForeground(Color.WHITE);
    add(textField);
    return textField;
}


    public void showPanelDifficulty() {
        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(PanelLoginName.this);
        if (topFrame instanceof View) {
            ((View) topFrame).showPanelDifficulty();
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
    if (nameField != null) {
        int panelWidth = getWidth();
        int fieldWidth = nameField.getWidth();
        int yPosition = nameField.getY();
        int fieldHeight = nameField.getHeight();
        int xPosition = (panelWidth - fieldWidth) / 2;
        nameField.setBounds(xPosition, yPosition, fieldWidth, fieldHeight);
    }
    if (continuarButton != null) {
        int panelWidth = getWidth();
        int btnWidth = continuarButton.getWidth();
        int yPosition = continuarButton.getY();
        int btnHeight = continuarButton.getHeight();
        int xPosition = (panelWidth - btnWidth) / 2;
        continuarButton.setBounds(xPosition, yPosition, btnWidth, btnHeight);
    }
}

}