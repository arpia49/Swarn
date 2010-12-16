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
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class ActAlarmaEditar extends Activity {

	public Geocoder gc;
	LocationManager locationManager;
	String context = Context.LOCATION_SERVICE;
	String provider = null;
	Location location = null;
	float lat = 0;
	float lng = 0;
	int sonido = 0;
	@SuppressWarnings("unchecked")
	ArrayAdapter adapter;
	Spinner sp_sonido;
	EditText et_lugar;
	CheckBox cb_posicion;
	Alarma alarmaActual;
	EditText et_nombreAlarma;
	EditText et_descAlarma;
	RadioGroup rg;
	RadioButton rb_fuerte;
	RadioButton rb_muyFuerte;
	Button bt;
	String defecto;
	String actual;

	@SuppressWarnings("unchecked")
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

		sp_sonido = (Spinner) findViewById(R.id.sp_sonido);
		adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item,
				ListaSonidos.arrayString());
		adapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp_sonido.setAdapter(adapter);
		
		alarmaActual = ListaAlarmas.element(getIntent().getExtras().getInt("id"));
		et_nombreAlarma = (EditText) findViewById(R.id.et_nombreAlarma);
		et_nombreAlarma.setText(alarmaActual.getNombre());
		et_descAlarma = (EditText) findViewById(R.id.et_descAlarma);
		et_descAlarma.setText(alarmaActual.getDescripcion());
		et_lugar = (EditText) findViewById(R.id.et_lugar);
		et_lugar.setText(alarmaActual.getUbicacion());
		cb_posicion = (CheckBox) findViewById(R.id.cb_posicion);
		cb_posicion.setChecked(alarmaActual.conUbicacion());
		rg = (RadioGroup) findViewById(R.id.RadioGroup01);
		rb_fuerte = (RadioButton) findViewById(R.id.rb_fuerte);
		rb_muyFuerte = (RadioButton) findViewById(R.id.rb_muyFuerte);
		rg.clearCheck();
		if(alarmaActual.getMuyFuerte()){
			rb_fuerte.setChecked(false);
			rb_muyFuerte.setChecked(true);
		}
		else{
			rb_fuerte.setChecked(true);
			rb_muyFuerte.setChecked(false);
		}
		sp_sonido.setSelection(ListaSonidos.obtenerIdDesdeClave(alarmaActual.getClaveSonido()));
		bt = (Button) findViewById(R.id.botonAceptar);
		bt.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent outData = new Intent();
				if (cb_posicion.isChecked()) {
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
				outData.putExtra("idSonido", sonido);
				setResult(Activity.RESULT_OK, outData);
				finish();
			}
		});

		et_nombreAlarma.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				defecto = getString(R.string.et_nombre);
				actual = et_nombreAlarma.getText().toString();

				if (hasFocus && actual.compareTo(defecto) == 0)
					et_nombreAlarma.setText("");

				else if (!hasFocus && actual.compareTo("") == 0)
					et_nombreAlarma.setText(defecto);
			}
		});

		et_descAlarma.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				defecto = getString(R.string.et_desc);
				actual = et_descAlarma.getText().toString();

				if (hasFocus && actual.compareTo(defecto) == 0)
					et_descAlarma.setText("");

				else if (!hasFocus && actual.compareTo("") == 0)
					et_descAlarma.setText(defecto);
			}
		});		
		
		et_lugar.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				cb_posicion.setChecked(false);
			}
		});

		et_lugar.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				defecto = getString(R.string.et_lugar);
				actual = et_lugar.getText().toString();

				if (hasFocus && actual.compareTo(defecto) == 0)
					et_lugar.setText("");

				else if (!hasFocus && actual.compareTo("") == 0)
					et_lugar.setText(defecto);
			}
		});

		cb_posicion.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (((CheckBox) v).isChecked()) {
					defecto = getString(R.string.et_lugar);
					actual = et_lugar.getText().toString();
					if (et_lugar.getText().toString().compareTo("") == 0) {
						Toast.makeText(getApplicationContext(),
								"No hay una dirección especificada",
								Toast.LENGTH_SHORT).show();
						((CheckBox) v).setChecked(false);
					} else if(actual.compareTo(defecto) == 0){
						try {
							et_lugar.setText(updateWithLocation(location));
						} catch (Exception e) {}
					}else {
						try {
							List<Address> ubicacion = gc.getFromLocationName(
									et_lugar.getText().toString(), 1);
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

		sp_sonido.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parentView,
					View selectedItemView, int position, long id) {
				sonido = position;
			}

			@Override
			public void onNothingSelected(AdapterView<?> parentView) {
			}

		});
	}
	
	private String updateWithLocation(Location location) {

		location = locationManager.getLastKnownLocation(provider);
		StringBuilder sb = new StringBuilder();
		if (location != null) {
			lat = (float) location.getLatitude();
			lng = (float) location.getLongitude();
			try {
				List<Address> ubicacion = gc.getFromLocation(lat, lng, 1);
				if (ubicacion != null && ubicacion.size() > 0) {
					Address address = ubicacion.get(0);
					for (int i = 0; i < address.getMaxAddressLineIndex(); i++)
						sb.append(address.getAddressLine(i));
				}
			} catch (IOException e) {

				cb_posicion.setChecked(false);
				sb.append("Sin ubicación");
				Toast.makeText(getApplicationContext(),
						"Ubicación no disponible", Toast.LENGTH_SHORT).show();
			}

		} else {
			lat = 0;
			lng = 0;
			cb_posicion.setChecked(false);
			sb.append("Sin ubicación");
			Toast.makeText(getApplicationContext(),
					"Ubicación no disponible", Toast.LENGTH_SHORT).show();
		}
		return (sb.toString());
	}
}