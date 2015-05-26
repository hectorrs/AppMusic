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

public class ConsultarAlbum extends Activity {
	SQLiteDatabase db;
	
	String [] canciones;
	String [] artistas;
	String [] albumes;
	String [] generos;
	
	ArrayList<String> historial;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_consultar_album);
		
		db = this.openOrCreateDatabase("musicaBD", MODE_PRIVATE, null);
		
		Intent i = getIntent(); // gets the previously created intent
		int idItem = i.getIntExtra("album", 0);
		historial = i.getStringArrayListExtra("historial");
		String id = Integer.toString(idItem);
		String [] columnasAlbum = new String[3];
		String [] args = {id};
    	Cursor c1 = db.rawQuery("select c.nameC, a.nameAl, ar.nameAr, a.genero from cancion c, album a, artista ar where a.idAl = ? and ar.idAr = a.idAr and a.idAl = c.idAl", args);
    	int nameColC = c1.getColumnIndex("nameC");
    	int nameColAl = c1.getColumnIndex("nameAl");
    	int nameColAr = c1.getColumnIndex("nameAr");
    	int generoColAl = c1.getColumnIndex("genero");
    	int numCanciones = c1.getCount();
    	
		int j = 0;
		if(numCanciones > 0){
			String [] canciones = new String[numCanciones];
			
			while(c1.moveToNext()){
				canciones[j] = c1.getString(nameColC);
				columnasAlbum[0] = c1.getString(nameColAl);
				columnasAlbum[1] = c1.getString(nameColAr);
				columnasAlbum[2] = c1.getString(generoColAl);
				j++;
			}
			
			c1.moveToFirst();
			historial.add(c1.getString(nameColAl));
			
			String lista = "";
			setContentView(R.layout.activity_consultar_album);
			TextView txtCancion = (TextView)findViewById(R.id.TextView03);
			for(int k = 0; k < numCanciones; k++){
				lista += canciones[k] + "\n";
			}
			txtCancion.setText(lista);
			
		}else{
	    	Cursor c2 = db.rawQuery("select a.nameAr, al.nameAl, al.genero from artista a, album al where idAl = ? and a.idAr = al.idAr", args);
	    	nameColAl = c2.getColumnIndex("nameAl");
	    	nameColAr = c2.getColumnIndex("nameAr");
	    	generoColAl = c2.getColumnIndex("genero");
	    	String cancion = "";
			
			c2.moveToNext();
			cancion = "Este álbum no tiene ninguna canción";
			columnasAlbum[0] = c2.getString(nameColAl);
			columnasAlbum[1] = c2.getString(nameColAr);
			columnasAlbum[2] = c2.getString(generoColAl);
			
			historial.add(c2.getString(nameColAl));
			
			setContentView(R.layout.activity_consultar_album);
			TextView txtCancion = (TextView)findViewById(R.id.TextView03);
			txtCancion.setText(cancion);
		}

		TextView txtAlbum = (TextView)findViewById(R.id.textView3);
		txtAlbum.setText(columnasAlbum[0]);
		
		TextView txtArtista = (TextView)findViewById(R.id.TextView02);
		txtArtista.setText(columnasAlbum[1]);
		
		TextView txtGenero = (TextView)findViewById(R.id.TextView04);
		txtGenero.setText(columnasAlbum[2]);

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
