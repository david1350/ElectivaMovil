package co.edu.uptc.swiftservicecliente.fragments.administrador;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import co.edu.uptc.swiftservicecliente.R;
import co.edu.uptc.swiftservicecliente.adaptaer.ViewPagerAdapter;

public class ConsultarPlatilloFragment extends Fragment{



    private AppBarLayout appBarLayout;
    private TabLayout tabs;
    private ViewPager viewPager;
    private View vista;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        vista = inflater.inflate(R.layout.fragment_consultar_platillo, container, false);

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
        adapter.addFragments(new Tab_ConsultarPlatilloFragment(),"CONSULTAR PLATILLO");
        adapter.addFragments(new Tab_EditarPlatilloFragment(),"EDITAR PLATILLO");
        adapter.addFragments(new Tab_EliminarPlatilloFragment(),"ELIMINAR PLATILLO");

        viewPager.setAdapter(adapter);
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
        this.appBarLayout.removeView(tabs);
    }

}
