package co.edu.uptc.swiftservicev3.fragments;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import co.edu.uptc.swiftservicev3.R;
import co.edu.uptc.swiftservicev3.adapter.ViewPagerAdapter;
import co.edu.uptc.swiftservicev3.entidades.Platillo;


public class Tab_ConsultarPlatilloFragment extends Fragment implements Response.Listener<JSONObject>,Response.ErrorListener{

    EditText campoConsultarID;
    TextView campoConsultarNombre, campoConsultarDesc,campoCategoria,campoPrecio;
    ImageView imagenConsultar;
    Button buttonConsultar;
    ProgressDialog progreso;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View v =  inflater.inflate(R.layout.fragment_tab_consultar_platillo, container, false);

       this.campoConsultarID = (EditText) v.findViewById(R.id.campoConsultarID);
       this.campoConsultarNombre = (TextView) v.findViewById(R.id.campoConsultarNombre);
       this.campoConsultarDesc = (TextView) v.findViewById(R.id.campoConsultarDescripcion);
       this.campoCategoria = (TextView)v.findViewById(R.id.campoConsultarCategoria);
       this.campoPrecio = (TextView) v.findViewById(R.id.campoConsultarPrecio);
       this.imagenConsultar = (ImageView) v.findViewById(R.id.consultarImagen);
       this.buttonConsultar = (Button) v.findViewById(R.id.btnConsultarUsuario);
       request = Volley.newRequestQueue(getContext());


       this.buttonConsultar.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               cargarWebService();
           }
       });

       return v;
    }



    private void cargarWebService() {

        progreso = new ProgressDialog(getContext());
        progreso.setMessage("Consultando...");
        progreso.show();
        String ip = getString(R.string.ip);
        String url = ip+"/BDremotaSwiftService/wsJSONConsultarRegistroImagen.php?id_platillo="+
                campoConsultarID.getText().toString();

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        request.add(jsonObjectRequest);

    }


    @Override
    public void onErrorResponse(VolleyError error) {
        progreso.hide();
        System.err.println("eeeeeeeeeeeeeeeeerrrrrrrrrrr " + error.toString());
        Toast.makeText(getContext(),"No se ha podido CONSULTAR" + error.toString(),Toast.LENGTH_SHORT).show();
        Log.i("error",error.toString());

    }

    @Override
    public void onResponse(JSONObject response) {

        progreso.hide();
        Platillo miPlatillo = new Platillo();

        JSONArray json = response.optJSONArray("platillo");
        JSONObject jsonObject = null;

        try {
            jsonObject = json.getJSONObject(0);
            miPlatillo.setID(jsonObject.optInt("id_platillo"));
            miPlatillo.setNombre(jsonObject.optString("nombre"));
            miPlatillo.setDescripcion(jsonObject.optString("descripcion"));
            miPlatillo.setCategoria(jsonObject.optString("categoria"));
            miPlatillo.setMetadataImagen(jsonObject.optString("imagen"));
            miPlatillo.setPrecio(jsonObject.optInt("precio"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        campoConsultarNombre.setText("Nombre: " +miPlatillo.getNombre());
        campoConsultarDesc.setText("Descripción: " +miPlatillo.getDescripcion());
        campoCategoria.setText("Categoria: " + miPlatillo.getCategoria());
        campoPrecio.setText("$ " + miPlatillo.getPrecio());



        if (miPlatillo.getImagen()!=null){
            imagenConsultar.setImageBitmap(miPlatillo.getImagen());
        }else{
            imagenConsultar.setImageResource(R.drawable.img_base);
        }
    }

}
