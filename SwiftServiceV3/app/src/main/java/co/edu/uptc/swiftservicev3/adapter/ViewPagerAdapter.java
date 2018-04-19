package co.edu.uptc.swiftservicev3.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

import co.edu.uptc.swiftservicev3.entidades.Platillo;
import co.edu.uptc.swiftservicev3.fragments.Tab_ConsultarPlatilloFragment;
import co.edu.uptc.swiftservicev3.fragments.Tab_EditarPlatilloFragment;
import co.edu.uptc.swiftservicev3.fragments.Tab_EliminarPlatilloFragment;

/**
 * Created by Frank on 8/04/2018.
 */

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> listaFragments = new ArrayList<>();
    private List<String> listaTitulos = new ArrayList<>();


    public  ViewPagerAdapter (FragmentManager fragmentManager){
        super(fragmentManager);
    }

    public void addFragments(Fragment fragment, String titulo){
        listaFragments.add(fragment);
        listaTitulos.add(titulo);

    }



    @Override
    public Fragment getItem(int position) {
        return listaFragments.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position){
        return listaTitulos.get(position);
    }

    @Override
    public int getCount() {
        return listaFragments.size();
    }
}
