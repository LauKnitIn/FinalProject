package view;

import javax.swing.JButton;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.event.*;
import java.io.File;
import javax.sound.sampled.*;

public class RoundedButtonv2 extends JButton {

    public RoundedButtonv2(String text, int cornerRadius) {
        super(text);
        setFocusPainted(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        addActionListener(new ActionListener() {
            @Override 
            public void actionPerformed (ActionEvent e){
                playSound("resources\\SonidoHoja.wav");
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Dibujar un círculo
        g2.setColor(getBackground());
        g2.fill(new Ellipse2D.Float(0, 0, getWidth(), getHeight()));

        super.paintComponent(g);
        g2.dispose();
    }

    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Dibujar el borde del círculo
        g2.setColor(getForeground());
        g2.draw(new Ellipse2D.Float(0, 0, getWidth() - 1, getHeight() - 1));

        g2.dispose();
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