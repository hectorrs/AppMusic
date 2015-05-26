package es.uvigo.esei.dm1415.p08;

import java.util.ArrayList;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class MyServiceDriver3 extends Activity implements OnClickListener {
    TextView txtMsg;
    ComponentName service;
    Intent intentMyService3;
    BroadcastReceiver receiver;
    
    ArrayList<String> historial;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicio1);
        
        txtMsg = (TextView) findViewById(R.id.txtMsg);
        
        Intent i = getIntent(); // gets the previously created intent
		historial = i.getStringArrayListExtra("historial");
		
        intentMyService3 = new Intent(this, Servicio.class);
        intentMyService3.putStringArrayListExtra("historial", historial);
        service = startService(intentMyService3);   
                
        txtMsg.setText("Servicio iniciado - mira el LogCat");
        findViewById(R.id.botonParar).setOnClickListener(this);
        
		// register & define filter for local listener
        IntentFilter mainFilter = new IntentFilter("matos.action.GOSERVICE3");
		receiver = new MyMainLocalReceiver();
		registerReceiver(receiver, mainFilter);
    }//onCreate
    
    // ////////////////////////////////////////////////////////////////////////
	public void onClick(View v) {
		// assume:  v.getId() == R.id.btnStopService
		try {
			stopService(intentMyService3);
			txtMsg.setText("Servicio parado");
		} catch (Exception e) {
			e.printStackTrace();
		}				
	}     

    // ////////////////////////////////////////////////////////////////////////
    @Override
	protected void onDestroy() {
		super.onDestroy();
		try {
			stopService(intentMyService3);
			unregisterReceiver(receiver);
		} catch (Exception e) {
			Log.e ("DESTROY>>>", e.getMessage() );
		}
		Log.e ("DESTROY>>>" , "El servicio ha sido destruido" );
	}

	//////////////////////////////////////////////////////////////////////
	// local RECEIVER
	public class MyMainLocalReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context localContext, Intent callerIntent) {
			String serviceData = callerIntent.getStringExtra("service3Data");
					
			Log.e ("MAIN>>>", "Datos recibidos del servicio: " + serviceData);
			
			String now = "\nDatos recibidos del servicio: > " + serviceData;
			txtMsg.append(now);
		}		
	}//MyMainLocalReceiver
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    @Override
	public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }else{
        	if(id == R.id.action_settings2){
        		Intent show = new Intent(this, IntroducirCancion.class);
        		show.putStringArrayListExtra("historial", historial);
        	    startActivity(show);
        	    return true;
        	}else{
        		if(id == R.id.action_settings3){
        			Intent show = new Intent(this, IntroducirAlbum.class);
        			show.putStringArrayListExtra("historial", historial);
            	    startActivity(show);
            	    return true;
        		}else{
        			if(id == R.id.action_settings4){
        				Intent show = new Intent(this, IntroducirArtista.class);
        				show.putStringArrayListExtra("historial", historial);
                	    startActivity(show);
                	    return true;
        			}else{
        				if(id == R.id.action_settings5){
        					Intent show = new Intent(this, WebView1.class);
        					show.putStringArrayListExtra("historial", historial);
        					startActivity(show);
        					return true;
        				}else{
        					if(id == R.id.action_settings6){
        						Intent show = new Intent(this, MainActivity.class);
        						show.putStringArrayListExtra("historial", historial);
        						startActivity(show);
        						return true;
        					}else{
        						if (id == R.id.txtMsg) {
									Intent show = new Intent(this,
											MyServiceDriver3.class);
									show.putStringArrayListExtra("historial", historial);
									startActivity(show);
									return true;
								}
        					}
        				}
        			}
        		}
        	}
        }
        return super.onOptionsItemSelected(item);
    }
}//MyServiceDriver3