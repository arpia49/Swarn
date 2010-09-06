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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class VistaAlarmaEditar extends Activity {

	public Geocoder gc;
	LocationManager locationManager;
	String context = Context.LOCATION_SERVICE;
	String provider = null;
	Location location = null;
	float lat = 0;
	float lng = 0;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.add_alarma);

		locationManager = (LocationManager) getSystemService(context);
		gc = new Geocoder(this, Locale.getDefault());
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
		Alarma alarmaActual = ListaAlarmas.element(getIntent().getExtras().getInt("id"));
		final EditText et_nombreAlarma = (EditText) findViewById(R.id.et_nombreAlarma);
		et_nombreAlarma.setText(alarmaActual.getNombre());
		final EditText et_descAlarma = (EditText) findViewById(R.id.et_descAlarma);
		et_descAlarma.setText(alarmaActual.getDescripcion());
		final EditText et_lugar = (EditText) findViewById(R.id.et_lugar);
		et_lugar.setText(alarmaActual.getUbicacion());
		final CheckBox cb_posicion = (CheckBox) findViewById(R.id.cb_posicion);
		cb_posicion.setChecked(alarmaActual.conUbicacion());

		final RadioButton rb_fuerte = (RadioButton) findViewById(R.id.rb_fuerte);
		rb_fuerte.setChecked(!alarmaActual.getMuyFuerte());
		
		final RadioButton rb_muyFuerte = (RadioButton) findViewById(R.id.rb_muyFuerte);
		rb_muyFuerte.setChecked(alarmaActual.getMuyFuerte());
		
		Button bt = (Button) findViewById(R.id.botonAceptar);
		Button bt2 = (Button) findViewById(R.id.botonCancelar);
		
		bt.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent outData = new Intent();
				if (cb_posicion.isChecked()) {
					int metros = 100;
					outData.putExtra("ubicAlarma", et_lugar.getText().toString());
					outData.putExtra("latAlarma", lat);
					outData.putExtra("lngAlarma", lng);
				} else {
					outData.putExtra("ubicAlarma", "Sin ubicación");
					outData.putExtra("latAlarma", 0);
					outData.putExtra("lngAlarma", 0);
				}

				outData.putExtra("sonidoFuerte", !rb_fuerte.isChecked());
				final String nombre_alarma = et_nombreAlarma.getText()
						.toString();
				outData.putExtra("nombreAlarma", nombre_alarma);
				final String desc_alarma = et_descAlarma.getText().toString();
				outData.putExtra("descAlarma", desc_alarma);
				outData.putExtra("id", getIntent().getExtras().getInt("id"));
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

		et_nombreAlarma.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				String defecto = getString(R.string.et_nombre);
				String actual = et_nombreAlarma.getText().toString();

				if (hasFocus && actual.compareTo(defecto) == 0)
					et_nombreAlarma.setText("");

				else if (!hasFocus && actual.compareTo("") == 0)
					et_nombreAlarma.setText(defecto);
			}
		});

		et_descAlarma.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				String defecto = getString(R.string.et_desc);
				String actual = et_descAlarma.getText().toString();

				if (hasFocus && actual.compareTo(defecto) == 0)
					et_descAlarma.setText("");

				else if (!hasFocus && actual.compareTo("") == 0)
					et_descAlarma.setText(defecto);
			}
		});		
		
		et_lugar.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus)
					cb_posicion.setChecked(false);
			}
		});

		cb_posicion.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (((CheckBox) v).isChecked()) {
					if (et_lugar.getText().toString().compareTo("") == 0) {
						Toast.makeText(getApplicationContext(),
								"No hay una dirección especificada",
								Toast.LENGTH_SHORT).show();
						((CheckBox) v).setChecked(false);
					} else {
						try {
							String temp = et_lugar.getText().toString();
							List<Address> ubicacion = gc.getFromLocationName(
									temp, 1);
							if (ubicacion != null && ubicacion.size() > 0) {
								et_lugar.setText(ubicacion.get(0)
										.getAddressLine(0));
								lat = (float) ubicacion.get(0).getLatitude();
								lng = (float) ubicacion.get(0).getLongitude();
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
