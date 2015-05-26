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

public class ConsultarArtista extends Activity {
	SQLiteDatabase db;

	String [] canciones;
	String [] artistas;
	String [] albumes;
	String [] generos;
	
	ArrayList<String> historial;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_consultar_artista);
		
db = this.openOrCreateDatabase("musicaBD", MODE_PRIVATE, null);
		
		Intent i = getIntent(); // gets the previously created intent
		int idItem = i.getIntExtra("artista", 0);
		historial = i.getStringArrayListExtra("historial");
		String id = Integer.toString(idItem);
		String [] args = {id};
    	Cursor c1 = db.rawQuery("select a.nameAl, ar.nameAr from album a, artista ar where ar.idAr = ? and ar.idAr = a.idAr", args);
    	int nameColAl = c1.getColumnIndex("nameAl");
    	int nameColAr = c1.getColumnIndex("nameAr");
    	int numAlbumes = c1.getCount();
    	String nombreArtista = null;

    	if(numAlbumes > 0){
	    	String [] listaAlbumes = new String[numAlbumes];
	    	int j = 0;
	    	while(c1.moveToNext()){
	    		nombreArtista = c1.getString(nameColAr);
	    		listaAlbumes[j] = c1.getString(nameColAl);
	    		j++;
	    	}
	    	
	    	c1.moveToFirst();
			historial.add(c1.getString(nameColAr));
	    	
	    	String lista = "";
	    	setContentView(R.layout.activity_consultar_artista);
	    	TextView txtAlbum = (TextView)findViewById(R.id.TextView02);
	    	for(int k = 0; k < numAlbumes; k++){
		    	lista += listaAlbumes[k] + "\n";
	    	}
	    	txtAlbum.setText(lista);
    	}else{
    		Cursor c2 = db.rawQuery("select nameAr from artista where idAr = ?", args);
           	nameColAr = c2.getColumnIndex("nameAr");
	    	c2.moveToNext();
	    	nombreArtista = c2.getString(nameColAr);
	    	
	    	historial.add(c2.getString(nameColAr));
	    	
	    	setContentView(R.layout.activity_consultar_artista);
	    	String lista = "No tiene álbumes asignados";
	    	TextView txtAlbum = (TextView)findViewById(R.id.TextView02);
	    	txtAlbum.setText(lista);
    	}
    	
    	
    	TextView txtCancion = (TextView)findViewById(R.id.textView3);
    	txtCancion.setText(nombreArtista);
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
