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

public class PanelGameRules extends JPanel {

    private Image backgroundImage;
    private JLabel titleLabel;
    private JTextArea rulesTextArea;
    private JButton btnCerrar;
    private JButton btnAvanzar;

    public PanelGameRules() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(screenSize.width, screenSize.height);
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        backgroundImage = new ImageIcon("src\\co\\edu\\uptc\\resources\\FondoMenus.png").getImage();
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
        ImageIcon xIcon = new ImageIcon("src\\co\\edu\\uptc\\resources\\image X.png");
        ImageIcon arrowIcon = new ImageIcon("src\\co\\edu\\uptc\\resources\\image avanzar.png");

        btnCerrar = addButton(xIcon, 0, 30, e -> showPanelStart());
        btnAvanzar = addButton(arrowIcon, 0, 635, e -> showPanelGameRulesP2());

        addTextArea("Solitario: Selecciona el modo \"Solitario\".\n" +
                "• Se abrirá un panel de dificultad. Elige la dificultad deseada,\n" +
                "  que definira la longitud de la palabra a adivinar\n" +
                "• El sistema seleccionará una palabra al azar.\n" +
                "• Comienza a adivinar letras para revelar la palabra oculta.\n\n" +
                "Multijugador: Selecciona el modo \"Multijugador\".\n\n" +
                "• Al igual que en el modo solitario, elige la dificultad\n" +
                "• El sistema te emparejará con otro jugador que haya \n" +
                "  seleccionado el mismo modo y dificultad.\n" +
                "• Una vez emparejados, cada jugador debera ingresar\n" +
                "  una palabra para que el otro la adivine.\n" +
                "• Aunque un jugador complete el ahorcado, debe esperar a\n" +
                "  que elm otro jugador termine su turno. si los dos jugadores\n" +
                "  completan el dibujo del ahorcado,los dos jugadores pierden\n" +
                "• Gana el jugador que adivine la palabra primero sin completar\n" +
                "  el ahorcado.");
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
        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(PanelGameRules.this);
        if (topFrame instanceof View) {
            ((View) topFrame).showPanelStart();
        }
    }

    private void showPanelGameRulesP2() {
        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(PanelGameRules.this);
        if (topFrame instanceof View) {
            ((View) topFrame).showPanelGameRulesP2();
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
        
        if (btnAvanzar != null) {
            int panelWidth = getWidth();
            btnAvanzar.setBounds(panelWidth - 150 - 250, 635, 150, 80);
        }
    }

}