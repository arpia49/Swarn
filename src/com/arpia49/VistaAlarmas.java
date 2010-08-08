package com.arpia49;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.arpia49.R;

public class VistaAlarmas extends Activity {

	// De los menús
	public static final int ACT_ADD_ALARMA = 1;
	public static final int ACT_DEL_ALARMA = 2;
	public static final int ACT_LISTA_NOTIFICACIONES = 3;
	static final private int ADD_ALARMA = Menu.FIRST;
	static final private int DEL_ALARMA = Menu.FIRST + 1;
	static final private int LISTA_ALERTAS = Menu.FIRST + 2;
	static final private int INFO = Menu.FIRST + 3;
	private static splEngine engine = null;
	private static Handler messageHandler = null;
	
	// Alerta de proximidad
	private static String PROXIMITY_ALERT = "com.arpia49.action.proximityalert";

	public void onCreate(Bundle savedInstanceState) {
		Registro.iniciar(this);
		messageHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				Toast
						.makeText(getApplicationContext(),
								"pin"+msg.what,
								Toast.LENGTH_SHORT).show();
			}
		};
		engine = splEngine.getInstance(messageHandler);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		cargarPosiciones((LinearLayout) findViewById(R.id.mainLay));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);

		// Create and add new menu items.
		MenuItem itemAdd = menu.add(0, ADD_ALARMA, Menu.NONE,
				R.string.menu_add_alarma);
		MenuItem itemDel = menu.add(0, DEL_ALARMA, Menu.NONE,
				R.string.menu_del_alarma);
		MenuItem itemLista = menu.add(0, LISTA_ALERTAS, Menu.NONE,
				R.string.menu_lista_alertas);
		MenuItem itemInfo = menu.add(0, INFO, Menu.NONE, R.string.menu_info);

		// Assign icons
		itemAdd.setIcon(R.drawable.add);
		itemDel.setIcon(R.drawable.del);
		itemLista.setIcon(R.drawable.list);
		itemInfo.setIcon(R.drawable.help);

		return true;
	}

	public boolean onPrepareOptionsMenu(Menu menu) {
		MenuItem itemDel = menu.getItem(1);
		MenuItem itemLista = menu.getItem(2);
		itemDel.setEnabled(ListaAlarmas.size()>0);
		itemLista.setEnabled(ListaNotificaciones.size()>0);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);
		switch (item.getItemId()) {
		case (ADD_ALARMA): {
			Intent intent = new Intent(this, AddAlarma.class);
			startActivityForResult(intent, ACT_ADD_ALARMA);
			return true;
		}
		case (DEL_ALARMA): {
			Intent intent = new Intent(this, DelAlarmas.class);
			startActivityForResult(intent, ACT_DEL_ALARMA);
			return true;
		}
		case (LISTA_ALERTAS): {
			Intent intent = new Intent(this, VistaNotificaciones.class);
			startActivityForResult(intent, ACT_LISTA_NOTIFICACIONES);
			return true;
		}
		case (INFO): {
			// Create an VIEW intent
			Intent myIntent = new Intent("android.intent.action.VIEW", Uri
					.parse("http://arpia49.com/timbre/help"));
			// Start the activity
			startActivity(myIntent);
			return true;
		}
		}
		return false;
	}

	@Override
	public void onActivityResult(int reqCode, int resCode, Intent data) {
		super.onActivityResult(reqCode, resCode, data);
		switch (reqCode) {
		case (ACT_ADD_ALARMA): {
			if (resCode == Activity.RESULT_OK) {

				Alarma nuevaAlarma = new Alarma.Builder(
						data.getStringExtra("nombreAlarma"),
						data.getStringExtra("descAlarma"),
						data.getStringExtra("ubicAlarma"),
						data.getIntExtra("radioAlarma",100),
						data.getFloatExtra("latAlarma", 0),
						data.getFloatExtra("lngAlarma", 0),
						data.getBooleanExtra("sonidoFuerte", false))
						.build(true);
				addAlarma(nuevaAlarma);
				
				Toast.makeText(getApplicationContext(), "Alarma añadida!",
						Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(getApplicationContext(),
						"La alarma no se ha creado", Toast.LENGTH_SHORT).show();
			}
		}
			break;
		case (ACT_DEL_ALARMA): {
			if (resCode == Activity.RESULT_OK) {
				if (data.getBooleanExtra("todas", false)) {
					int numAlarmas = ListaAlarmas.size()-1;
					for (int i = numAlarmas; i >= 0; i--) {
						delAlarma(i);
					}
					Toast.makeText(getApplicationContext(),
							"¡Eliminadas todas las alarmas!",
							Toast.LENGTH_SHORT).show();
				} else {
					delAlarma(data.getIntExtra("idAlarma",0)+1);
					Toast.makeText(getApplicationContext(),
							"Alarma  eliminada!", Toast.LENGTH_SHORT).show();
				}
				this.onCreate(null);
			} else {
				Toast.makeText(getApplicationContext(),
						"No se han borrado alarmas", Toast.LENGTH_SHORT).show();
			}
		}
		break;
		case (ACT_LISTA_NOTIFICACIONES): {
			if (resCode == Activity.RESULT_OK) {
				if (data.getBooleanExtra("todas", false)) {
					ListaNotificaciones.borrar();
					Toast.makeText(getApplicationContext(),
							"Notificaciones eliminadas!", Toast.LENGTH_SHORT).show();
				}
				this.onCreate(null);
			} else {
				Toast.makeText(getApplicationContext(),
						"No se han borrado alarmas", Toast.LENGTH_SHORT).show();
			}
		}
			break;
		}
	}

	private void cargarPosiciones(LinearLayout lx) {

		// Leemos el número de alarmas
		int numAlarmas = ListaAlarmas.size();
		for (int i = 0; i < numAlarmas; i++) {
			Alarma alarmaActual = ListaAlarmas.elementAt(i);
			addAlarma(alarmaActual);
		}
	}

	private void addAlarma(Alarma val) {

		//Obtenemos los datos que se usarán más de una vez
		final int id = val.getId();
		final String descripcion = val.getDescripcion();
		final boolean marcada = val.getMarcada();
		final int radio = val.getRadio();
		
		LinearLayout lx = (LinearLayout) findViewById(R.id.mainLay);
		LinearLayout la = new LinearLayout(this);
		la.setId(id);
		la.setOrientation(1);

		// Creamos la alarma en la vista
		CheckBox cb = new CheckBox(this);
		TextView tbdesc = new TextView(this);
		cb.setId(id);
		tbdesc.setId(id);
		tbdesc.setSingleLine(false);
		cb.setText(val.getNombre());
		if (marcada) {
			setProximityAlert(val);
		}
		cb.setChecked(marcada);
		if (val.conUbicacion()) {
			tbdesc.setText(descripcion + " - " + val.getUbicacion() + " (" + radio + "m)");
		} else {
			tbdesc.setText(descripcion + " - " + val.getUbicacion());
		}

		cb.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				int v_id = v.getId();
				Alarma alarmaActual = ListaAlarmas.obtenerAlarma(v_id);
				if (((CheckBox) v).isChecked()) {
					alarmaActual.setMarcada(true);
					if (!alarmaActual.conUbicacion()) {
						engine.start_engine(alarmaActual.getMuyFuerte());
					} else {
						setProximityAlert(alarmaActual);
					}
				} else {
					alarmaActual.setMarcada(false);
					if (alarmaActual.conUbicacion()) {
						removeProximityAlert(alarmaActual);
					}else{
						engine.stop_engine();
					}
				}
			}
		});
		la.addView(cb);
		la.addView(tbdesc);
		lx.addView(la);
	}

	private void delAlarma(int val) {
		Alarma alarmaABorrar = ListaAlarmas.elementAt(val-1);
		ListaAlarmas.del(val);
		
		if(alarmaABorrar.getRegistrada()){
			removeProximityAlert(alarmaABorrar);
		}
	}

	private void setProximityAlert(Alarma val) {
		int id = val.getId();
		
		String locService = Context.LOCATION_SERVICE;
		LocationManager locationManager;
		locationManager = (LocationManager) getSystemService(locService);

		long expiration = -1; // do not expire
		Intent intent = new Intent(PROXIMITY_ALERT);
		intent.putExtra("id", id);
		intent.setData((Uri.parse(id + "://" + id)));

		PendingIntent proximityIntent = PendingIntent.getBroadcast(
				getApplicationContext(), id, intent, 0);
		locationManager.addProximityAlert(val.getLatitud(), val.getLongitud(), val.getRadio(), expiration,
				proximityIntent);

		if (!val.getRegistrada()) {
			IntentFilter filter = new IntentFilter(PROXIMITY_ALERT);
			filter.addDataScheme(Integer.toString(id));
			registerReceiver(new AlertaEntrante(), filter);
			val.setRegistrada(true);
		}
		
		Toast.makeText(getApplicationContext(), "Añadida alerta" + id,
				Toast.LENGTH_SHORT).show();
	}

	private void removeProximityAlert(Alarma val) {
		int id = val.getId();
		
		String locService = Context.LOCATION_SERVICE;
		LocationManager locationManager;
		locationManager = (LocationManager) getSystemService(locService);

		Intent intent = new Intent(PROXIMITY_ALERT);
		intent.putExtra("id", id);
		intent.setData((Uri.parse(id + "://" + id)));

		PendingIntent proximityIntent = PendingIntent.getBroadcast(
				getApplicationContext(), id, intent, 0);

		locationManager.removeProximityAlert(proximityIntent);

		Toast.makeText(getApplicationContext(), "Borrada alerta" + id,
				Toast.LENGTH_SHORT).show();
		if(val.getActivada()){
			engine.stop_engine();
			val.setActivada(false);
		}
		val.setRegistrada(false);
	}
}