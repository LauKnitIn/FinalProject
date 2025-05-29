package co.edu.uptc.view;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionListener;
import java.awt.Toolkit;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.Component;

import co.edu.uptc.client.Client;
import co.edu.uptc.view.constants.ColorPalette;
import co.edu.uptc.view.constants.FontPalette;

public class PanelOnePlayer extends JPanel {

    private Image backgroundImage;
    private JPanel keyboardBackground;
    private JPanel hangmanPanel;
    private WordDisplayPanel wordDisplayPanel;
    private JPanel namePanel;
    private JButton btnSalir;
    private JButton btnHome;
    private int partesAhorcado = 0;
    private Client client;
    private int maxIntentos = 10;

    public PanelOnePlayer(Client client) {
        this.client = client;
        this.maxIntentos = client.getMaxIntentos();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(screenSize.width, screenSize.height);
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        backgroundImage = new ImageIcon("GUI-CLIENT_SIDE\\src\\co\\edu\\uptc\\resources\\FondoMadera2.jpg").getImage();
        setLayout(null);
        initComponents();

        client.setIntentosListener(new Client.IntentosListener() {
            @Override
            public void onIntentosRestantes(int intentosRestantes) {
                int errores = maxIntentos - intentosRestantes;
                int totalPartes = 10;
                int partesADibujar = (int) Math.ceil((errores * totalPartes) / (double) maxIntentos);
                setPartesAhorcado(partesADibujar);
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }

    private void initComponents() {
        addOptions();
        addkeyboard();
        addHangmanPanel();
        addPlayerNamePanel();
        addWordDisplayPanel();
    }

    private void addOptions() {
        ImageIcon exitIcon = new ImageIcon("GUI-CLIENT_SIDE\\src\\co\\edu\\uptc\\resources\\cerrar-sesion.png");
        ImageIcon homeIcon = new ImageIcon("GUI-CLIENT_SIDE\\src\\co\\edu\\uptc\\resources\\home.png");

        btnSalir = addButton(exitIcon, 0, 0, e -> System.exit(0));
        btnHome = addButton(homeIcon, 0, 0, e -> showPanelLStar());
    }

    private void addkeyboard() {
        keyboardBackground = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g; 
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(ColorPalette.COLOR_KEYBOARD);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 50, 50);
            }
        };
        keyboardBackground.setOpaque(false);
        keyboardBackground.setBounds(680, 450, 570, 200);
        keyboardBackground.setLayout(null);
        add(keyboardBackground);

        int startX = 10;
        int startY = 10;
        int buttonSpacing = 55;

        keyboardBackground.add(createButton("Q", startX, startY));
        keyboardBackground.add(createButton("W", startX + buttonSpacing, startY));
        keyboardBackground.add(createButton("E", startX + 2 * buttonSpacing, startY));
        keyboardBackground.add(createButton("R", startX + 3 * buttonSpacing, startY));
        keyboardBackground.add(createButton("T", startX + 4 * buttonSpacing, startY));
        keyboardBackground.add(createButton("Y", startX + 5 * buttonSpacing, startY));
        keyboardBackground.add(createButton("U", startX + 6 * buttonSpacing, startY));
        keyboardBackground.add(createButton("I", startX + 7 * buttonSpacing, startY));
        keyboardBackground.add(createButton("O", startX + 8 * buttonSpacing, startY));
        keyboardBackground.add(createButton("P", startX + 9 * buttonSpacing, startY));

        startY += 60;
        keyboardBackground.add(createButton("A", startX, startY));
        keyboardBackground.add(createButton("S", startX + buttonSpacing, startY));
        keyboardBackground.add(createButton("D", startX + 2 * buttonSpacing, startY));
        keyboardBackground.add(createButton("F", startX + 3 * buttonSpacing, startY));
        keyboardBackground.add(createButton("G", startX + 4 * buttonSpacing, startY));
        keyboardBackground.add(createButton("H", startX + 5 * buttonSpacing, startY));
        keyboardBackground.add(createButton("J", startX + 6 * buttonSpacing, startY));
        keyboardBackground.add(createButton("K", startX + 7 * buttonSpacing, startY));
        keyboardBackground.add(createButton("L", startX + 8 * buttonSpacing, startY));
        keyboardBackground.add(createButton("Ñ", startX + 9 * buttonSpacing, startY));

        startY += 60;
        int offsetX = startX + buttonSpacing + 30;
        keyboardBackground.add(createButton("Z", offsetX, startY));
        keyboardBackground.add(createButton("X", offsetX + buttonSpacing, startY));
        keyboardBackground.add(createButton("C", offsetX + 2 * buttonSpacing, startY));
        keyboardBackground.add(createButton("V", offsetX + 3 * buttonSpacing, startY));
        keyboardBackground.add(createButton("B", offsetX + 4 * buttonSpacing, startY));
        keyboardBackground.add(createButton("N", offsetX + 5 * buttonSpacing, startY));
        keyboardBackground.add(createButton("M", offsetX + 6 * buttonSpacing, startY));
    }

    private void addHangmanPanel() {
        hangmanPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2d.setColor(ColorPalette.COLOR_HANGMAN_BACKGROUND);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 50, 50);
                g2d.setColor(Color.BLACK);

                int baseX = getWidth() / 2 - 90;
                int baseY = 60;

                // Identificadores de partes:
                // 1: base del suelo
                if (partesAhorcado > 0)
                    g2d.drawLine(baseX, baseY + 300, baseX + 60, baseY + 300);
                // 2: poste vertical
                if (partesAhorcado > 1)
                    g2d.drawLine(baseX + 30, baseY + 300, baseX + 30, baseY);
                // 3: barra superior
                if (partesAhorcado > 2)
                    g2d.drawLine(baseX + 30, baseY, baseX + 150, baseY);
                // 4: cuerda
                if (partesAhorcado > 3)
                    g2d.drawLine(baseX + 150, baseY, baseX + 150, baseY + 50);
                // 5: cabeza
                if (partesAhorcado > 4)
                    g2d.drawOval(baseX + 135, baseY + 50, 30, 30);
                // 6: cuerpo
                if (partesAhorcado > 5)
                    g2d.drawLine(baseX + 150, baseY + 80, baseX + 150, baseY + 160);
                // 7: brazo izquierdo
                if (partesAhorcado > 6)
                    g2d.drawLine(baseX + 150, baseY + 110, baseX + 110, baseY + 110);
                // 8: brazo derecho
                if (partesAhorcado > 7)
                    g2d.drawLine(baseX + 150, baseY + 110, baseX + 190, baseY + 110);
                // 9: pierna izquierda
                if (partesAhorcado > 8)
                    g2d.drawLine(baseX + 150, baseY + 160, baseX + 120, baseY + 220);
                // 10: pierna derecha
                if (partesAhorcado > 9)
                    g2d.drawLine(baseX + 150, baseY + 160, baseX + 180, baseY + 220);
            }
        };

        hangmanPanel.setOpaque(false);
        hangmanPanel.setBounds(50, 150, 600, 500);
        hangmanPanel.setLayout(null);
        add(hangmanPanel);
    }

    // Para actualizar el dibujo desde fuera:
    // Llama a este método cuando recibas los intentos restantes del servidor
    public void setPartesAhorcado(int errores) {
        this.partesAhorcado = errores;
        hangmanPanel.repaint();
    }

    private void addPlayerNamePanel() {
        namePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(ColorPalette.COLOR_NAME_PLAYER_FOND);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 60, 60);
            }
        };
        namePanel.setOpaque(false);
        namePanel.setLayout(new BorderLayout());
        namePanel.setBounds(830, 380, 280, 50);

        JLabel nameLabel = new JLabel("ALEXANDER", SwingConstants.CENTER);
        nameLabel.setFont(FontPalette.JLABEL_NAME_FONT);
        nameLabel.setForeground(Color.BLACK);

        namePanel.add(nameLabel, BorderLayout.CENTER);
        add(namePanel);
    }

    private void addWordDisplayPanel() {
        wordDisplayPanel = new WordDisplayPanel();
        wordDisplayPanel.setBounds(730, 250, 800, 70);
        add(wordDisplayPanel);
    }

    private JButton createButton(String text, int x, int y) {
        RoundedButtonv2 button = new RoundedButtonv2(text, 20);
        button.setBounds(x, y, 50, 50);
        button.setBackground(ColorPalette.COLOR_BUTTON);
        button.setForeground(Color.WHITE);
        button.setFont(FontPalette.BUTTON_KERYBOARD_FONT);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
             String value = ((View)SwingUtilities.getWindowAncestor(PanelOnePlayer.this)).getClient().makeGuess(text);
             wordDisplayPanel.setProgress(value);
            }
        });
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

    private void showPanelLStar() {
    JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(PanelOnePlayer.this);
    if (topFrame instanceof View) {
        ((View) topFrame).showPanelStart();
        resetHangman();
    }
}

    public void setMaxIntentos(int maxIntentos) {
        this.maxIntentos = maxIntentos;
    }

    public void resetHangman() {
        this.partesAhorcado = 0;
        if (hangmanPanel != null) {
            hangmanPanel.repaint();
        }
        resetKeyboardButtons(); 
    }

    void resetKeyboardButtons() {
        if (keyboardBackground != null) {
            for (Component comp : keyboardBackground.getComponents()) {
                if (comp instanceof RoundedButtonv2) {
                    RoundedButtonv2 btn = (RoundedButtonv2) comp;
                    btn.setEnabled(true);
                    btn.setBackground(co.edu.uptc.view.constants.ColorPalette.COLOR_BUTTON);
                }
            }
        }
    }

    @Override
    public void doLayout() {
        super.doLayout();

        int panelWidth = getWidth();
        int panelHeight = getHeight();

        int kbX = 0, kbY = 0, kbWidth = 0, kbHeight = 0;
        if (keyboardBackground != null) {
            kbWidth = keyboardBackground.getWidth();
            kbHeight = keyboardBackground.getHeight();
            kbX = panelWidth - kbWidth - 150;
            kbY = panelHeight - kbHeight - 180;
            keyboardBackground.setBounds(kbX, kbY, kbWidth, kbHeight);
        }

        if (wordDisplayPanel != null) {
            int wdpWidth = wordDisplayPanel.getWidth();
            int wdpHeight = wordDisplayPanel.getHeight();
            int wdpX = panelWidth - wdpWidth - 0 + 125;
            int wdpY = panelHeight - kbHeight - wdpHeight - 340;
            wordDisplayPanel.setBounds(wdpX, wdpY, wdpWidth, wdpHeight);
        }

        if (keyboardBackground != null && namePanel != null) {
            int npWidth = namePanel.getWidth();
            int npHeight = namePanel.getHeight();
            int npX = kbX + (kbWidth - npWidth) / 2;
            int npY = kbY - npHeight - 50;
            namePanel.setBounds(npX, npY, npWidth, npHeight);
        }

        if (hangmanPanel != null) {
            int hpWidth = hangmanPanel.getWidth();
            int hpHeight = hangmanPanel.getHeight();
            int xPosition = 80;
            int yPosition = (panelHeight - hpHeight) / 2;
            hangmanPanel.setBounds(xPosition, yPosition, hpWidth, hpHeight);
        }
        
        if (btnSalir != null) {
            btnSalir.setBounds(30, 20, 150, 80); 
        }
        
        if (btnHome != null) {
            btnHome.setBounds(panelWidth - 150 - 30, 20, 150, 80); 
        }
    }
}