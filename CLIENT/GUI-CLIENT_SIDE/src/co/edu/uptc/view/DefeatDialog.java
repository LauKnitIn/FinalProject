package co.edu.uptc.view;

import java.awt.Color;
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

public class DefeatDialog extends JDialog {

    private JLabel defeatTextLabel;
    private DefeatRoundedButton backButton;

    public DefeatDialog() {
        setSize(515, 180);
        getContentPane().setBackground(Color.WHITE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);
        setModal(true);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setTitle("¡MALA SUERTE!");
        playSound("GUI-CLIENT_SIDE\\src\\co\\edu\\uptc\\resources\\Sonido-Derrota.wav");
        initComponents();
    }

    public void initComponents() {
        createWinTextLabel();
        createBackButton();
    }

    public void createWinTextLabel() {
        defeatTextLabel = new JLabel("¡HAS PERDIDO LA PARTIDA!");
        defeatTextLabel.setBounds(3, 10, getWidth(), 50);
        defeatTextLabel.setForeground(ColorPalette.COLOR_DEFEAT_BACK_MENU_BUTTON);
        defeatTextLabel.setFont(FontPalette.BUTTON_WIN_DEFEAT_FONT);
        add(defeatTextLabel);
    }

    public void createBackButton() {
        backButton = new DefeatRoundedButton("OK", 20);
        backButton.setBounds(100, 65, 300, 60);
        backButton.setBackground(ColorPalette.COLOR_DEFEAT_BACK_MENU_BUTTON);
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
        java.awt.Window window = SwingUtilities.getWindowAncestor(this);
        if (window instanceof View view) {
            view.showPanelStart();
        }
    }
}
