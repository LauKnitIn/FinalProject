package view;

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

import view.constants.FontPalette;

public class PanelGameRules extends JPanel {

    private Image backgroundImage;

    public PanelGameRules() {
        setSize(1300, 800);
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
        ImageIcon xIcon = new ImageIcon("resources\\image x.png");
        ImageIcon arrowIcon = new ImageIcon("resources\\image avanzar.png");

        addButton(xIcon, 0, 30, e -> showPanelStart());
        addButton(arrowIcon, 900, 635, e -> showPanelGameRulesP2());

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

}
