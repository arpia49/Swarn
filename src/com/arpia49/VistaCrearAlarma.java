package com.arpia49;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

public class VistaCrearAlarma extends Activity {

	public Geocoder gc;
	LocationManager locationManager;
	String context = Context.LOCATION_SERVICE;
	String provider = null;
	Location location = null;
	public List<Address> addresses;
	float lat = 0;
	float lng = 0;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.add_alarma);

		locationManager = (LocationManager) getSystemService(context);

		// criterio para la actualización de posiciones
		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_FINE);
		criteria.setAltitudeRequired(false);
		criteria.setBearingRequired(false);
		criteria.setCostAllowed(true);
		criteria.setPowerRequirement(Criteria.POWER_LOW);
		provider = locationManager.getBestProvider(criteria, true);
		locationManager.requestLocationUpdates(provider, 30000, 100,
				locationListener);
		final EditText et = (EditText) findViewById(R.id.et_nombreAlarma);
		final EditText et2 = (EditText) findViewById(R.id.et_descAlarma);
		final EditText et3 = (EditText) findViewById(R.id.et_lugar);
		final CheckBox cb = (CheckBox) findViewById(R.id.cb_posicion);

		final RadioButton rb = (RadioButton) findViewById(R.id.rb_fuerte);

		Button bt = (Button) findViewById(R.id.botonAceptar);
		Button bt2 = (Button) findViewById(R.id.botonCancelar);
		final Spinner sp = (Spinner) findViewById(R.id.sp_radio);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.radios, android.R.layout.simple_spinner_item);
		adapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		sp.setAdapter(adapter);
		updateWithLocation(location);
		bt.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent outData = new Intent();
				if (cb.isChecked()) {
					Address address = addresses.get(0);
					int metros = 100;
					if (sp.getSelectedItemPosition() == 1) {
						metros = 500;
					}
					outData.putExtra("ubicAlarma", address.getAddressLine(0));
					outData.putExtra("radioAlarma", metros);
					outData.putExtra("latAlarma", lat);
					outData.putExtra("lngAlarma", lng);
				} else {
					outData.putExtra("ubicAlarma", "Sin ubicación");
					outData.putExtra("radioAlarma", 0);
					outData.putExtra("latAlarma", 0);
					outData.putExtra("lngAlarma", 0);
				}

				outData.putExtra("sonidoFuerte", !rb.isChecked());
				final String nombre_alarma = et.getText().toString();
				outData.putExtra("nombreAlarma", nombre_alarma);
				final String desc_alarma = et2.getText().toString();
				outData.putExtra("descAlarma", desc_alarma);
				setResult(Activity.RESULT_OK, outData);
				finish();
			}
		});

		bt2.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				setResult(Activity.RESULT_CANCELED, null);
				finish();
			}
		});

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

		cb.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (((CheckBox) v).isChecked()) {
					if (et3.getText().toString().compareTo("") == 0) {
						Toast.makeText(getApplicationContext(),
								"No hay una dirección especificada",
								Toast.LENGTH_SHORT).show();
						((CheckBox) v).setChecked(false);
					} else {
						try {
							addresses = gc.getFromLocationName(et3.getText()
									.toString(), 1);
							if (addresses != null && addresses.size() > 0) {
								et3.setText(addresses.get(0).getAddressLine(0));
								lat = (float) addresses.get(0).getLatitude();
								lng = (float) addresses.get(0).getLongitude();
							} else {
								Toast.makeText(getApplicationContext(),
										"No se ha encontrado la dirección",
										Toast.LENGTH_SHORT).show();
								((CheckBox) v).setChecked(false);
							}
						} catch (IOException e) {
							Toast.makeText(getApplicationContext(),
									"No se ha encontrado la dirección",
									Toast.LENGTH_SHORT).show();
							((CheckBox) v).setChecked(false);
						}
					}
				}
			}
		});

	}

	private void updateWithLocation(Location location) {

		location = locationManager.getLastKnownLocation(provider);
		EditText tv_lugar;
		tv_lugar = (EditText) findViewById(R.id.et_lugar);
		StringBuilder sb = new StringBuilder();
		if (location != null) {
			lat = (float) location.getLatitude();
			lng = (float) location.getLongitude();
			gc = new Geocoder(this, Locale.getDefault());
			addresses = null;
			try {
				addresses = gc.getFromLocation(lat, lng, 1);
				if (addresses.size() > 0) {
					Address address = addresses.get(0);
					for (int i = 0; i < address.getMaxAddressLineIndex(); i++)
						sb.append(address.getAddressLine(i));
				}
			} catch (IOException e) {
				final CheckBox cb = (CheckBox) findViewById(R.id.cb_posicion);

				cb.setChecked(false);
				Toast.makeText(getApplicationContext(),
						"Ubicación no disponible", Toast.LENGTH_SHORT).show();
			}

		} else {
			lat = 0;
			lng = 0;
		}
		tv_lugar.setText(sb.toString());

	}

	private final LocationListener locationListener = new LocationListener() {
		public void onLocationChanged(Location location) {
		}

		public void onProviderDisabled(String provider) {
		}

		public void onProviderEnabled(String provider) {
		}

		public void onStatusChanged(String provider, int status, Bundle extras) {
		}
	};

}
