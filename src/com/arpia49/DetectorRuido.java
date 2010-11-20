package com.arpia49;

import java.math.BigDecimal;
import java.util.Stack;

import android.content.SharedPreferences;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;

/**
 * @author  Hashir N A <hashir@mobware4u.com>
 */
public class DetectorRuido implements Runnable {
	private static final int FREQUENCY = 8000;
	private static final int CHANNEL = AudioFormat.CHANNEL_CONFIGURATION_MONO;
	private static final int ENCODING = AudioFormat.ENCODING_PCM_16BIT;
	private int BUFFSIZE = 320;
	public volatile boolean isRunning = false;
	public volatile static Stack<short[]> ruidos;
	/**
	 * @uml.property  name="instance"
	 * @uml.associationEnd  
	 */
	private static DetectorRuido instance = null;
	AudioRecord recordInstance = null;
	static SharedPreferences sp = null;
	/**
	 * @uml.property  name="valorFinal"
	 */
	private String valorFinal = null;

	/**
	 * @return
	 * @uml.property  name="valorFinal"
	 */
	public String getValorFinal(){
		return valorFinal;
	}
	
	protected DetectorRuido() {
		// Exists only to defeat instantiation.
	}

	/**
	 * @return
	 * @uml.property  name="instance"
	 */
	public static DetectorRuido getInstance() {
		if (instance == null) {
			instance = new DetectorRuido();
			ruidos = new Stack<short[]>();
		}
		return instance;
	}

	public static void setPreferences(SharedPreferences pref) {
		sp = pref;
	}

	/**
	 * starts the engine.
	 */
	public void comienza_prueba() {
		if (!this.isRunning) {
			this.isRunning = true;
			Thread t = new Thread(this);
			t.start();
		}
	}

	/**
	 * stops the engine
	 */
	public String stop_engine() {
		if (this.isRunning) {
			this.isRunning = false;
			recordInstance.stop();
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i< BUFFSIZE-1; i++){
				short menor = ruidos.get(0)[i];
				for (int k = 0; k < ruidos.size(); k++){
					if(ruidos.get(k)[i]<menor){
						menor=ruidos.get(k)[i];
					}
				}
				sb.append(menor+",");
			}
			valorFinal = sb.toString();
		}
		return valorFinal;
	}


	/*
	 * The main thread. Records audio and calculates the SPL The heart of the
	 * Engine.
	 */
	public void run() {
		try {
			android.os.Process
					.setThreadPriority(android.os.Process.THREAD_PRIORITY_URGENT_AUDIO);
			recordInstance = new AudioRecord(MediaRecorder.AudioSource.MIC,
					FREQUENCY, CHANNEL, ENCODING, 8000);

			recordInstance.startRecording();
			short[] tempBuffer = new short[BUFFSIZE];

			while (this.isRunning) {

				for (int i = 0; i < BUFFSIZE; i++) {
					tempBuffer[i] = 0;
				}

				recordInstance.read(tempBuffer, 0, BUFFSIZE);
				ruidos.add(tempBuffer.clone());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * Utility function for rounding decimal values
	 */
	public double round(double d, int decimalPlace) {
		// see the Javadoc about why we use a String in the constructor
		// http://java.sun.com/j2se/1.5.0/docs/api/java/math/BigDecimal.html#BigDecimal(double)
		BigDecimal bd = new BigDecimal(Double.toString(d));
		bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
		return bd.doubleValue();
	}

}
