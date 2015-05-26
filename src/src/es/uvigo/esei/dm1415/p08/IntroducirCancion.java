package es.uvigo.esei.dm1415.p08;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class IntroducirCancion extends Activity {
	TextView labelnamecancion;
	TextView labelnamealbum;
	TextView labelnameartista;
	TextView labelnamegenero;
	EditText txtnamecancion;
	EditText txtnamealbum;
	EditText txtnameartista;
	EditText txtnamegenero;
	Button btnadd;
	
	SQLiteDatabase db;
	
	ArrayList<String> historial;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_introducir_cancion);
		
		labelnamecancion = (TextView) findViewById(R.id.textView1);
		txtnamecancion = (EditText) findViewById(R.id.editText1);
		
		labelnamealbum = (TextView) findViewById(R.id.textView2);
		txtnamealbum = (EditText) findViewById(R.id.EditText02);
		
		labelnameartista = (TextView) findViewById(R.id.textView4);
		txtnameartista = (EditText) findViewById(R.id.EditText03);
		
		labelnamegenero = (TextView) findViewById(R.id.textView5);
		txtnamegenero = (EditText) findViewById(R.id.EditText04);
		
		btnadd = (Button) findViewById(R.id.button1);
		
		Intent i = getIntent(); // gets the previously created intent
		historial = i.getStringArrayListExtra("historial");
		
		try{
			db = openOrCreateDatabase("musicaBD", MODE_PRIVATE, null);
			btnadd.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					String nameCancion = txtnamecancion.getText().toString();
					String nameAlbum = txtnamealbum.getText().toString();
					String nameArtista = txtnameartista.getText().toString();
					String nameGenero = txtnamegenero.getText().toString();
					
					String [] columnasArtista = {"idAr"};
					String [] valoresArtista = {nameArtista};
					Cursor c1 = db.query("artista", columnasArtista, "nameAr = ?", valoresArtista, null, null, null);
					c1.moveToNext();
					int numRow = c1.getCount();
					if(numRow == 0){
						db.execSQL("insert into artista(nameAr) values('" + nameArtista + "')");
					}
					
					String [] columnasAlbum = {"idAl"};
					String [] valoresAlbum = {nameAlbum};
					Cursor c2 = db.query("album", columnasAlbum, "nameAl = ?", valoresAlbum, null, null, null);
					c2.moveToNext();
					int numRow2 = c2.getCount();
					if(numRow2 == 0){
						Cursor c3 = db.query("artista", columnasArtista, "nameAr = ?", valoresArtista, null, null, null);
						c3.moveToNext();
						int col = c3.getColumnIndex("idAr");
						int idAr = c3.getInt(col);
						db.execSQL("insert into album(nameAl, genero, idAr) values('" + nameAlbum + "', '" + nameGenero + "', '" + idAr + "')");		
					}
					
					String [] columnasCancion = {"idC"};
					String [] valoresCancion = {nameCancion};
					Cursor c4 = db.query("cancion", columnasCancion, "nameC = ?", valoresCancion, null, null, null);
					c4.moveToNext();
					int numRow3 = c4.getCount();
		        	if(numRow3 == 0){
		        		Cursor c5 = db.query("album", columnasAlbum, "nameAl = ?", valoresAlbum, null, null, null);
		        		c5.moveToNext();
		        		int col2 = c5.getColumnIndex("idAl");
		        		int idAl = c5.getInt(col2);
		        		db.execSQL("insert into cancion(nameC, idAl) values('" + nameCancion + "', '" + idAl + "')");
		        	}
		        	//Redirigir al home
		        	try{
		        		Intent i = new Intent(IntroducirCancion.this, MainActivity.class);
		        		i.putStringArrayListExtra("historial", historial);
		        		startActivity(i);
		        	}catch(Exception e){
		        		e.getStackTrace();
		        	}
				}//cierra onClick
			 });
		 }catch (Exception e) {
			 Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
		 }
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
