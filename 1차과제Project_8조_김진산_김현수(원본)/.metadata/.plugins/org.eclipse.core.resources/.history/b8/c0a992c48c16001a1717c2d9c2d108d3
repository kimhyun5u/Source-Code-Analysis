package pt.technic.apps.minesfinder;


import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.plaf.synth.SynthSeparatorUI;


public class SoundEffect {
	   private Clip clip;
	   
	   public SoundEffect(String file) 
	   {
	       try {
	    	  File base = new File(file);
	          String basePath = base.getAbsolutePath();
	          File audio = new File(basePath);
	          
	          AudioInputStream ais = AudioSystem.getAudioInputStream(audio);
	          clip = AudioSystem.getClip();
	          clip.open(ais);
	       }
	       catch(Exception e) {
	          e.printStackTrace();
	       }
	      
	    }
	    public void startClip() {
	       clip.start();     
	    }
	    public void loopStartClip() {
	       clip.start();
	       clip.loop(clip.LOOP_CONTINUOUSLY);
	    }
	    public void stopClip() {
	       clip.stop();
	    }
	   
	}
