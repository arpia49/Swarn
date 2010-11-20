package com.arpia49;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * @author  arpia49
 */
public class ActSonidosTimbre extends Activity {
	/**
	 * @uml.property  name="progressThread"
	 * @uml.associationEnd  
	 */
	ProgressThread progressThread;
	/**
	 * @uml.property  name="detector"
	 * @uml.associationEnd  
	 */
	DetectorSonido detector;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.timbre_sonido);

		final Button bt = (Button) findViewById(R.id.bt_procesar_sonido);

		bt.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {

				detector = DetectorSonido.getInstance();
				showDialog(0);
			}
		});
	}

	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case 0:

			detector.comienza_prueba();
			ProgressDialog dialog = ProgressDialog.show(this, "",
					"Procesando. Por favor, espere...", true);
			progressThread = new ProgressThread(handler);
			progressThread.start();
			return dialog;
		default:
			return null;
		}
	}

	// Define the Handler that receives messages from the thread and update the
	// progress
	final Handler handler = new Handler() {
		public void handleMessage(Message msg) {

			if (msg.getData().getBoolean("fin")) {
				String tmp = detector.stop_engine();
				dismissDialog(0);
				Intent outData = new Intent();
				outData.putExtra("Sonido", tmp);
				setResult(Activity.RESULT_OK, outData);
				finish();
			}
		}
	};

	/** Nested class that performs progress calculations (counting) */
	private class ProgressThread extends Thread {
		Handler mHandler;

		ProgressThread(Handler h) {
			mHandler = h;
		}

		public void run() {
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					Log.e("ERROR", "Thread Interrupted");
				}
				Message msg = mHandler.obtainMessage();
				Bundle b = new Bundle();
				b.putBoolean("fin", true);
				msg.setData(b);
				mHandler.sendMessage(msg);
		}

	}

}
