package co.edu.uptc.swiftservicecliente.adaptaer;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Adaptador general
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
