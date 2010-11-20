package com.arpia49;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;

public class ActSonidosCrear extends Activity {

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.add_sonido);

		final EditText et_nombreSonido = (EditText) findViewById(R.id.et_nombreSonido);
		final EditText et_descSonido = (EditText) findViewById(R.id.et_descSonido);

		Button bt = (Button) findViewById(R.id.bt_siguiente);

		bt.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent outData = new Intent();

				String nombre_sonido = et_nombreSonido.getText().toString();
				if (nombre_sonido.compareTo("") == 0)
					nombre_sonido = getString(R.string.et_nombre);
				outData.putExtra("nombreSonido", nombre_sonido);

				String desc_sonido = et_descSonido.getText().toString();
				if (desc_sonido.compareTo("") == 0)
					desc_sonido = getString(R.string.et_desc);
				outData.putExtra("descSonido", desc_sonido);
				setResult(Activity.RESULT_OK, outData);
				finish();
			}
		});

		et_nombreSonido.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				String defecto = getString(R.string.et_nombre);
				String actual = et_nombreSonido.getText().toString();

				if (hasFocus && actual.compareTo(defecto) == 0)
					et_nombreSonido.setText("");

				else if (!hasFocus && actual.compareTo("") == 0)
					et_nombreSonido.setText(defecto);
			}
		});

		et_descSonido.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				String defecto = getString(R.string.et_desc);
				String actual = et_descSonido.getText().toString();

				if (hasFocus && actual.compareTo(defecto) == 0)
					et_descSonido.setText("");

				else if (!hasFocus && actual.compareTo("") == 0)
					et_descSonido.setText(defecto);
			}
		});
	}
}
