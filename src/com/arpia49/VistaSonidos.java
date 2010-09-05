package com.arpia49;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class VistaSonidos extends Activity {

	// De los menús
	public static final int ACT_ADD_SONIDO = 1;
	public static final int ACT_LISTA_DEL_SONIDO = 2;
	static final private int ADD_SONIDO = Menu.FIRST;
	static final private int DEL_SONIDO = Menu.FIRST + 2;
	private static splEngine engine = null;
	public static Activity actividad = null;
	SharedPreferences sp = null;
	
	public void onCreate(Bundle savedInstanceState) {
		actividad = this;
		sp = PreferenceManager.getDefaultSharedPreferences(this);
		engine = splEngine.getInstance();
		splEngine.setPreferences(sp);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.vista_sonidos);
//OGT		cargarPosiciones((LinearLayout) findViewById(R.id.mainLay));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);

		// Create and add new menu items.
		MenuItem itemAdd = menu.add(0, ADD_SONIDO, Menu.NONE,
				R.string.menu_add_sonido);
		MenuItem itemDel = menu.add(0, DEL_SONIDO, Menu.NONE,
				R.string.menu_del_sonido);

		// Assign icons
		itemAdd.setIcon(R.drawable.add);
		itemDel.setIcon(R.drawable.del);

		return true;
	}

	public boolean onPrepareOptionsMenu(Menu menu) {
		MenuItem itemDel = menu.getItem(1);
		itemDel.setEnabled(ListaAlarmas.size()>0); //OGT
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);
		switch (item.getItemId()) {
		case (ADD_SONIDO): {
			Intent intent = new Intent(this, VistaAlarmaCrear.class); //OGT
			startActivityForResult(intent, ACT_ADD_SONIDO);
			return true;
		}
		case (DEL_SONIDO): {
			Intent intent = new Intent(this, VistaListarAlarmasBorrar.class); //OGT
			startActivityForResult(intent, ACT_LISTA_DEL_SONIDO	);
			return true;
		}
		}
		return false;
	}

	@Override
	public void onActivityResult(int reqCode, int resCode, final Intent data) {
		super.onActivityResult(reqCode, resCode, data);
		switch (reqCode) {
		case (ACT_ADD_SONIDO): {
			if (resCode == Activity.RESULT_OK) {
//	OGT			Alarma nuevaAlarma = new Alarma.Builder(
//						data.getStringExtra("nombreAlarma"),
//						data.getStringExtra("descAlarma"),
//						data.getStringExtra("ubicAlarma"),
//						data.getFloatExtra("latAlarma", 0),
//						data.getFloatExtra("lngAlarma", 0),
//						data.getBooleanExtra("sonidoFuerte", false))
//						.build(true);
//				addAlarma(nuevaAlarma);
//				
//				Toast.makeText(getApplicationContext(), "¡Alarma añadida!",
//						Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(getApplicationContext(),
						"El sonido no se ha creado", Toast.LENGTH_SHORT).show();
			}
		}
		break;
		case (ACT_LISTA_DEL_SONIDO): {
			if (resCode == Activity.RESULT_OK) {
//	OGT			if (data.getBooleanExtra("todas", false)) {
//					int numAlarmas = ListaAlarmas.size();
//					for (int i = numAlarmas; i > 0; i--) {
//						delAlarma(i);
//					}
//					Toast.makeText(getApplicationContext(),
//							"¡Eliminadas todas las alarmas!",
//							Toast.LENGTH_SHORT).show();
//				} else {
//					delAlarma(data.getIntExtra("idAlarma",0)+1);
//					Toast.makeText(getApplicationContext(),
//							"¡Alarma  eliminada!", Toast.LENGTH_SHORT).show();
//				}
//				setContentView(R.layout.main);
//				cargarPosiciones((LinearLayout) findViewById(R.id.mainLay));
			} else {
				Toast.makeText(getApplicationContext(),
						"No se han borrado sonidos", Toast.LENGTH_SHORT).show();
			}
		}
		break;
		}
	}

//	OGT private void cargarPosiciones(LinearLayout lx) {
//
//		// Leemos el número de alarmas
//		int numAlarmas = ListaAlarmas.size();
//		for (int i = 1; i <= numAlarmas; i++) {
//			Alarma alarmaActual = ListaAlarmas.element(i);
//			addAlarma(alarmaActual);
//			if(alarmaActual.getMarcada()){
//				if (!alarmaActual.conUbicacion()) {
//					engine.start_engine(new Evento(alarmaActual.getId(),alarmaActual.getMuyFuerte(), actividad));
//				} else {
//					setProximityAlert(alarmaActual);
//				}
//			}
//		}
//	}
//	
//	private void addAlarma(Alarma val) {
//
//		//Obtenemos los datos que se usarán más de una vez
//
//		LinearLayout lx = (LinearLayout) findViewById(R.id.mainLay);
//		LinearLayout la = new LinearLayout(this);
//		la.setId(val.getId());
//		la.setOrientation(1);
//
//		// Creamos la alarma en la vista
//		CheckBox cb = new CheckBox(this);
//		cb.setId(val.getId());
//		cb.setSingleLine(false);
//		cb.setText(val.toString());
//		if (val.getMarcada()) {
//			setProximityAlert(val);
//		}
//		cb.setChecked(val.getMarcada());
//
//		cb.setOnClickListener(new OnClickListener() {
//			public void onClick(View v) {
//				int v_id = v.getId();
//				Alarma alarmaActual = ListaAlarmas.element(v_id);
//				if (((CheckBox) v).isChecked()) {
//					alarmaActual.setMarcada(true);
//					if (!alarmaActual.conUbicacion()) {
//						engine.start_engine(new Evento(v_id,alarmaActual.getMuyFuerte(), actividad));
//					} else {
//						setProximityAlert(alarmaActual);
//					}
//				} else {
//					alarmaActual.setMarcada(false);
//					if (alarmaActual.conUbicacion()) {
//						removeProximityAlert(alarmaActual);
//					}else{
//						engine.stop_engine();
//					}
//				}
//			}
//		});
//		la.addView(cb);
//		lx.addView(la);
//	}
	
//	OGT private void delAlarma(int id) {
//		Alarma alarmaABorrar = ListaAlarmas.element(id);
//		ListaAlarmas.del(id);
//		
//		if(alarmaABorrar.getRegistrada()){
//			removeProximityAlert(alarmaABorrar);
//		}
//	}
	
}