package co.edu.uptc.swiftservicecliente.adaptaer;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import co.edu.uptc.swiftservicecliente.R;
import co.edu.uptc.swiftservicecliente.entidades.Platillo;

/**
 * Created by CHENAO on 6/08/2017.
 */

public class PlatilloImagenAdapter extends RecyclerView.Adapter<PlatilloImagenAdapter.PlatillosHolder>{

    List<Platillo> listaPlatillos;

    public PlatilloImagenAdapter(List<Platillo> listaUsuarios) {
        this.listaPlatillos = listaUsuarios;
    }

    @Override
    public PlatillosHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View vista= LayoutInflater.from(parent.getContext()).inflate(R.layout.plaltillos_list_imagen,parent,false);
        RecyclerView.LayoutParams layoutParams=new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        vista.setLayoutParams(layoutParams);
        return new PlatillosHolder(vista);
    }

    @Override
    public void onBindViewHolder(PlatillosHolder holder, int position) {
        holder.txtID.setText(listaPlatillos.get(position).getID().toString());
        holder.txtNombre.setText(listaPlatillos.get(position).getNombre().toString());
        holder.txtDescripcion.setText(listaPlatillos.get(position).getDescripcion().toString());
        holder.txtCategoria.setText(listaPlatillos.get(position).getCategoria().toString());
        holder.txtPrecio.setText(listaPlatillos.get(position).getPrecio().toString());

        if (listaPlatillos.get(position).getImagen()!=null){
            holder.imagenConsultar.setImageBitmap(listaPlatillos.get(position).getImagen());
        }else{
            holder.imagenConsultar.setImageResource(R.drawable.img_base);
        }
    }

    @Override
    public int getItemCount() {
        return listaPlatillos.size();
    }

    public class PlatillosHolder extends RecyclerView.ViewHolder{

        TextView txtID,txtNombre,txtDescripcion, txtCategoria,txtPrecio;
        ImageView imagenConsultar;

        public PlatillosHolder(View itemView) {
            super(itemView);
            txtID= (TextView) itemView.findViewById(R.id.campoListarID);
            txtNombre= (TextView) itemView.findViewById(R.id.campoListarNombre);
            txtDescripcion= (TextView) itemView.findViewById(R.id.campoListarDescripcion);
            txtCategoria = (TextView)itemView.findViewById(R.id.campoListarCategoria);
            txtPrecio = (TextView) itemView.findViewById(R.id.campoListarPrecio);
            imagenConsultar = (ImageView)itemView.findViewById(R.id.campoListarImagen);

        }
    }
}
