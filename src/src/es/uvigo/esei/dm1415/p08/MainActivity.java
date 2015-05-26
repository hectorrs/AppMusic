package es.uvigo.esei.dm1415.p08;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.Toast;
import es.uvigo.esei.dm1415.p08.Fragmento1.OnPictureSelectedListener;

public class MainActivity extends Activity implements TabListener,
		OnPictureSelectedListener {
	// Fragmento que se ve al iniciar
	SQLiteDatabase db;

	Integer tabActual = 0;

	RelativeLayout mainLayout;

	FragmentTransaction fragTransactMgr = null;

	// Número de tabs con sus nombres
	private final String tab1 = "Canciones";
	private final String tab2 = "Álbumes";
	private final String tab3 = "Artistas";

	String[] canciones;
	String[] artistas;
	String[] albumes;

	ArrayList<String> historial;

	private Boolean firstTime = null;
	
	private Boolean drop = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) throws SQLiteException {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (isFirstTime()) {
			historial = new ArrayList<String>();
		} else {
			Intent intent = getIntent();
			historial = intent.getStringArrayListExtra("historial");
		}
		
		try {
			db = this.openOrCreateDatabase("musicaBD", MODE_PRIVATE, null);
			
			if(isFirstTimeDrop()){
				db.execSQL("DROP TABLE IF EXISTS 'cancion'");
				db.execSQL("DROP TABLE IF EXISTS 'album'");
				db.execSQL("DROP TABLE IF EXISTS 'artista'");
				db.execSQL("create table artista ("
						+ " idAr integer PRIMARY KEY autoincrement, "
						+ " nameAr text );");

				db.execSQL("create table album ("
						+ " idAl integer PRIMARY KEY autoincrement, "
						+ " nameAl text, " + " genero text, "
						+ " idAr integer,"
						+ "FOREIGN KEY(idAr) REFERENCES artista(idAr) );");

				db.execSQL("create table cancion ("
						+ " idC integer PRIMARY KEY autoincrement, "
						+ " nameC text, " + " idAl integer,"
						+ "FOREIGN KEY(idAl) REFERENCES album(idAl) );");

				db.execSQL("insert into artista(nameAr) values('Pepe')");
				db.execSQL("insert into artista(nameAr) values('Juan')");
				db.execSQL("insert into artista(nameAr) values('Ana')");
				db.execSQL("insert into artista(nameAr) values('Luis')");
				db.execSQL("insert into artista(nameAr) values('Marco')");
				db.execSQL("insert into artista(nameAr) values('Javi')");
				db.execSQL("insert into album(nameAl, genero, idAr) values('Ayer', 'Pop', '1')");
				db.execSQL("insert into album(nameAl, genero, idAr) values('Hoy', 'Rock', '1')");
				db.execSQL("insert into album(nameAl, genero, idAr) values('Mañana', 'House', '2')");
				db.execSQL("insert into album(nameAl, genero, idAr) values('Pasado', 'Dance', '3')");
				db.execSQL("insert into album(nameAl, genero, idAr) values('Antesdeayer', 'Jazz', '4')");
				db.execSQL("insert into album(nameAl, genero, idAr) values('Pasadomañana', 'Dubstep', '5')");
				db.execSQL("insert into cancion(nameC, idAl) values('Mesa', '1')");
				db.execSQL("insert into cancion(nameC, idAl) values('Silla', '1')");
				db.execSQL("insert into cancion(nameC, idAl) values('Cama', '1')");
				db.execSQL("insert into cancion(nameC, idAl) values('Mesilla', '2')");
				db.execSQL("insert into cancion(nameC, idAl) values('Tele', '2')");
				db.execSQL("insert into cancion(nameC, idAl) values('Sofá', '3')");
				db.execSQL("insert into cancion(nameC, idAl) values('Sillón', '3')");
				db.execSQL("insert into cancion(nameC, idAl) values('Cocina', '4')");
				db.execSQL("insert into cancion(nameC, idAl) values('Nevera', '4')");
				db.execSQL("insert into cancion(nameC, idAl) values('Lámpara', '5')");
				db.execSQL("insert into cancion(nameC, idAl) values('Plato', '5')");
				db.execSQL("insert into cancion(nameC, idAl) values('Tenedor', '5')");
			}

			String[] columnasCancion = { "nameC" };
			Cursor c1 = db.query("cancion", columnasCancion, null, null, null,
					null, "nameC");
			int nameColC = c1.getColumnIndex("nameC");
			int numCanciones = c1.getCount();
			canciones = new String[numCanciones];
			int i = 0;
			while (c1.moveToNext()) {
				canciones[i] = c1.getString(nameColC);
				i++;
			}

			String[] columnasArtista = { "nameAr" };
			Cursor c2 = db.query("artista", columnasArtista, null, null, null,
					null, "nameAr");
			int nameColAr = c2.getColumnIndex("nameAr");
			int numArtistas = c2.getCount();
			artistas = new String[numArtistas];
			int j = 0;
			while (c2.moveToNext()) {
				artistas[j] = c2.getString(nameColAr);
				j++;
			}

			String[] columnasAlbum = { "nameAl" };
			Cursor c3 = db.query("album", columnasAlbum, null, null, null,
					null, "nameAl");
			int nameColAl = c3.getColumnIndex("nameAl");
			int numAlbumes = c3.getCount();
			albumes = new String[numAlbumes];
			int k = 0;
			while (c3.moveToNext()) {
				albumes[k] = c3.getString(nameColAl);
				k++;
			}

			mainLayout = (RelativeLayout) findViewById(R.id.mainLayout);

			ActionBar bar = getActionBar();

			// create tabs adding caption and icon
			bar.addTab(bar.newTab().setText(tab1).setTabListener(this));
			bar.addTab(bar.newTab().setText(tab2).setTabListener(this));
			bar.addTab(bar.newTab().setText(tab3).setTabListener(this));

			bar.setDisplayOptions(ActionBar.DISPLAY_USE_LOGO);
			bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

			bar.setDisplayShowHomeEnabled(true);
			bar.setDisplayShowTitleEnabled(false);
			bar.show();
		} catch (Exception e) {
			Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();

		SharedPreferences mPreferences = this.getSharedPreferences(
				"first_time", Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = mPreferences.edit();
		editor.putBoolean("firstTime", true);
		editor.commit();

		historial = null;
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
		} else {
			if (id == R.id.action_settings2) {
				Intent show = new Intent(this, IntroducirCancion.class);
				show.putStringArrayListExtra("historial", historial);
				startActivity(show);
				return true;
			} else {
				if (id == R.id.action_settings3) {
					Intent show = new Intent(this, IntroducirAlbum.class);
					show.putStringArrayListExtra("historial", historial);
					startActivity(show);
					return true;
				} else {
					if (id == R.id.action_settings4) {
						Intent show = new Intent(this, IntroducirArtista.class);
						show.putStringArrayListExtra("historial", historial);
						startActivity(show);
						return true;
					} else {
						if (id == R.id.action_settings5) {
							Intent show = new Intent(this, WebView1.class);
							show.putStringArrayListExtra("historial", historial);
							startActivity(show);
							return true;
						} else {
							if (id == R.id.action_settings6) {
								Intent show = new Intent(this,
										MainActivity.class);
								show.putStringArrayListExtra("historial",
										historial);
								startActivity(show);
								return true;
							} else {
								if (id == R.id.txtMsg) {
									Intent show = new Intent(this,
											MyServiceDriver3.class);
									show.putStringArrayListExtra("historial",
											historial);
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

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		if (tab.getText().equals(tab1)) {
			executeFragment(new Fragmento1(canciones, "cancion", db, historial));
		} else if (tab.getText().equals(tab2)) {
			executeFragment(new Fragmento1(albumes, "album", db, historial));
		} else if (tab.getText().equals(tab3)) {
			executeFragment(new Fragmento1(artistas, "artista", db, historial));
		}
	}

	private void executeFragment(Fragment fragment) {
		try {
			mainLayout.removeAllViews();
			fragTransactMgr = getFragmentManager().beginTransaction();
			// fragTransactMgr.replace(mainLayout.getId(), fragment);
			fragTransactMgr.add(mainLayout.getId(), fragment);
			fragTransactMgr.commit();
		} catch (Exception e) {

		}
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPictureSelected(Integer selectedRow) {
		// TODO Auto-generated method stub

	}

	private boolean isFirstTime() {
		if (firstTime == null) {
			SharedPreferences mPreferences = this.getSharedPreferences(
					"first_time", Context.MODE_PRIVATE);
			firstTime = mPreferences.getBoolean("firstTime", true);
			if (firstTime) {
				SharedPreferences.Editor editor = mPreferences.edit();
				editor.putBoolean("firstTime", false);
				editor.commit();
			}
		}
		return firstTime;
	}
	
	private boolean isFirstTimeDrop() {
		if (drop == null) {
			SharedPreferences mPreferences = this.getSharedPreferences(
					"first_time", Context.MODE_PRIVATE);
			drop = mPreferences.getBoolean("drop", true);
			if (drop) {
				SharedPreferences.Editor editor = mPreferences.edit();
				editor.putBoolean("drop", false);
				editor.commit();
			}
		}
		return drop;
	}
}
