package co.edu.uptc.usuariossqlite.logic;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import co.edu.uptc.usuariossqlite.R;
import co.edu.uptc.usuariossqlite.entidades.Persona;

/**
 * Created by Frank on 17/03/2018.
 */

public class AdaptadorPersonas extends ArrayAdapter<Persona> {

    public AdaptadorPersonas(@NonNull Context context, List<Persona> resource) {
        super(context,0, resource);
    }

    public View getView(int position, View convertView, ViewGroup parent){

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (null==convertView){
            convertView = inflater.inflate(R.layout.item,parent,false);
        }

        TextView txtNombre = (TextView) convertView.findViewById(R.id.txtNombreItem);
        TextView txtCorreo = (TextView) convertView.findViewById(R.id.txtCorreoItem);
        TextView txtTelefono = (TextView) convertView.findViewById(R.id.txtTelefonoItem);

        Persona persona = getItem(position);


        txtNombre.setText(persona.getNombre());
        txtCorreo.setText(persona.getCorreo());
        txtTelefono.setText(String.valueOf(persona.getTelefono()));

        return  convertView;
    }
}
