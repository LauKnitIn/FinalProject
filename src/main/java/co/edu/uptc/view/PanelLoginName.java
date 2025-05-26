package co.edu.uptc.view;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.text.AbstractDocument;
import javax.swing.text.DocumentFilter;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionListener;

import co.edu.uptc.view.constants.ColorPalette;
import co.edu.uptc.view.constants.FontPalette;

public class PanelLoginName extends JPanel {

    private Image backgroundImage;

    public PanelLoginName() {
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

    private void initComponents() {
        addTitle();
        addOptions();
    }

    private void addTitle() {
        int frameWidth = 1300;
        int labelWidth = 1000;
        int labelHeight = 100;
        int xPosition = (frameWidth - labelWidth) / 2;

        JLabel title = createLabel("Ingrese su nombre", xPosition, 150, labelWidth, labelHeight);
        add(title);
    }

    private JLabel createLabel(String text, int x, int y, int width, int height) {
        JLabel label = new JLabel(text);
        label.setBounds(x, y, width, height);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setFont(FontPalette.TITLE_2_FONT);
        return label;
    }

    private void addOptions() {
        addTextField("___________________", 330, 350, e -> {
        });

        addButton("Continuar", 480, 500, e -> showPanelDifficulty());
    }

    private void addButton(String text, int x, int y, ActionListener actionListener) {
        RoundedButton button = new RoundedButton(text, 20);
        button.setBounds(x, y, 370, 70);
        button.setBackground(ColorPalette.COLOR_BUTTON);
        button.setForeground(Color.WHITE);
        button.setFont(FontPalette.BUTTON_2_FONT);
        button.addActionListener(actionListener);
        add(button);
    }

    private void addTextField(String text, int x, int y, ActionListener actionListener) {
        RoundedTextField textField = new RoundedTextField(1, 30, 30, text);
        textField.setBounds(x, y, 650, 90);
        textField.setFont(FontPalette.TEXTFIELD_FONT);
        textField.setBackground(ColorPalette.COLOR_TEXTFIELD);
        textField.setForeground(Color.WHITE);
        textField.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        ((AbstractDocument) textField.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, javax.swing.text.AttributeSet attr)
                    throws javax.swing.text.BadLocationException {
                if (fb.getDocument().getLength() + string.length() <= 22) {
                    super.insertString(fb, offset, string, attr);
                }
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text,
                    javax.swing.text.AttributeSet attrs) throws javax.swing.text.BadLocationException {
                if (fb.getDocument().getLength() - length + (text != null ? text.length() : 0) <= 22) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }
        });

        add(textField);
    }

    public void showPanelDifficulty() {
        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(PanelLoginName.this);
        if (topFrame instanceof View) {
            ((View) topFrame).showPanelDifficulty();
        }
    }

}