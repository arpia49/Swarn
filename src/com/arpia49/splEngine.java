package com.arpia49;

import java.math.BigDecimal;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Handler;
import android.preference.PreferenceManager;

/**
 * 
 * @author Hashir N A <hashir@mobware4u.com>
 * 
 */
public class splEngine extends Thread {
	public static final String PREFS_NAME = "PrefTimbre";
	private static final int FREQUENCY = 8000;
	private static final int CHANNEL = AudioFormat.CHANNEL_CONFIGURATION_MONO;
	private static final int ENCODING = AudioFormat.ENCODING_PCM_16BIT;
	private int BUFFSIZE = 320;
	private static final double P0 = 0.000002;
	public volatile boolean isRunning = false;
	private Handler handle;
	private Context context;

	static AudioRecord recordInstance = null;

	/**
	 * starts the engine.
	 */
	public void start_engine(Handler handle, Context context) {

		this.isRunning = true;
		this.handle = handle;
		this.context = context;
		this.start();

	}

	/**
	 * stops the engine
	 */
	public void stop_engine() {
		this.isRunning = false;
		recordInstance.stop();
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
			SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);

			while (this.isRunning) {
				double splValue = 0.0;
				double rmsValue = 0.0;

				for (int i = 0; i < BUFFSIZE - 1; i++) {
					tempBuffer[i] = 0;
				}

				recordInstance.read(tempBuffer, 0, BUFFSIZE);

				for (int i = 0; i < BUFFSIZE - 1; i++) {
					rmsValue += tempBuffer[i] * tempBuffer[i];

				}
				rmsValue = rmsValue / BUFFSIZE;
				rmsValue = Math.sqrt(rmsValue);

				splValue = 20 * Math.log10(rmsValue / P0);
				splValue = round(splValue, 2);
				
				if (splValue > 90 && settings.getBoolean("sonidoFuerte", false)) {
					handle.sendEmptyMessage((int) splValue);
					Thread.sleep(10000);
				}
				else if (splValue > 80) {
					handle.sendEmptyMessage((int) splValue);
					Thread.sleep(10000);
				}

			}

			recordInstance.stop();
		} catch (Exception e) {

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