package co.edu.uptc.view;

import java.awt.Color;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import co.edu.uptc.view.constants.ColorPalette;
import co.edu.uptc.view.constants.FontPalette;

public class WinDialog extends JDialog {

    private JLabel winTextLabel;
    private WinRoundedButton backButton;

    public WinDialog() {
        setSize(500, 180);
        getContentPane().setBackground(Color.WHITE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);
        setModal(true);
        setTitle("¡FELICIDADES!");
        playSound("GUI-CLIENT_SIDE\\src\\co\\edu\\uptc\\resources\\Sonido-Victoria.wav");
        initComponents();
    }

    public void initComponents() {
        createWinTextLabel();
        createBackButton();
    }

    public void createWinTextLabel() {
        winTextLabel = new JLabel("¡HAS GANADO LA PARTIDA!");
        winTextLabel.setBounds(5, 10, getWidth(), 50);
        winTextLabel.setForeground(ColorPalette.COLOR_BACK_MENU_BUTTON);
        winTextLabel.setFont(FontPalette.BUTTON_WIN_DEFEAT_FONT);
        add(winTextLabel);
    }

    public void createBackButton() {
        backButton = new WinRoundedButton("OK", 20);
        backButton.setBounds(90, 65, 300, 60);
        backButton.setBackground(ColorPalette.COLOR_BACK_MENU_BUTTON);
        backButton.setForeground(Color.WHITE);
        backButton.setFont(FontPalette.BUTTON_BACK_TO_MENU_FONT);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showMainView();
                dispose();
            }
        });
        add(backButton);
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

    public void showMainView() {
        Window window = SwingUtilities.getWindowAncestor(this);
        if (window instanceof View) {
            ((View) window).showPanelStart();
        } else {
            System.err.println("No se pudo encontrar la ventana padre como instancia de View.");
        }
    }
}
