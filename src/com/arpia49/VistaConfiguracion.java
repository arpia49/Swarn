package com.arpia49;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class VistaConfiguracion extends PreferenceActivity {

	public static final int ACT_LISTA_DE_SONIDOS = 1;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.layout.config);
//		final Spinner sp = (Spinner) findViewById(R.id.sp_radio);
//		final TextView tv_sbFuerte = (TextView) findViewById(R.id.tv_fuerte);
//		final TextView tv_sbMuyFuerte = (TextView) findViewById(R.id.tv_muyFuerte);
//		final SeekBar sb_fuerte = (SeekBar) findViewById(R.id.sb_fuerte);
//		final SeekBar sb_muyFuerte = (SeekBar) findViewById(R.id.sb_muyFuerte);
//		final Spinner sp_radio = (Spinner) findViewById(R.id.sp_radio);
//		final Button bt_restaurar = (Button) findViewById(R.id.bt_restaurar);
//		final Button bt_sonidos = (Button) findViewById(R.id.bt_sonidos);
//
//		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
//				this, R.array.radios, android.R.layout.simple_spinner_item);
//		adapter
//				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//		sp.setAdapter(adapter);
//
//		tv_sbFuerte.setText(getString(R.string.tv_fuerte) + " "
//				+ (Configuracion.getFuerte() + 70));
//		tv_sbMuyFuerte.setText(getString(R.string.tv_muyFuerte) + " "
//				+ (Configuracion.getMuyFuerte() + 85));
//
//		sb_fuerte.setProgress(Configuracion.getFuerte());
//		sb_muyFuerte.setProgress(Configuracion.getMuyFuerte());
//
//		sb_fuerte
//				.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//					@Override
//					public void onProgressChanged(SeekBar seekBar,
//							int progress, boolean fromUser) {
//						// TODO Auto-generated method stub
//						tv_sbFuerte.setText(getString(R.string.tv_fuerte) + " "
//								+ String.valueOf(progress + 70));
//						Configuracion.setFuerte(progress);
//					}
//
//					@Override
//					public void onStartTrackingTouch(SeekBar seekBar) {
//						// TODO Auto-generated method stub
//					}
//
//					@Override
//					public void onStopTrackingTouch(SeekBar seekBar) {
//						// TODO Auto-generated method stub
//					}
//				});
//
//		sb_muyFuerte
//				.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//					@Override
//					public void onProgressChanged(SeekBar seekBar,
//							int progress, boolean fromUser) {
//						// TODO Auto-generated method stub
//						tv_sbMuyFuerte.setText(getString(R.string.tv_muyFuerte)
//								+ " " + String.valueOf(progress + 85));
//						Configuracion.setMuyFuerte(progress);
//					}
//
//					@Override
//					public void onStartTrackingTouch(SeekBar seekBar) {
//						// TODO Auto-generated method stub
//					}
//
//					@Override
//					public void onStopTrackingTouch(SeekBar seekBar) {
//						// TODO Auto-generated method stub
//					}
//				});
//		sp_radio.setSelection(Configuracion.getRadio());
//		sp_radio.setOnItemSelectedListener(new MyOnItemSelectedListener());
//
//		bt_restaurar.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				sp_radio.setSelection(2);
//				sb_fuerte.setProgress(10);
//				sb_muyFuerte.setProgress(10);
//			}
//		});
//
//		bt_sonidos.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				Intent intent = new Intent(getApplicationContext(), VistaListarSonidos.class);
//				startActivityForResult(intent, ACT_LISTA_DE_SONIDOS);
//			}
//		});
	}

//	public class MyOnItemSelectedListener implements OnItemSelectedListener {
//
//		public void onItemSelected(AdapterView<?> parent, View view, int pos,
//				long id) {
//			Configuracion.setRadio(pos);
//		}
//
//		public void onNothingSelected(AdapterView parent) {
//			// Do nothing.
//		}
//	}
}
