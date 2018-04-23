package co.edu.uptc.swiftservicecliente.adaptaer;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import co.edu.uptc.swiftservicecliente.R;
import co.edu.uptc.swiftservicecliente.entidades.Pedido;
import co.edu.uptc.swiftservicecliente.entidades.Platillo;

/**
 * Adaptador de un pedido.
 */

public class PlatilloPedidosAdapter extends RecyclerView.Adapter<PlatilloPedidosAdapter.PlatillosHolder>
implements View.OnClickListener{

    List<Pedido> listaPedidos;
    private View.OnClickListener listener;

    public PlatilloPedidosAdapter(List<Pedido> listaPed) {
        this.listaPedidos = listaPed;
    }

    @Override
    public PlatillosHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View vista= LayoutInflater.from(parent.getContext()).inflate(R.layout.platillos_list_hechos,parent,false);
        RecyclerView.LayoutParams layoutParams=new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        vista.setLayoutParams(layoutParams);

        vista.setOnClickListener(this);
        return new PlatillosHolder(vista);
    }

    @Override
    public void onBindViewHolder(PlatillosHolder holder, int position) {
        holder.txvFecha.setText("Fecha: " + listaPedidos.get(position).getFecha().toString());
        holder.txvNumeroPedido.setText("Numero Orden: " + listaPedidos.get(position).getId_pedido().toString());
        holder.txvMesa.setText("Mesa: " + listaPedidos.get(position).getMesa().toString());
        holder.txvProductos.setText("Platos de Orden:\n"+listaPedidos.get(position).getProductos().toString());
        holder.txvObservaciones.setText("Observaciones: "+listaPedidos.get(position).getObservaciones().toString());
        holder.txvEstado.setText("ESTADO ORDEN: "+listaPedidos.get(position).getEstado().toString());
        holder.txtvCosto.setText("Costo Pedido: "+listaPedidos.get(position).getCostoTotal().toString());

    }

    public void setOnclickListener (View.OnClickListener listener){
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        return listaPedidos.size();
    }

    @Override
    public void onClick(View v) {
         if (listener!=null){
             listener.onClick(v);
         }
    }


    public void updateData(List<Pedido> rashisList){
        notifyDataSetChanged();
    }

    public void clearList (List<Platillo> rashisList){
        rashisList.clear();
    }


    public class PlatillosHolder extends RecyclerView.ViewHolder{

        TextView  txvFecha,txvNumeroPedido,txvMesa, txvProductos,txvObservaciones,txvEstado,txtvCosto;


        public PlatillosHolder(View itemView) {
            super(itemView);
            txvFecha = (TextView) itemView.findViewById(R.id.campoFechaPedido);
            txvNumeroPedido = (TextView) itemView.findViewById(R.id.campoIDpedido);
            txvMesa = (TextView) itemView.findViewById(R.id.campoMesa);
            txvProductos = (TextView) itemView.findViewById(R.id.campoProductos);
            txvObservaciones = (TextView) itemView.findViewById(R.id.campoObservacionesHechas);
            txvEstado = (TextView) itemView.findViewById(R.id.campoEstado);
            txtvCosto = (TextView) itemView.findViewById(R.id.campoCostoPedido);

        }

    }


}
