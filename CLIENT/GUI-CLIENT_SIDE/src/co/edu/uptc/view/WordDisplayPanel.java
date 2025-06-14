package co.edu.uptc.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import co.edu.uptc.view.constants.FontPalette;

public class WordDisplayPanel extends JPanel {
    
    private String progress = "_ _ _ _ _ _";

      public WordDisplayPanel() {
        setOpaque(false);
    }

    public void setProgress(String progress) {
        this.progress = progress;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Color.BLACK);
        g2.setFont(FontPalette.WORD_DISPLAY_FONT);

        int spacing = 80;
        int startX = 20;
        int baseY = 40;

        // Divide por espacio para respetar el formato del servidor: "_ _ _ A _ S"
        String[] chars = progress.split(" ");

        for (int i = 0; i < chars.length; i++) {
            int x = startX + i * spacing;
            g2.setStroke(new BasicStroke(3));
            g2.drawLine(x, baseY, x + 50, baseY);

            if (!chars[i].equals("_")) {
                g2.drawString(chars[i], x + 10, baseY - 10);
            }
        }
   
    }
}
