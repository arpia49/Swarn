package com.arpia49;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class AddAlarma extends Activity {

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_alarma);
		final EditText et = (EditText) findViewById(R.id.et_nombreAlarma);
		final EditText et2 = (EditText) findViewById(R.id.et_descAlarma);

		final EditText et3 = (EditText) findViewById(R.id.et_lugar);
		et3.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus)
					et3.setText("");
			}
		});
		et3.setEnabled(false);
		
		Button bt = (Button) findViewById(R.id.botonAceptar);
		bt.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent outData = new Intent();
				final String nombre_alarma = et.getText().toString();
				outData.putExtra("nombreAlarma", nombre_alarma);
				final String desc_alarma = et2.getText().toString();
				outData.putExtra("descAlarma", desc_alarma);
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

		final Button bt3 = (Button) findViewById(R.id.bt_verify);
		bt3.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Geocoder fwdGeocoder = new Geocoder(getApplicationContext(),
						Locale.getDefault());
				String streetAddress = et3.getText().toString();
				List<Address> locations = null;
				try {
					locations = fwdGeocoder.getFromLocationName(streetAddress,
							10);
					TextView tv = (TextView) findViewById(R.id.tv_latlng);
					String texto = "lat: "
							+ Double.toString(locations.get(0).getLatitude())
							+ " long: "
							+ Double.toString(locations.get(0).getLongitude());
					tv.setText(texto);
				} catch (IOException e) {
				}
			}
		});
		bt3.setEnabled(false);

		
		et.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus)
					et.setText("");
			}
		});

		et2.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus)
					et2.setText("");
			}
		});

		CheckBox cb = (CheckBox) findViewById(R.id.cb_locale);
		cb.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (((CheckBox) v).isChecked()) {
					et3.setEnabled(true);
					bt3.setEnabled(true);
				} else {
					et3.setEnabled(false);
					bt3.setEnabled(false);
				}
			}
		});

	}
}
