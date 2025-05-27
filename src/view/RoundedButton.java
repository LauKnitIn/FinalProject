package view;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JButton;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;
import java.io.File;

public class RoundedButton extends JButton {

    private int cornerRadius;

    public RoundedButton(String text, int cornerRadius) {
        super(text);
        this.cornerRadius = cornerRadius;
        setFocusPainted(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playSound("resources\\SonidoHoja.wav");
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
