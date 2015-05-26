package es.uvigo.esei.dm1415.p08;

import java.util.ArrayList;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class Servicio extends Service {
	boolean isRunning = true;

	ArrayList<String> historial;

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
	}

	@Override
	public void onStart(Intent intent, int startId) {
		Log.e("SERVICIO INICIADO", "El servicio ha sido iniciado");
		
		historial = intent.getStringArrayListExtra("historial");
		
		Thread serviceThread = new Thread(new Runnable() {

			public void run() {
				int size;
				if (historial == null) {
					size = 1;
				} else {
					size = historial.size();
				}
				
				Log.e("size: ", "" + size);
				
				for (int i = 0; (i < size) & isRunning; i++) {
					try {
						//Thread.sleep(1000);
						String msg = historial.get(i);
						Intent intentDataForMyClient = new Intent("matos.action.GOSERVICE3");
						intentDataForMyClient.putExtra("service3Data", msg);
						sendBroadcast(intentDataForMyClient);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}// for
			}// run
		});
		serviceThread.start();
	}// onStart

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.e("<<MyService3-onDestroy>>", "I am Dead-3");
		isRunning = false;
	}// onDestroy
}
