package co.edu.uptc.view;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JButton;

import co.edu.uptc.view.constants.ColorPalette;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.io.File;

public class WinRoundedButton extends JButton {

    private int cornerRadius;

    public WinRoundedButton(String text, int cornerRadius) {
        super(text);
        this.cornerRadius = cornerRadius;
        setFocusPainted(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        buttonEffects();
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(ColorPalette.COLOR_BACK_MENU_BUTTON_HOVER);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(ColorPalette.COLOR_BACK_MENU_BUTTON);
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
     
        g2.setColor(getBackground());
        g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius));

        super.paintComponent(g);
        g2.dispose();
    }

    public void buttonEffects() {
        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playSound("GUI-CLIENT_SIDE\\src\\co\\edu\\uptc\\resources\\SonidoHoja.wav");
            }
        });
    }

    public static void playSound(String nombreArchivo) {
        try {
            File sound = new File(nombreArchivo);
            if (!sound.exists()) {
                System.out.println("Archivo no encontrado: " + sound.getAbsolutePath());
                return;
            }

            AudioInputStream audio = AudioSystem.getAudioInputStream(sound);
            Clip clip = AudioSystem.getClip();
            clip.open(audio);
            clip.start();
        } catch (Exception e) {
            System.out.println("No se pudo reproducir el sonido");
            e.printStackTrace();
        }
    }
}
