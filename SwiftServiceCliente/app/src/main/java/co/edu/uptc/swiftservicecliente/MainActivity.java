package co.edu.uptc.swiftservicecliente;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import java.util.ArrayList;

import co.edu.uptc.swiftservicecliente.entidades.Platillo;
import co.edu.uptc.swiftservicecliente.entidades.Servicio;
import co.edu.uptc.swiftservicecliente.fragments.CategoriaPlatilloFragment;
import co.edu.uptc.swiftservicecliente.fragments.InicioFragment;
import co.edu.uptc.swiftservicecliente.fragments.ListarPlatillosImagenFragment;
import co.edu.uptc.swiftservicecliente.fragments.PedidoFragment;
import co.edu.uptc.swiftservicecliente.fragments.PedidosHechosFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private ArrayList<Platillo> listaPlatillos;
    private ArrayList<Platillo> listaPlatillosOrden;
    private Servicio servicio;
    private int numeroMesa;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle  bundle = this.getIntent().getExtras();
        this.numeroMesa =  bundle.getInt("NUMERO_MESA");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.buttonComprar);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.contentPrincipal, new PedidosHechosFragment(numeroMesa)).commit();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.contentPrincipal, new InicioFragment()).commit();


        this.servicio = new Servicio(this);
        this.listaPlatillos  = this.servicio.getListaPlatillos();
        this.listaPlatillosOrden = new ArrayList<>();




    }

    public void actualizarPlatillos (){
        this.listaPlatillos.clear();
        this.listaPlatillos  = this.servicio.getListaPlatillos();
        Toast.makeText(this,"Platos Actualizados", Toast.LENGTH_SHORT).show();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_update) {
           actualizarPlatillos();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();

        if (id == R.id.nav_Inicio) {
            fragmentManager.beginTransaction().replace(R.id.contentPrincipal,new InicioFragment()).commit();
        }else if (id == R.id.nav_ListarPlatos) {
            fragmentManager.beginTransaction().replace(R.id.contentPrincipal,new ListarPlatillosImagenFragment(listaPlatillosOrden,this.listaPlatillos)).commit();
        }else if (id == R.id.nav_CategoriaPlatillos) {
            fragmentManager.beginTransaction().replace(R.id.contentPrincipal,
                    new CategoriaPlatilloFragment(listaPlatillosOrden,
                            getPlatillosPrincipales(),getPlatillosEnsaladas(),getPlatillosPostres())).commit();
        }else if (id == R.id.nav_ListarOrden) {
            fragmentManager.beginTransaction().replace(R.id.contentPrincipal,new PedidoFragment(listaPlatillosOrden,numeroMesa )).commit();
        }else if (id == R.id.nav_ListarOrdenes) {
            fragmentManager.beginTransaction().replace(R.id.contentPrincipal,new PedidosHechosFragment(numeroMesa)).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    public ArrayList<Platillo> getListaPlatillos() {
        return listaPlatillos;
    }

    public ArrayList<Platillo> getPlatillosPrincipales (){
        ArrayList<Platillo> lista = new ArrayList<>();
        for (int i = 0 ; i<listaPlatillos.size(); i++){
            Platillo p = listaPlatillos.get(i);
            if ("platilloPrincipal".equals(p.getCategoria())){
                lista.add(p);
            }
        }
        return  lista;
    }

    public ArrayList<Platillo> getPlatillosEnsaladas (){
        ArrayList<Platillo> lista = new ArrayList<>();
        for (int i = 0 ; i<listaPlatillos.size(); i++){
            Platillo p = listaPlatillos.get(i);
            if ("ensalada".equals(p.getCategoria())){
                lista.add(p);
            }
        }
        return  lista;
    }

    public ArrayList<Platillo> getPlatillosPostres (){
        ArrayList<Platillo> lista = new ArrayList<>();
        for (int i = 0 ; i<listaPlatillos.size(); i++){
            Platillo p = listaPlatillos.get(i);
            if ("postre".equals(p.getCategoria())){
                lista.add(p);
            }
        }
        return  lista;
    }



}
