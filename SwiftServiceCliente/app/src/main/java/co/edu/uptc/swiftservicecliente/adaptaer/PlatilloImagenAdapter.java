package co.edu.uptc.swiftservicecliente.adaptaer;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import co.edu.uptc.swiftservicecliente.R;
import co.edu.uptc.swiftservicecliente.entidades.Platillo;

/**
 * Adaptador de un platillo Imagen.
 */

public class PlatilloImagenAdapter extends RecyclerView.Adapter<PlatilloImagenAdapter.PlatillosHolder>
implements View.OnClickListener{

    List<Platillo> listaPlatillos;
    private View.OnClickListener listener;

    public PlatilloImagenAdapter(List<Platillo> listaUsuarios) {
        this.listaPlatillos = listaUsuarios;
    }

    @Override
    public PlatillosHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View vista= LayoutInflater.from(parent.getContext()).inflate(R.layout.plaltillos_list_imagen,parent,false);
        RecyclerView.LayoutParams layoutParams=new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        vista.setLayoutParams(layoutParams);

        vista.setOnClickListener(this);
        return new PlatillosHolder(vista);
    }

    @Override
    public void onBindViewHolder(PlatillosHolder holder, int position) {
        holder.txtNombre.setText(listaPlatillos.get(position).getNombre().toString());
        holder.txtDescripcion.setText(listaPlatillos.get(position).getDescripcion().toString());
        holder.txtPrecio.setText(listaPlatillos.get(position).getPrecio().toString());

        if (listaPlatillos.get(position).getImagen()!=null){
            holder.imagenConsultar.setImageBitmap(listaPlatillos.get(position).getImagen());
        }else{
            holder.imagenConsultar.setImageResource(R.drawable.img_base);
        }
    }

    public void setOnclickListener (View.OnClickListener listener){
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        return listaPlatillos.size();
    }

    @Override
    public void onClick(View v) {
         if (listener!=null){
             listener.onClick(v);
         }
    }


    public void updateData(List<Platillo> rashisList){
        notifyDataSetChanged();
    }

    public void clearList (List<Platillo> rashisList){
        rashisList.clear();
    }


    public class PlatillosHolder extends RecyclerView.ViewHolder{

        TextView  txtNombre,txtDescripcion, txtPrecio;
        ImageView imagenConsultar;
        CheckBox ischecked;

        public PlatillosHolder(View itemView) {
            super(itemView);
            txtNombre= (TextView) itemView.findViewById(R.id.campoListarNombre);
            txtDescripcion= (TextView) itemView.findViewById(R.id.campoListarDescripcion);
            txtPrecio = (TextView) itemView.findViewById(R.id.campoListarPrecio);
            imagenConsultar = (ImageView)itemView.findViewById(R.id.campoListarImagen);

        }

    }


}
