package com.arpia49;

import java.math.BigDecimal;
import java.util.Stack;
import java.util.Vector;

import org.hermit.dsp.FFTTransformer;
import org.hermit.dsp.Window;

import android.content.SharedPreferences;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;

/**
 * @author  Hashir N A <hashir@mobware4u.com>
 */
public class splEngine implements Runnable {
	private static final String BAJO = "84";
	private static final String ALTO = "97";
	private static final int FREQUENCY = 8000;
	private static final int CHANNEL = AudioFormat.CHANNEL_CONFIGURATION_MONO;
	private static final int ENCODING = AudioFormat.ENCODING_PCM_16BIT;
	private int BUFFSIZE = 256;
	private static final double P0 = 0.000002;
	public volatile boolean isRunning = false;
	public volatile static Stack<Evento> pila;
	public volatile static Vector <Vector<Float>> datos;
	public volatile static Stack<Long> fechas;
	/**
	 * @uml.property  name="instance"
	 * @uml.associationEnd  
	 */
	private static splEngine instance = null;
	AudioRecord recordInstance = null;
	static SharedPreferences sp = null;

	protected splEngine() {
		// Exists only to defeat instantiation.
	}

	/**
	 * @return
	 * @uml.property  name="instance"
	 */
	public static splEngine getInstance() {
		if (instance == null) {
			instance = new splEngine();
			pila = new Stack<Evento>();
			datos = new Vector <Vector<Float>>();
			fechas = new Stack<Long>();
		}
		return instance;
	}

	public static void setPreferences(SharedPreferences pref) {
		sp = pref;
	}

	/**
	 * starts the engine.
	 */
	public void start_engine(Evento evento, boolean unpause) {
		if(!unpause){
			pila.push(evento);
			if (!this.isRunning) {
				this.isRunning = true;
				int clave = evento.getClaveSonido();
				Vector<Float> tmpDatos = new Vector <Float>();
				if(clave!=0){
					String tmp[] = ListaSonidos.element(ListaSonidos.obtenerIdDesdeClave(clave)).getDatos().split(",");
					for(int i = 0;i<(BUFFSIZE/2)-1;i++){
						tmpDatos.addElement(Float.parseFloat(tmp[i]));
					}					
				}else{
					for(int i = 0;i<(BUFFSIZE/2)-1;i++){
						tmpDatos.addElement(new Float (0));
					}
				}
				datos.addElement(tmpDatos);
				fechas.push(new Long(0));
				Thread t = new Thread(this);
				t.start();
			}
		}
		else{
			if(pila.size()>0 && !isRunning){
				this.isRunning = true;
				Thread t = new Thread(this);
				t.start();
			}
		}	
	}

	/**
	 * stops the engine
	 */
	public void stop_engine(boolean pause, int claveAlarma) {
		if(!pause){
			for(int i=0;i<pila.size();i++){
				if(claveAlarma==pila.elementAt(i).getClave()){
					pila.remove(i);
					datos.remove(i);
					break;
				}
			}
			if (pila.size() == 0) {
				this.isRunning = false;
				recordInstance.stop();
			}
		}else{
			if(isRunning){
				isRunning = false;
				recordInstance.stop();
			}
		}
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
				splValue = splValue - 80;

				for(int m=0; m<pila.size();m++){
					int comparar = Integer.parseInt(sp.getString("sonidoFuerte",
					BAJO));
					
					if (pila.elementAt(m).getMuyFuerte())
						comparar = Integer.parseInt(sp.getString("sonidoMuyFuerte",
								ALTO));
					if (splValue >= comparar) {
						if (ListaNotificaciones.size() == 0
								|| (System.currentTimeMillis() - fechas.elementAt(m) > 10000)) {
	
							if(pila.elementAt(m).getClaveSonido()==0){
	
								fechas.setElementAt(System.currentTimeMillis(),m);
								Evento.getHandler().sendEmptyMessage(
										pila.elementAt(m).getClave());
							}else{
								float contador = 0;
								
							    // Fourier Transform calculator we use for calculating the spectrum.
							    FFTTransformer spectrumAnalyser;
								
							    // The selected windowing function.
							    Window.Function windowFunction = Window.Function.BLACKMAN_HARRIS;

							    spectrumAnalyser = new FFTTransformer(BUFFSIZE, windowFunction);
							    
							    spectrumAnalyser.setInput(tempBuffer, 0, BUFFSIZE);
							    spectrumAnalyser.transform();

							    float nuevo[] = new float[BUFFSIZE/2];
							    float nuevo2[] = new float[BUFFSIZE];
							    nuevo2 = spectrumAnalyser.getResults(nuevo);
							    float nuevo3[] = new float[BUFFSIZE/2];
							    spectrumAnalyser.findKeyFrequencies(nuevo2, nuevo3);

								float a;
								float b;
							    
								for(int l=0;l<(BUFFSIZE/2) - 1;l++){
									a=datos.elementAt(m).elementAt(l);
									b=nuevo3[l];
									
									if(a>0 && b>0){
										if(a>b){
											contador = contador + 5*b/a;
										}
										else{
											contador = contador + 5*a/b;
										}
										contador++;
									}else if(datos.elementAt(m).elementAt(l)>0 || nuevo3[l]>0){
										contador=contador-5;
									}
								}
								if(contador>4){
									fechas.setElementAt(System.currentTimeMillis(),m);
									Evento.getHandler().sendEmptyMessage(
											pila.elementAt(m).getClave());
								}
							}
						}
					}
				}
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
