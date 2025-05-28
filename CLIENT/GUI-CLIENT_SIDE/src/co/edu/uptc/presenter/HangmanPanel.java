package co.edu.uptc.presenter;

import javax.swing.*;
import java.awt.*;

public class HangmanPanel extends JPanel {
    private String progress = "A _ T I C _ _ _ T I T _ C I _ _ A L";

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Color.BLACK);
        g2.setFont(new Font("Arial", Font.BOLD, 32));

        int spacing = 50;
        int startX = 20;
        int baseY = 50;
        int lineSpacing = 60;

        String[] chars = progress.split(" ");

        for (int i = 0; i < chars.length; i++) {
            int row = i / 6;
            int col = i % 6;

            int x = startX + col * spacing;
            int y = baseY + row * lineSpacing;

            g2.setStroke(new BasicStroke(3));
            g2.drawLine(x, y, x + 40, y);

            if (!chars[i].equals("_")) {
                g2.drawString(chars[i], x + 10, y - 10);
            }
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Test Hangman");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        HangmanPanel panel = new HangmanPanel();
        frame.add(panel);

        frame.setVisible(true);
    }
}
