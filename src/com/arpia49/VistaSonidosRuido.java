package com.arpia49;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class VistaSonidosRuido extends Activity {
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.ruido_sonido);
		
		final Button bt = (Button) findViewById(R.id.bt_procesar_ruido);
		
		bt.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				
				bt.setText("procesando");
				bt.setEnabled(false);
				
				Intent outData = new Intent();


				splEngine engine = splEngine.getInstance();
				
				outData.putExtra("descSonido", "yeah");
				setResult(Activity.RESULT_OK, outData);
				finish();
			}
		});
	}
}
