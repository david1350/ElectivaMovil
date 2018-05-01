package co.edu.uptc.swiftservicecliente.fragments.administrador;

import android.annotation.SuppressLint;
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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import co.edu.uptc.swiftservicecliente.R;
import co.edu.uptc.swiftservicecliente.adaptaer.PlatilloPedidosAdapter;
import co.edu.uptc.swiftservicecliente.entidades.Pedido;

@SuppressLint("ValidFragment")
public class ConsultaPedidosFragment extends Fragment
  implements Response.Listener<JSONObject>,Response.ErrorListener {


    RecyclerView recyclerPedidos;
    private ArrayList<Pedido> listaPedidos;
    ProgressDialog progreso;

    PlatilloPedidosAdapter adapter;

    StringRequest stringRequest;
    JsonObjectRequest jsonObjectRequest;
    RequestQueue request;
    int id_pedido;


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View v  = inflater.inflate(R.layout.list_pedidos_hechos, container, false);


        this.listaPedidos  = new ArrayList<>();
        this.recyclerPedidos = (RecyclerView) v.findViewById(R.id.RecyclerPedidosHechos);
        this.recyclerPedidos.setLayoutManager(new LinearLayoutManager(this.getContext()));
        this.recyclerPedidos.setHasFixedSize(true);
        this.request = Volley.newRequestQueue(getContext());
        cargarWebService();
        return v;
    }



    private void cargarWebService() {

        this.progreso = new ProgressDialog(getContext());
        progreso.setMessage("Consultando Lista....");
        progreso.show();

        String ip = getString(R.string.ip);

        String url =ip+"/BDremotaSwiftService/wsJSONConsultarListaPedidosHechos.php";

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        request.add(jsonObjectRequest);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        progreso.hide();
        Toast.makeText(getContext(),"No se ha podido consultar pedidos hechos" + error.toString(),Toast.LENGTH_SHORT).show();
        Log.i("error",error.toString());
    }

    @Override
    public void onResponse(JSONObject response) {
        Pedido pedido = null;
        JSONArray json = response.optJSONArray("pedido");
        try {
            for (int i = 0; i<json.length();i++){
                pedido = new Pedido();
                JSONObject jsonObject = null;
                jsonObject = json.getJSONObject(i);

                pedido.setId_pedido(jsonObject.optInt("id_pedido"));
                pedido.setMesa(jsonObject.optInt("mesa"));
                pedido.setObservaciones(jsonObject.optString("observaciones"));
                pedido.setCostoTotal(jsonObject.optInt("costo_total"));
                pedido.setFecha(jsonObject.optString("fecha"));
                pedido.setEstado(jsonObject.getString("estado"));
                pedido.setProductos(jsonObject.getString("productos"));
                listaPedidos.add(pedido);
            }
            progreso.hide();
            adapter = new PlatilloPedidosAdapter(listaPedidos);

            adapter.setOnclickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Pedido  p = listaPedidos.get(recyclerPedidos.getChildAdapterPosition(v));
                    id_pedido = p.getId_pedido();
                    actualizarWebService();
                    Toast.makeText(getContext(),"cambiado el estado Pedido: " +p.getId_pedido() , Toast.LENGTH_SHORT).show();
                }
            });


            recyclerPedidos.setAdapter(adapter);

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getContext(),"No se ha podido establecer conexion con el servidor",Toast.LENGTH_SHORT).show();
        }


    }

    private void actualizarWebService() {
        progreso = new ProgressDialog(getContext());
        progreso.setMessage("Cargando...");
        progreso.show();

        String ip = getString(R.string.ip);
        String url =ip+"/BDremotaSwiftService/wsJSONUpdateEstado.php?";

        stringRequest  = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progreso.hide();

                if (response.trim().equalsIgnoreCase("actualiza estado")) {
                    Toast.makeText(getContext(), "Se ha actualizado estado correctamente", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "No se pudo actualizar estado", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "No se ha podido conectar", Toast.LENGTH_SHORT).show();
                progreso.hide();
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                String estad = "entregado";
                String id = String.valueOf(id_pedido);

                Map<String,String> parametros = new HashMap<>();
                parametros.put("id_pedido",id);
                parametros.put("estado",estad);

                return  parametros;

            }
        };
        request.add(stringRequest);
    }



}
