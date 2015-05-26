package es.uvigo.esei.dm1415.p08;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Fragmento1 extends Fragment{
	OnPictureSelectedListener mListener;
	
	String [] contenido;
	String tabla;
	SQLiteDatabase db;
	ArrayList<String> historial;
	
	public Fragmento1(String [] contenido, String tabla, SQLiteDatabase db, ArrayList<String> historial){
		this.contenido = contenido;
		this.tabla = tabla;
		this.db = db;
		if(historial == null){
			Log.e("Error en fragmento", "Historial vacío");
		}
		
		this.historial = historial;
	}
	
	// ///////////////////////////////////////////////////////////////
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {			
		ListView listView = new ListView(getActivity());
		
		ArrayAdapter<String> array = new ArrayAdapter<String>(
				getActivity(),
				android.R.layout.simple_list_item_1,
				contenido);
		
		listView.setAdapter(array);
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
				// Aquí dirigir a la consulta que se clica
				String [] columnas;
				String [] valores;
				
				try{	
					if(tabla == "cancion"){
						columnas = new String[]{"idC"};
						valores = new String[]{contenido[position]};
						Cursor c1 = db.query("cancion", columnas, "nameC = ?", valores, null, null, null);
						c1.moveToNext();
						int col = c1.getColumnIndex("idC");
						int idItem = c1.getInt(col);
						Intent i = new Intent(getActivity(), ConsultarCancion.class);
						i.putExtra("cancion", idItem);
						i.putStringArrayListExtra("historial", historial);
						startActivity(i);
					}else{
						if(tabla == "album"){
							columnas = new String[]{"idAl"};
							valores = new String[]{contenido[position]};
							Cursor c1 = db.query(tabla, columnas, "nameAl = ?", valores, null, null, null);
							c1.moveToNext();
							int col = c1.getColumnIndex("idAl");
							int idItem = c1.getInt(col);
							Intent i = new Intent(getActivity(), ConsultarAlbum.class);
							i.putExtra("album", idItem);
							i.putStringArrayListExtra("historial", historial);
			        		startActivity(i);
						}else{
							if(tabla == "artista"){
								columnas = new String[]{"idAr"};
								valores = new String[]{contenido[position]};
								Cursor c1 = db.query(tabla, columnas, "nameAr = ?", valores, null, null, null);
								c1.moveToNext();
								int col = c1.getColumnIndex("idAr");
								int idItem = c1.getInt(col);
								Intent i = new Intent(getActivity(), ConsultarArtista.class);
								i.putExtra("artista", idItem);
								i.putStringArrayListExtra("historial", historial);
				        		startActivity(i);
							}
						}
					}
				}catch(Exception e){
					Log.e("Error", "" + e.getMessage());
				}
			
		        // Send the event and clicked item's row ID to the host activity
				mListener.onPictureSelected(position);
			}
		});
		return listView;
	}//onCreateView
	
	// ///////////////////////////////////////////////////////////////
	// Main Activity must implement this interface
    public interface OnPictureSelectedListener {
        public void onPictureSelected(Integer selectedRow);
    }
    
    // ///////////////////////////////////////////////////////////////
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnPictureSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + "Your activity must implement OnArticleSelectedListener");
        }
    }//onAttach
}
