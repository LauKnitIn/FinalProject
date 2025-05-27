package view.sounds;

import javax.sound.sampled.*;
import java.io.*;

public class BackgroundMusic {

     private Clip clip;
     private FloatControl volumeControl;

      public void playFromFile(String ruta) {
        try {
            File file = new File(ruta); // ejemplo: "resources/sonido.wav"
            AudioInputStream audioInput = AudioSystem.getAudioInputStream(file);
            clip = AudioSystem.getClip();
            clip.open(audioInput);

             if (clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                setVolume(0.5f); // Volumen entre 0.0 (mínimo) y 1.0 (máximo)
            } else {
                System.out.println("Este sistema no soporta control de volumen.");
            }

            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setVolume(float volume) {
        if (volumeControl != null) {
            float min = volumeControl.getMinimum(); // por ejemplo, -80.0 dB
            float max = volumeControl.getMaximum(); // por ejemplo, 6.0 dB
            float gain = min + (max - min) * volume; // Interpolación
            volumeControl.setValue(gain);
        }
    }

    public void stop() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }
    
}
