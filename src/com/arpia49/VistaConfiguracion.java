package com.arpia49;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class VistaConfiguracion extends Activity {

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.config);
		final Spinner sp = (Spinner) findViewById(R.id.sp_radio);
		final TextView tv_sbFuerte = (TextView) findViewById(R.id.tv_fuerte);
		final TextView tv_sbMuyFuerte = (TextView) findViewById(R.id.tv_muyFuerte);
		final SeekBar sb_fuerte = (SeekBar) findViewById(R.id.sb_fuerte);
		final SeekBar sb_muyFuerte = (SeekBar) findViewById(R.id.sb_muyFuerte);
		final Spinner sp_radio = (Spinner) findViewById(R.id.sp_radio);

		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.radios, android.R.layout.simple_spinner_item);
		adapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp.setAdapter(adapter);

		tv_sbFuerte.setText(getString(R.string.tv_fuerte) + " "
				+ (Configuracion.getFuerte() + 70));
		tv_sbMuyFuerte.setText(getString(R.string.tv_muyFuerte) + " "
				+ (Configuracion.getMuyFuerte() + 85));

		sb_fuerte.setProgress(Configuracion.getFuerte());
		sb_muyFuerte.setProgress(Configuracion.getMuyFuerte());
		
		sb_fuerte
				.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
					@Override
					public void onProgressChanged(SeekBar seekBar,
							int progress, boolean fromUser) {
						// TODO Auto-generated method stub
						tv_sbFuerte.setText(getString(R.string.tv_fuerte) + " "
								+ String.valueOf(progress + 70));
						Configuracion.setFuerte(progress);
					}

					@Override
					public void onStartTrackingTouch(SeekBar seekBar) {
						// TODO Auto-generated method stub
					}

					@Override
					public void onStopTrackingTouch(SeekBar seekBar) {
						// TODO Auto-generated method stub
					}
				});

		sb_muyFuerte
				.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
					@Override
					public void onProgressChanged(SeekBar seekBar,
							int progress, boolean fromUser) {
						// TODO Auto-generated method stub
						tv_sbMuyFuerte.setText(getString(R.string.tv_muyFuerte)
								+ " " + String.valueOf(progress + 85));
						Configuracion.setMuyFuerte(progress);
					}

					@Override
					public void onStartTrackingTouch(SeekBar seekBar) {
						// TODO Auto-generated method stub
					}

					@Override
					public void onStopTrackingTouch(SeekBar seekBar) {
						// TODO Auto-generated method stub
					}
				});
		sp_radio.setSelection(Configuracion.getRadio());
		sp_radio.setOnItemSelectedListener(new MyOnItemSelectedListener());
	}
	
	public class MyOnItemSelectedListener implements OnItemSelectedListener {

	    public void onItemSelected(AdapterView<?> parent,
	        View view, int pos, long id) {
	      Configuracion.setRadio(pos);
	    }

	    public void onNothingSelected(AdapterView parent) {
	      // Do nothing.
	    }
	}
}
