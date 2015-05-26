package es.uvigo.esei.dm1415.p08;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class ConsultarCancion extends Activity {
	SQLiteDatabase db;

	String [] canciones;
	String [] artistas;
	String [] albumes;
	String [] generos;	
	
	ArrayList<String> historial;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_consultar_cancion);
		
		db = this.openOrCreateDatabase("musicaBD", MODE_PRIVATE, null);
		
		Intent i = getIntent(); // gets the previously created intent
		int idItem = i.getIntExtra("cancion", 0);
		historial = i.getStringArrayListExtra("historial");
		String id = Integer.toString(idItem);
		String [] columnasCancion = new String[4];
		String [] args = {id};
    	Cursor c1 = db.rawQuery("select c.nameC, a.nameAl, ar.nameAr, a.genero from cancion c, album a, artista ar where c.idC = ? and ar.idAr = a.idAr and a.idAl = c.idAl", args);
    	int nameColC = c1.getColumnIndex("nameC");
    	int nameColAl = c1.getColumnIndex("nameAl");
    	int nameColAr = c1.getColumnIndex("nameAr");
    	int generoColAl = c1.getColumnIndex("genero");
    	
    	while(c1.moveToNext()){
    		columnasCancion[0] = c1.getString(nameColC);
    		columnasCancion[1] = c1.getString(nameColAl);
    		columnasCancion[2] = c1.getString(nameColAr);
    		columnasCancion[3] = c1.getString(generoColAl);
    	}
    	
    	c1.moveToFirst();
		historial.add(c1.getString(nameColC));
    	
    	setContentView(R.layout.activity_consultar_cancion);
    	TextView txtCancion = (TextView)findViewById(R.id.textView3);
    	txtCancion.setText(columnasCancion[0]);
    	
    	//setContentView(R.layout.activity_consultar_cancion);
    	TextView txtAlbum = (TextView)findViewById(R.id.TextView02);
    	txtAlbum.setText(columnasCancion[1]);
    	
    	//setContentView(R.layout.activity_consultar_cancion);
    	TextView txtArtista = (TextView)findViewById(R.id.TextView03);
    	txtArtista.setText(columnasCancion[2]);
    	
    	//setContentView(R.layout.activity_consultar_cancion);
    	TextView txtGenero = (TextView)findViewById(R.id.TextView04);
    	txtGenero.setText(columnasCancion[3]);
	}

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
}
