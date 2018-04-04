package co.edu.uptc.usuariossqlite.logic;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import co.edu.uptc.usuariossqlite.R;
import co.edu.uptc.usuariossqlite.entidades.Persona;
import co.edu.uptc.usuariossqlite.persistencia.DatabaseHandler;

public class ListaSQLiteActivity extends AppCompatActivity {

    private ListView listaPersonas;
    private Button buttonAgregar;
    private DatabaseHandler db;

    private AdaptadorPersonas adaptadorPersonas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_sqlite);


        this.db = new DatabaseHandler(this);
        this.listaPersonas = (ListView) findViewById(R.id.listPersonas);
        this.buttonAgregar = (Button) findViewById(R.id.btnAgregarPersona);


        this.buttonAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListaSQLiteActivity.this, CrearPersona.class);
                startActivity(intent);
            }
        });

        this.adaptadorPersonas = new AdaptadorPersonas(ListaSQLiteActivity.this,db.getPersonas());

        this.adaptadorPersonas.notifyDataSetChanged();
        listaPersonas.setAdapter(this.adaptadorPersonas);

        listaPersonas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                Persona p = adaptadorPersonas.getItem(position);

                Intent intent = new Intent(ListaSQLiteActivity.this, ModificarPersona.class);

                intent.putExtra("ID",p.getId());

                startActivity(intent);
            }
        });

    }
}
