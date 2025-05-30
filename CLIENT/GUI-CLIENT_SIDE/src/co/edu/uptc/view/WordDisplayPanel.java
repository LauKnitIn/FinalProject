package co.edu.uptc.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

import co.edu.uptc.view.constants.FontPalette;

public class WordDisplayPanel extends JPanel {

    private String progress = "";

    public WordDisplayPanel() {
        setOpaque(false);
    }

    public void setProgress(String progress) {
        this.progress = progress;
        revalidate();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Color.BLACK);
        g2.setFont(FontPalette.WORD_DISPLAY_FONT);

        int panelWidth = getWidth();
        int panelHeight = getHeight();
        int margin = 20;

        String[] chars = progress.split(" ");
        int totalLetters = chars.length;

        // Cálculo del espaciado y ajuste del tamaño de fuente
        int maxSpacing = 80;
        int spacing = Math.min(maxSpacing, (panelWidth - 2 * margin) / Math.max(totalLetters, 1));
        int lineLength = Math.max(spacing - 20, 10);

        // Calculamos el ancho total que ocuparán las letras y líneas
        int totalWidth = (spacing * totalLetters);

        // Centramos horizontalmente
        int startX = (panelWidth - totalWidth) / 2;
        int baseY = panelHeight / 2 + 20;

        for (int i = 0; i < chars.length; i++) {
            int x = startX + i * spacing;
            g2.setStroke(new BasicStroke(3));
            g2.drawLine(x, baseY, x + lineLength, baseY);

            if (!chars[i].equals("_")) {
                g2.drawString(chars[i], x + 10, baseY - 10);
            }
        }
    }
}
