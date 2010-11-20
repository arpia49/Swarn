package com.arpia49;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class ActConfiguracion extends PreferenceActivity {

	public static final int ACT_LISTA_DE_SONIDOS = 1;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.layout.config);
	}
}
