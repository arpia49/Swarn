package com.arpia49;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

public class Evento {
	private static Context contexto = null;
	private int id;
	private int fuerte;
	private static Handler handler=null;
    private final int NOTIFICATION_ID = 1010;

	public Evento(final int id, final Boolean fuerte, Activity val) {
		contexto = val.getApplicationContext();
		this.id = id;
		if(fuerte){
			this.fuerte = 110;
		}else{
			this.fuerte = 100;
		}
		if(handler==null){
			handler = new Handler() {
				@Override
				public void handleMessage(Message msg) {
					Notificacion nuevaNotificacion = new Notificacion.Builder(
							System.currentTimeMillis(),
							ListaAlarmas.element(msg.what).getNombre(),
							ListaAlarmas.element(msg.what).getUbicacion(),
							ListaAlarmas.element(msg.what).getId())
							.build(true);
					triggerNotification();
					Toast.makeText(contexto, "lalalalal",
							Toast.LENGTH_SHORT).show();
				}
			};
		}
	}

	public int getId() {
		return id;
	}

	public int getFuerte() {
		return fuerte;
	}

	public static Handler getHandler() {
		return handler;
	}
	
    private void triggerNotification()
    {
        CharSequence title = "Hello";
        CharSequence message = "Hello, Android!";
 
        NotificationManager notificationManager = (NotificationManager)contexto.getSystemService(contexto.NOTIFICATION_SERVICE);
        Notification notification = new Notification(R.drawable.icon, "A New Message!", System.currentTimeMillis());
 
        Intent notificationIntent = new Intent(contexto, VistaNotificaciones.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(contexto, 0, notificationIntent, 0);
 
        notification.setLatestEventInfo(contexto, title, message, pendingIntent);
        notificationManager.notify(NOTIFICATION_ID, notification);
    }
}
