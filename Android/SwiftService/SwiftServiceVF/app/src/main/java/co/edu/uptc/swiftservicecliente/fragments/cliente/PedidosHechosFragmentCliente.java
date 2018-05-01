package co.edu.uptc.swiftservicecliente.fragments.cliente;

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
import android.widget.TextView;
import android.widget.Toast;

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

import co.edu.uptc.swiftservicecliente.R;
import co.edu.uptc.swiftservicecliente.adaptaer.PlatilloPedidosAdapterCliente;
import co.edu.uptc.swiftservicecliente.entidades.Pedido;

@SuppressLint("ValidFragment")
public class PedidosHechosFragmentCliente extends Fragment
  implements Response.Listener<JSONObject>,Response.ErrorListener {


    TextView etNumMesa, costoTotalHechos;

    RecyclerView recyclerPedidos;
    private ArrayList<Pedido> listaPedidos;
    ProgressDialog progreso;

    StringRequest stringRequest;
    JsonObjectRequest jsonObjectRequest;
    RequestQueue request;
    int numeroMesa;
    int precio;

    public PedidosHechosFragmentCliente(int nm){
        this.numeroMesa = nm;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View v  = inflater.inflate(R.layout.list_pedidos_hechos_cliente, container, false);

        etNumMesa = (TextView) v.findViewById(R.id.mesaPeidosHechosCliente);
        etNumMesa.setText("MESA: " + numeroMesa);
        this.precio = 0;
        costoTotalHechos = (TextView) v.findViewById(R.id.campoCostoPedidosHechosCliente);

        this.listaPedidos  = new ArrayList<>();
        this.recyclerPedidos = (RecyclerView) v.findViewById(R.id.RecyclerPedidosHechosCliente);
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

        String url =ip+"/BDremotaSwiftServiceCliente/wsJSONConsultarPedidosHechos.php?mesa="+numeroMesa;

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
            calcularCostoPedidosTotal();
            PlatilloPedidosAdapterCliente adapter = new PlatilloPedidosAdapterCliente(listaPedidos);
            recyclerPedidos.setAdapter(adapter);

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getContext(),"No se ha podido establecer conexion con el servidor",Toast.LENGTH_SHORT).show();
        }


    }


    public void calcularCostoPedidosTotal (){
        for ( int i =  0;  i< listaPedidos.size(); i++){
            Pedido p = listaPedidos.get(i);
            precio = precio + p.getCostoTotal();
        }
        this.costoTotalHechos.setText("Costo Total Ordenes " + "$"+ precio);

    }

}
