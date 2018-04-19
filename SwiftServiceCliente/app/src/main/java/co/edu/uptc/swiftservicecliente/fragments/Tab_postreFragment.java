package co.edu.uptc.swiftservicecliente.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import co.edu.uptc.swiftservicecliente.R;
import co.edu.uptc.swiftservicecliente.adaptaer.PlatilloImagenAdapter;
import co.edu.uptc.swiftservicecliente.entidades.Platillo;

@SuppressLint("ValidFragment")
public class Tab_postreFragment extends Fragment {

    RecyclerView recyclerPlatillos;
    ArrayList<Platillo> listaPostres;
    ArrayList<Platillo> listaOrden;

    public Tab_postreFragment (ArrayList<Platillo> Orden,ArrayList<Platillo> listaPlatillos){
        this.listaOrden = Orden;
        this.listaPostres = listaPlatillos;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_recicler_view, container, false);

        this.recyclerPlatillos = (RecyclerView) v.findViewById(R.id.idRecyclerView);
        this.recyclerPlatillos.setLayoutManager(new LinearLayoutManager(this.getContext()));
        this.recyclerPlatillos.setHasFixedSize(true);

        PlatilloImagenAdapter platilloAdapter = new PlatilloImagenAdapter(listaPostres);

        platilloAdapter.setOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Platillo p = listaPostres.get(recyclerPlatillos.getChildAdapterPosition(v));
                Toast.makeText(getContext(),p.getNombre() + "añadido a orden", Toast.LENGTH_SHORT).show();
                listaOrden.add(p);
            }
        });

        recyclerPlatillos.setAdapter(platilloAdapter);

        return v;
    }

}
