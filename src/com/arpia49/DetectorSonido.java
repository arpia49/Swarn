package com.arpia49;

import java.math.BigDecimal;
import java.util.Stack;

import com.arpia49.utilidades.FFTTransformer;
import com.arpia49.utilidades.Window;

import android.content.SharedPreferences;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;

/**
 * @author  Hashir N A <hashir@mobware4u.com>
 */
public class DetectorSonido implements Runnable {
	private static final int FREQUENCY = 8000;
	private static final int CHANNEL = AudioFormat.CHANNEL_CONFIGURATION_MONO;
	private static final int ENCODING = AudioFormat.ENCODING_PCM_16BIT;
	private int BUFFSIZE = 256;
	public volatile boolean isRunning = false;
	public volatile static Stack<short[]> ruidos;	
	private static final double P0 = 0.000002;
	/**
	 * @uml.property  name="instance"
	 * @uml.associationEnd  
	 */
	private static DetectorSonido instance = null;
	AudioRecord recordInstance = null;
	static SharedPreferences sp = null;
	private String valorFinal = null;
	
	protected DetectorSonido() {
		// Exists only to defeat instantiation.
	}

	/**
	 * @return
	 * @uml.property  name="instance"
	 */
	public static DetectorSonido getInstance() {
		if (instance == null) {
			instance = new DetectorSonido();
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

			int maxmedia = 0;
			int maxpos = 0;
			
			for (int k = 0; k < ruidos.size(); k++){
	
			
				double splValue = 0.0;
				double rmsValue = 0.0;
				
				for (int i = 0; i < BUFFSIZE - 1; i++) {
					rmsValue += ruidos.get(k)[i] * ruidos.get(k)[i];
	
				}
				rmsValue = rmsValue / BUFFSIZE;
				rmsValue = Math.sqrt(rmsValue);
	
				splValue = 20 * Math.log10(rmsValue / P0);
				splValue = round(splValue, 2);
				splValue = splValue - 80;
				
				if(splValue>maxmedia){
					maxmedia=(int)splValue;
					maxpos=k;
				}
			
			}

		    // Fourier Transform calculator we use for calculating the spectrum.
		    FFTTransformer spectrumAnalyser;
			
		    // The selected windowing function.
		    Window.Function windowFunction = Window.Function.BLACKMAN_HARRIS;
		    
		    spectrumAnalyser = new FFTTransformer(BUFFSIZE, windowFunction);
		    
		    spectrumAnalyser.setInput(ruidos.get(maxpos), 0, BUFFSIZE);
		    spectrumAnalyser.transform();

		    float nuevo[] = new float[BUFFSIZE/2];
		    float nuevo2[] = new float[BUFFSIZE];
		    nuevo2 = spectrumAnalyser.getResults(nuevo);
		    float nuevo3[] = new float[BUFFSIZE/2];
		    spectrumAnalyser.findKeyFrequencies(nuevo2, nuevo3);
		    
			for (int i = 0; i< (BUFFSIZE/2)-1; i++){
				sb.append(new Float(nuevo3[i])+",");
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

				for (int i = 0; i < BUFFSIZE - 1; i++) {
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
