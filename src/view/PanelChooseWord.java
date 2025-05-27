package view;

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
import java.awt.Toolkit;
import java.awt.Dimension;

import view.constants.ColorPalette;
import view.constants.FontPalette;

public class PanelChooseWord extends JPanel {

    private Image backgroundImage;
    private List<String> palabras = Arrays.asList("AFABLE", "VALIENTE", "CURIOSO", "HONESTO");
    private JLabel palabraLabel;
    private int palabraIndex = 0;
    private JLabel eligeLabel;
    private RoundedButton continuarButton;
    private JButton btnTrianguloArriba;
private JButton btnTrianguloAbajo;

    public PanelChooseWord() {
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
        addListWords();
        addOptions();
    }

    private void addListWords() {
        palabraLabel = createLabel(palabras.get(palabraIndex), 0, 400, 515, 70); 
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

    eligeLabel = createEligeLabel();
    add(eligeLabel);

    continuarButton = addButton("Continuar", 0, 550, e -> showPanelOnePlayerOrMultiplayer());

    btnTrianguloArriba = addButton(trianguloArriba, 0, 330, e -> navigateUp());
    btnTrianguloAbajo = addButton(trianguloAbajo, 0, 450, e -> navigateDown());
}

    private JLabel createEligeLabel() {
        JLabel eligeLabel = new JLabel("Elige una palabra", SwingConstants.CENTER);
        eligeLabel.setBounds(0, 220, 660, 90); 
        eligeLabel.setOpaque(false);
        eligeLabel.setFont(FontPalette.TITLE_5_FONT);
        return eligeLabel;
    }

    private void navigateDown() {
        palabraIndex = (palabraIndex + 1) % palabras.size();
        palabraLabel.setText(palabras.get(palabraIndex));
    }

    private void navigateUp() {
        palabraIndex = (palabraIndex - 1 + palabras.size()) % palabras.size();
        palabraLabel.setText(palabras.get(palabraIndex));
    }

    private RoundedButton addButton(String text, int x, int y, ActionListener actionListener) {
        RoundedButton button = new RoundedButton(text, 20);
        button.setBounds(x, y, 660, 90);
        button.setBackground(ColorPalette.COLOR_BUTTON);
        button.setForeground(Color.WHITE);
        button.setFont(FontPalette.BUTTON_3_FONT);
        button.addActionListener(actionListener);
        add(button);
        return button;
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

    @Override
public void doLayout() {
    super.doLayout();
    if (palabraLabel != null) {
        int panelWidth = getWidth();
        int labelWidth = palabraLabel.getWidth();
        int yPosition = palabraLabel.getY();
        int labelHeight = palabraLabel.getHeight();
        int xPosition = (panelWidth - labelWidth) / 2;
        palabraLabel.setBounds(xPosition, yPosition, labelWidth, labelHeight);
    }
    if (eligeLabel != null) {
        int panelWidth = getWidth();
        int labelWidth = eligeLabel.getWidth();
        int yPosition = eligeLabel.getY();
        int labelHeight = eligeLabel.getHeight();
        int xPosition = (panelWidth - labelWidth) / 2;
        eligeLabel.setBounds(xPosition, yPosition, labelWidth, labelHeight);
    }
    if (continuarButton != null) {
        int panelWidth = getWidth();
        int btnWidth = continuarButton.getWidth();
        int yPosition = continuarButton.getY();
        int btnHeight = continuarButton.getHeight();
        int xPosition = (panelWidth - btnWidth) / 2;
        continuarButton.setBounds(xPosition, yPosition, btnWidth, btnHeight);
    }
 
    if (btnTrianguloArriba != null) {
        int panelWidth = getWidth();
        int btnWidth = btnTrianguloArriba.getWidth();
        int yPosition = btnTrianguloArriba.getY();
        int btnHeight = btnTrianguloArriba.getHeight();
        int xPosition = panelWidth - btnWidth - 450; 
        btnTrianguloArriba.setBounds(xPosition, yPosition, btnWidth, btnHeight);
    }
    if (btnTrianguloAbajo != null) {
        int panelWidth = getWidth();
        int btnWidth = btnTrianguloAbajo.getWidth();
        int yPosition = btnTrianguloAbajo.getY();
        int btnHeight = btnTrianguloAbajo.getHeight();
        int xPosition = panelWidth - btnWidth - 450; 
        btnTrianguloAbajo.setBounds(xPosition, yPosition, btnWidth, btnHeight);
    }
}

}