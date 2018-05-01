package co.edu.uptc.swiftservicecliente.fragments.cliente;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import co.edu.uptc.swiftservicecliente.R;


public class InicioFragmentCliente extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       // setRetainInstance(true);

        return inflater.inflate(R.layout.fragment_inicio_cliente, container, false);
    }



}
