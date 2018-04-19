package co.edu.uptc.swiftservicecliente.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import co.edu.uptc.swiftservicecliente.R;
import co.edu.uptc.swiftservicecliente.adaptaer.ViewPagerAdapter;
import co.edu.uptc.swiftservicecliente.entidades.Platillo;

@SuppressLint("ValidFragment")
public class CategoriaPlatilloFragment extends Fragment{


    private AppBarLayout appBarLayout;
    private TabLayout tabs;
    private ViewPager viewPager;
    private View vista;
    private ArrayList<Platillo> listaOrden;
    private ArrayList<Platillo> listaPlatosPrincipales;
    private ArrayList<Platillo> listaEnsaladas;
    private ArrayList<Platillo> listaPostres;


    public  CategoriaPlatilloFragment (ArrayList listaOrden,ArrayList listaPlatosPrincipales, ArrayList listaEnsaladas, ArrayList listaPostres){
        this.listaOrden = listaOrden;
        this.listaPlatosPrincipales = listaPlatosPrincipales;
        this.listaEnsaladas = listaEnsaladas;
        this.listaPostres = listaPostres;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        vista = inflater.inflate(R.layout.fragment_categorias, container, false);

        View contenedor = (View) container.getParent();
        if (appBarLayout == null) {
            this.appBarLayout = (AppBarLayout) contenedor.findViewById(R.id.appBar);
            this.tabs = new TabLayout(getActivity());
            this.appBarLayout.addView(tabs);

            viewPager = (ViewPager) vista.findViewById(R.id.idViewPagerConsulta);
            llenarViewPager(viewPager);
            viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                }
            });
            tabs.setupWithViewPager(viewPager);
        }

        return vista;
    }

    private void llenarViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getFragmentManager());
        adapter.addFragments(new Tab_platoPrincipalFragment(listaOrden,listaPlatosPrincipales),"PLATOS PRINCIPALES");
        adapter.addFragments(new Tab_ensaladaFragment(listaOrden,listaEnsaladas),"ENSALADAS");
        adapter.addFragments(new Tab_postreFragment(listaOrden,listaPostres),"POSTRES");

        viewPager.setAdapter(adapter);
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
        this.appBarLayout.removeView(tabs);
    }

}
