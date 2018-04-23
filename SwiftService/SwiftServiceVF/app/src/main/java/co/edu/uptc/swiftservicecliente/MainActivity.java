package co.edu.uptc.swiftservicecliente;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import co.edu.uptc.swiftservicecliente.fragments.AcercaDeFragment;
import co.edu.uptc.swiftservicecliente.fragments.administrador.ConsultaPedidosFragment;
import co.edu.uptc.swiftservicecliente.fragments.administrador.ConsultarPlatilloFragment;
import co.edu.uptc.swiftservicecliente.fragments.administrador.InicioFragment;
import co.edu.uptc.swiftservicecliente.fragments.administrador.ListarPlatillosImagenFragment;
import co.edu.uptc.swiftservicecliente.fragments.administrador.RegistrarPlatilloFragment;

public class MainActivity extends AppCompatActivity

        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_viewA);
        navigationView.setNavigationItemSelectedListener(this);

        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.contentPrincipal, new InicioFragment()).commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();

        if (id == R.id.nav_Inicio) {
            fragmentManager.beginTransaction().replace(R.id.contentPrincipal,new InicioFragment()).commit();
        } else if (id == R.id.nav_RegistrarPlato) {
            fragmentManager.beginTransaction().replace(R.id.contentPrincipal,new RegistrarPlatilloFragment()).commit();
        }
        else if (id == R.id.nav_ConsultarPlato) {
            fragmentManager.beginTransaction().replace(R.id.contentPrincipal,new ConsultarPlatilloFragment()).commit();
        } else if (id == R.id.nav_ListarPlatos) {
            fragmentManager.beginTransaction().replace(R.id.contentPrincipal,new ListarPlatillosImagenFragment()).commit();
        } else if (id == R.id.nav_Acerca_De) {
            fragmentManager.beginTransaction().replace(R.id.contentPrincipal,new AcercaDeFragment()).commit();
        }else if (id == R.id.nav_ListarOrdenes) {
            fragmentManager.beginTransaction().replace(R.id.contentPrincipal,new ConsultaPedidosFragment()).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }


}
