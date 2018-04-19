package co.edu.uptc.swiftservicecliente.entidades;


import android.content.Context;
import android.util.Log;
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
import java.util.ArrayList;


/**
 * Created by Frank on 15/04/2018.
 */

public class Servicio implements Response.Listener<JSONObject>,Response.ErrorListener{


    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    Context context;
    ArrayList<Platillo> listaPlatillos;


    public  Servicio (Context context){
        this.context = context;
        this.request = Volley.newRequestQueue(context);
        this.listaPlatillos = new ArrayList<>();
    }

    private void cargarWebService() {

        String url ="https://swiftservicefd.000webhostapp.com/BDremotaSwiftServiceCliente/wsJSONConsultarListaImagenes.php";

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        request.add(jsonObjectRequest);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(this.context,"No se ha podido CONSULTAR" + error.toString(),Toast.LENGTH_SHORT).show();
        Log.i("error",error.toString());
    }

    @Override
    public void onResponse(JSONObject response) {
        Platillo platillo = null;
        JSONArray json = response.optJSONArray("platillo");
        try {
            for (int i = 0; i<json.length();i++){
                platillo = new Platillo();
                JSONObject jsonObject = null;
                jsonObject = json.getJSONObject(i);

                platillo.setID(jsonObject.optInt("id_platillo"));
                platillo.setNombre(jsonObject.optString("nombre"));
                platillo.setDescripcion(jsonObject.optString("descripcion"));
                platillo.setCategoria(jsonObject.optString("categoria"));
                platillo.setPrecio(jsonObject.getInt("precio"));
                platillo.setMetadataImagen(jsonObject.optString("imagen"));
                listaPlatillos.add(platillo);
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this.context,"No se ha podido establecer conexion con el servidor",Toast.LENGTH_SHORT).show();
        }


    }


    public ArrayList getListaPlatillos (){
        cargarWebService();
        return  listaPlatillos;
    }




}
