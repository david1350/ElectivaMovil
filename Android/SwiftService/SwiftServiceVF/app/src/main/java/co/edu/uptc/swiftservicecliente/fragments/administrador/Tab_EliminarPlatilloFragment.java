package co.edu.uptc.swiftservicecliente.fragments.administrador;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import co.edu.uptc.swiftservicecliente.R;


public class Tab_EliminarPlatilloFragment extends Fragment {


    EditText txvEliminar;
    Button buttonEliminar;

    ProgressDialog progreso;
    RequestQueue request;
    StringRequest stringRequest;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View v =  inflater.inflate(R.layout.fragment_tab_eliminar_platillo, container, false);


       txvEliminar = (EditText) v.findViewById(R.id.cajaEliminar);
       buttonEliminar = (Button) v.findViewById(R.id.btnEliminarPlatillo);
       this.request = Volley.newRequestQueue(getContext());

       this.buttonEliminar.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               eliminarPlato();
           }
       });

       return v;
    }


    public void  eliminarPlato (){

        if (txvEliminar.getText().toString().length()>=1){
            eliminarPlatillo();
            txvEliminar.setText("");
        }else {
            txvEliminar.setError("Debe indicar un numero valido");
        }

    }


    private void eliminarPlatillo() {
        progreso = new ProgressDialog(getContext());
        progreso.setMessage("Cargando...");
        progreso.show();

        String ip = getString(R.string.ip);
        String url =ip+"/BDremotaSwiftService/wsJSONDeleteMovil.php?id_platillo="+txvEliminar.getText().toString();

        stringRequest  = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progreso.hide();

                if (response.trim().equalsIgnoreCase("elimina")){
                    Toast.makeText(getContext(), "eliminado correctamente", Toast.LENGTH_SHORT).show();
                }else{
                    if (response.trim().equalsIgnoreCase("noExiste")) {
                        Toast.makeText(getContext(), "No se encuentra platillo", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "No se ha eliminado", Toast.LENGTH_SHORT).show();
                    }
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "No se ha podido conectar", Toast.LENGTH_SHORT).show();
                progreso.hide();
            }
        });
        request.add(stringRequest);
    }

}
