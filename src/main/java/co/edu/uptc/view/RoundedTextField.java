package co.edu.uptc.view;

import javax.swing.JTextField;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class RoundedTextField extends JTextField {

    private int arcWidth;
    private int arcHeight;
    private String placeholder;

    public RoundedTextField(int columns, int arcWidth, int arcHeight, String placeholder) {
        super(columns);
        this.arcWidth = arcWidth;
        this.arcHeight = arcHeight;
        this.placeholder = placeholder;
        setOpaque(false); 
        addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (getText().equals(placeholder)) {
                    setText("");
                    setForeground(Color.white); 
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (getText().isEmpty()) {
                    setText(placeholder);
                    setForeground(Color.GRAY); 
                }
            }
        });

        setText(placeholder);
        setForeground(Color.GRAY);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), arcWidth, arcHeight);

        g2.setColor(getForeground());
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, arcWidth, arcHeight);

        g2.dispose();
        super.paintComponent(g);
    }

    @Override
    protected void paintBorder(Graphics g) {
    }
    
}