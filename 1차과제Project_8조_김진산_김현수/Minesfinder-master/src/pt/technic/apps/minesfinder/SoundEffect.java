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
	   private static Clip[] clip = new Clip[3];
	   private static String[] file = {"src/pt/technic/apps/minesfinder/music/BGM.wav",
			   "src/pt/technic/apps/minesfinder/music/¹öÆ°.wav","src/pt/technic/apps/minesfinder/music/Æø¹ß1.wav"};
	   //0 : BGM
	   //1 : Å¬¸¯À½
	   //2 : Æø¹ßÀ½
//	   public SoundEffect() 
//	   {
//		 for(int i = 0; i < 3; i++) {
//			 try {
//				 File base = new File(file[i]);
//		         String basePath = base.getAbsolutePath();
//		         File audio = new File(basePath);
//		          
//		         AudioInputStream ais = AudioSystem.getAudioInputStream(audio);
//		         clip[i] = AudioSystem.getClip();
//		         clip[i].open(ais);
//		       }
//		       catch(Exception e) {
//		          e.printStackTrace();
//		       } 
//		  }
//	    }
	   
	    public static void bgmClip() {
	    	try {
				 File base = new File(file[0]);
		         String basePath = base.getAbsolutePath();
		         File audio = new File(basePath);
		          
		         AudioInputStream ais = AudioSystem.getAudioInputStream(audio);
		         clip[0] = AudioSystem.getClip();
		         clip[0].open(ais);
		       }
		       catch(Exception e) {
		          e.printStackTrace();
		    } 
		  
	    	clip[0].start();
	    	clip[0].loop(clip[0].LOOP_CONTINUOUSLY);
	    }
	    
	    public static void clickClip() {
	    	try {
				 File base = new File(file[1]);
		         String basePath = base.getAbsolutePath();
		         File audio = new File(basePath);
		          
		         AudioInputStream ais = AudioSystem.getAudioInputStream(audio);
		         clip[1] = AudioSystem.getClip();
		         clip[1].open(ais);
		       }
		       catch(Exception e) {
		          e.printStackTrace();
		     } 
	   
	    	clip[1].start();
	    }
	    
	    public static void boomClip() {
	    	try {
				 File base = new File(file[2]);
		         String basePath = base.getAbsolutePath();
		         File audio = new File(basePath);
		          
		         AudioInputStream ais = AudioSystem.getAudioInputStream(audio);
		         clip[2] = AudioSystem.getClip();
		         clip[2].open(ais);
		       }
		       catch(Exception e) {
		          e.printStackTrace();
		     } 
	    	clip[2].start();
	    }
//	    public void startClip(int num) {
//	       clip[num].start();     
//	    }
//	    
//	    public void loopStartClip() {
//	       clip[].start();
//	       clip.loop(clip.LOOP_CONTINUOUSLY);
//	    }
//	    
//	    public void stopClip() {
//	       clip.stop();
//	    }
	   
	}
