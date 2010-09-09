package com.arpia49;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class VistaSonidosTimbre extends Activity {
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.timbre_sonido);
		
		final Button bt = (Button) findViewById(R.id.bt_procesar_sonido);
		
		bt.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				
				bt.setText("procesando");
				bt.setEnabled(false);
				
				Intent outData = new Intent();
//
//				String nombre_sonido = et_nombreSonido.getText().toString();
//				if (nombre_sonido.compareTo("") == 0)
//					nombre_sonido = getString(R.string.et_nombre);
//				outData.putExtra("nombreSonido", nombre_sonido);
//
//				String desc_sonido = et_descSonido.getText().toString();
//				if (desc_sonido.compareTo("") == 0)
//					desc_sonido = getString(R.string.et_desc);
				outData.putExtra("descSonido", "yeah");
				setResult(Activity.RESULT_OK, outData);
				finish();
			}
		});
	}
}
