package es.uvigo.esei.dm1415.p08;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class IntroducirArtista extends Activity {
	TextView labelnameartista;
	EditText txtnameartista;
	Button btnadd;
	
	SQLiteDatabase db;
	
	ArrayList<String> historial;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_introducir_artista);
		
		labelnameartista = (TextView) findViewById(R.id.textView4);
		txtnameartista = (EditText) findViewById(R.id.EditText02);
		
		btnadd = (Button) findViewById(R.id.button1);
		
		Intent i = getIntent(); // gets the previously created intent
		historial = i.getStringArrayListExtra("historial");
		
		try{
			db = openOrCreateDatabase("musicaBD", MODE_PRIVATE, null);
			btnadd.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					String nameArtista = txtnameartista.getText().toString();

					String [] columnasArtista = {"idAr"};
					String [] valoresArtista = {nameArtista};
					Cursor c1 = db.query("artista", columnasArtista, "nameAr = ?", valoresArtista, null, null, null);
					c1.moveToNext();
					int numRow = c1.getCount();
					if(numRow == 0){
						db.execSQL("insert into artista(nameAr) values('" + nameArtista + "')");						
					}
		        	try{
		        		Intent i = new Intent(IntroducirArtista.this, MainActivity.class);
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
