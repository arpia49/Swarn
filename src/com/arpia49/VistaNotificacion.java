package com.arpia49;

import java.util.List;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class VistaNotificacion extends MapActivity {

	List<Overlay> mapOverlays;
	Drawable drawable;
	HelloItemizedOverlay itemizedOverlay;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Intent kk = this.getIntent();
		
		int lat = (int) (kk.getFloatExtra("lat", 0)*1000000);
		int lng = (int) (kk.getFloatExtra("lng", 0)*1000000);
		
		setContentView(R.layout.vistanotificaciones);
		MapView mapView = (MapView)findViewById(R.id.myMapView);
		MapController mapController = mapView.getController();
		
		mapOverlays = mapView.getOverlays();
		drawable = this.getResources().getDrawable(R.drawable.androidmarker);
		itemizedOverlay = new HelloItemizedOverlay(drawable);
		
		mapView.setSatellite(true);
		
		GeoPoint point = new GeoPoint(lat,lng);
		OverlayItem overlayitem = new OverlayItem(point, "aaaa", "bbb");

		itemizedOverlay.addOverlay(overlayitem);
		mapOverlays.add(itemizedOverlay);
		
		mapController.setCenter(point);
		mapController.setZoom(15);
		mapController.animateTo(point);
		mapView.setBuiltInZoomControls(true);
	}
	
	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}
}