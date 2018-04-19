package co.edu.uptc.swiftservicecliente.fragments;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import co.edu.uptc.swiftservicecliente.R;
import co.edu.uptc.swiftservicecliente.adaptaer.PlatilloImagenAdapter;
import co.edu.uptc.swiftservicecliente.entidades.Platillo;

@SuppressLint("ValidFragment")
public class PedidoFragment extends Fragment {

    private  static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
   private static final Date date = new Date();
   private   String fecha = dateFormat.format(date);

   TextView campoFecha, campoMesa, campoCostoTotal;
   EditText campoObservaciones;
   Button btnEnviarOrden;
   RecyclerView recyclerPlatillosPedido;
   private ArrayList<Platillo> listaPlatillosPedido;
   int numeroMesa;
   int precio;


    PlatilloImagenAdapter platilloAdapter;

   ProgressDialog progreso;
   StringRequest stringRequest;
   RequestQueue request;


   public PedidoFragment (ArrayList<Platillo> listaPlatillosPedido, int i){
       this.listaPlatillosPedido = listaPlatillosPedido;
       this.numeroMesa  = i;
   }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View v  = inflater.inflate(R.layout.list_pedido, container, false);

        this.recyclerPlatillosPedido = (RecyclerView) v.findViewById(R.id.idRecyclerViewPedido);
        this.recyclerPlatillosPedido.setLayoutManager(new LinearLayoutManager(this.getContext()));
        this.recyclerPlatillosPedido.setHasFixedSize(true);


        this.campoFecha = (TextView)v.findViewById(R.id.campoFecha);
        this.campoFecha.setText("FECHA: " +  fecha);

        this.campoMesa = (TextView) v.findViewById(R.id.campoMesa);

        this.campoMesa.setText("MESA: "  +  this.numeroMesa);

        this.campoObservaciones = (EditText)v.findViewById(R.id.campoObservaciones);
        this.campoCostoTotal  =(TextView) v.findViewById(R.id.campoCostoTotal);
        this.btnEnviarOrden = (Button) v.findViewById(R.id.btnEnviarOrden);
        request = Volley.newRequestQueue(getContext());
        this.precio = 0;


        platilloAdapter = new PlatilloImagenAdapter(listaPlatillosPedido);

        platilloAdapter.setOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Platillo p = listaPlatillosPedido.get(recyclerPlatillosPedido.getChildAdapterPosition(v));
                Toast.makeText(getContext(),p.getNombre() + "removido", Toast.LENGTH_SHORT).show();
                calcularCostoEliminarProducto(p.getPrecio());
                listaPlatillosPedido.remove(p);
                platilloAdapter.updateData(listaPlatillosPedido);

            }
        });

        calcularCostoTotal();
        recyclerPlatillosPedido.setAdapter(platilloAdapter);


        this.btnEnviarOrden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               realizarTransaccion();
            }
        });
        return v;
    }


    public void calcularCostoTotal (){

       for ( int i =  0;  i< listaPlatillosPedido.size(); i++){
           Platillo p = listaPlatillosPedido.get(i);
          precio = precio + p.getPrecio();
       }
       this.campoCostoTotal.setText("Costo Total " + "$"+ precio);
    }

    public void calcularCostoEliminarProducto (int costo){
        precio = precio-costo;
        this.campoCostoTotal.setText("Costo Total " + "$"+ precio);
    }


    private void enviarPedido() {

        progreso = new ProgressDialog(getContext());
        progreso.setMessage("Cargando...");
        progreso.show();

        String ip = getString(R.string.ip);
        String url = ip+"/BDremotaSwiftServiceCliente/wsJSONRegistroPedidoMovil.php?";

        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progreso.hide();

                if (response.trim().equalsIgnoreCase("registra pedido")){
                    campoObservaciones.setText("");
                    campoCostoTotal.setText("");
                    platilloAdapter.clearList(listaPlatillosPedido);
                    recyclerPlatillosPedido.setAdapter(platilloAdapter);



                    Toast.makeText(getContext(),"Se ha Registrado Pedido con exito",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getContext(),"No_se_pudo_registrar pedido",Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(),"No_se_puede_conectar_pedidos",Toast.LENGTH_SHORT).show();
                progreso.hide();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                String observaciones = campoObservaciones.getText().toString();
                String costo_total=  String.valueOf(precio);
                String mesa = String.valueOf(numeroMesa);
                String estado = "espera";

                Map<String,String> parametros = new HashMap<>();
                parametros.put("mesa", mesa);
                parametros.put("observaciones",observaciones);
                parametros.put("costo_total",costo_total);
                parametros.put("fecha",fecha);
                parametros.put("estado",estado);
                parametros.put("productos", getProductos());

                return parametros;
            }
        };
        request.add(stringRequest);

    }

    public void realizarTransaccion (){
        if (listaPlatillosPedido.size()>=1){
            enviarPedido();
        }else{
            Toast.makeText(getContext(),"Debe añadir almenos UN producto",Toast.LENGTH_LONG).show();
        }
    }

    public String getProductos (){
        String tmp="";

        for ( int i =  0; i<listaPlatillosPedido.size();i++){
            Platillo p = listaPlatillosPedido.get(i);
            tmp = tmp + p.getNombre()+","+p.getDescripcion()+", $"+p.getPrecio()+"\n";
        }
        return  tmp;
    }


}
