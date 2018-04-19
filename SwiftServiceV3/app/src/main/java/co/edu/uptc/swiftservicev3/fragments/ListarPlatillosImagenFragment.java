package co.edu.uptc.swiftservicev3.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import co.edu.uptc.swiftservicev3.R;
import co.edu.uptc.swiftservicev3.adapter.PlatilloImagenAdapter;
import co.edu.uptc.swiftservicev3.entidades.Platillo;


public class ListarPlatillosImagenFragment extends Fragment implements Response.Listener<JSONObject>,Response.ErrorListener{

    RecyclerView recyclerPlatillos;
    ArrayList<Platillo> listaPlatillos;
    ProgressDialog progreso;

    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_listar_platillo, container, false);

        this.listaPlatillos = new ArrayList<>();
        this.recyclerPlatillos = (RecyclerView) v.findViewById(R.id.idRecyclerImagen);
        this.recyclerPlatillos.setLayoutManager(new LinearLayoutManager(this.getContext()));
        this.recyclerPlatillos.setHasFixedSize(true);

        this.request = Volley.newRequestQueue(getContext());

        cargarWebService();

        return v;
    }

    private void cargarWebService() {

        this.progreso = new ProgressDialog(getContext());
        progreso.setMessage("Consultando Lista....");
        progreso.show();

        String ip = getString(R.string.ip);

        String url =ip+"/BDremotaSwiftService/wsJSONConsultarListaImagenes.php";

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        request.add(jsonObjectRequest);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        progreso.hide();
        Toast.makeText(getContext(),"No se ha podido CONSULTAR" + error.toString(),Toast.LENGTH_SHORT).show();
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
            progreso.hide();
            PlatilloImagenAdapter platilloAdapter = new PlatilloImagenAdapter(listaPlatillos);
            recyclerPlatillos.setAdapter(platilloAdapter);

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getContext(),"No se ha podido establecer conexion con el servidor",Toast.LENGTH_SHORT).show();
        }


    }
}
