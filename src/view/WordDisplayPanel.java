package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JPanel;

import view.constants.FontPalette;

public class WordDisplayPanel extends JPanel {
    private String wordToGuess = "AMABLE"; 
    private Set<Integer> revealedIndices = new HashSet<>();

    public WordDisplayPanel() {
        setOpaque(false);
    }

    public void revealLetter(char c) {
        for (int i = 0; i < wordToGuess.length(); i++) {
            if (Character.toUpperCase(wordToGuess.charAt(i)) == Character.toUpperCase(c)) {
                revealedIndices.add(i);
            }
        }
        repaint();
    }

    public void setWord(String word) {
        this.wordToGuess = word;
        revealedIndices.clear();
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

        for (int i = 0; i < wordToGuess.length(); i++) {
            int x = startX + i * spacing;
            g2.setStroke(new BasicStroke(3));
            g2.drawLine(x, baseY, x + 50, baseY); 

            if (revealedIndices.contains(i)) {
                String letter = String.valueOf(wordToGuess.charAt(i));
                g2.drawString(letter, x + 5, baseY - 10);
            }
        }
    }
}
