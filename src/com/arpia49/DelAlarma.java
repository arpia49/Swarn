package com.arpia49;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class DelAlarma extends Activity {

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_alarma);

		Button bt = (Button) findViewById(R.id.botonAceptar);
		bt.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent outData = new Intent();
				final String kk = ((TextView) findViewById(R.id.nombreAlarma))
						.getText().toString();
				outData.putExtra("nombreAlarma", kk);
				setResult(Activity.RESULT_OK, outData);
				finish();

			}
		});
		Button bt2 = (Button) findViewById(R.id.botonCancelar);
		bt2.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				setResult(Activity.RESULT_CANCELED, null);
				finish();
			}
		});
	}
}
