package co.edu.uptc.view.sounds;

import javax.sound.sampled.*;
import java.io.*;

public class BackgroundMusic {

     private Clip clip;
     private FloatControl volumeControl;

      public void playFromFile(String ruta) {
        try {
            File file = new File(ruta);
            AudioInputStream audioInput = AudioSystem.getAudioInputStream(file);
            clip = AudioSystem.getClip();
            clip.open(audioInput);

             if (clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                setVolume(0.5f);
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
            float min = volumeControl.getMinimum();
            float max = volumeControl.getMaximum();
            float gain = min + (max - min) * volume; 
            volumeControl.setValue(gain);
        }
    }

    public void stop() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }
    
}
