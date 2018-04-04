package co.edu.uptc.usuariossqlite.logic;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import co.edu.uptc.usuariossqlite.R;

public class PantallaPrincipal extends AppCompatActivity {


    private Button buttonCrear;
    private Button buttonUsuarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        buttonCrear = (Button) findViewById(R.id.buttonCrear);
        buttonUsuarios = (Button) findViewById(R.id.buttonUsuarios);

        escucharButtonCrear(buttonCrear);
        escucharButtonListar(buttonUsuarios);

    }


    public void escucharButtonCrear (Button btnAceptar){

        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //se crea el Intent, para redireccionar al otro activity.

                Intent intent = new Intent(PantallaPrincipal.this, CrearPersona.class);

                startActivity(intent);


            }
        });
    }

    public void escucharButtonListar (Button btnListar){

        btnListar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //se crea el Intent, para redireccionar al otro activity.

                Intent intent = new Intent(PantallaPrincipal.this, ListaSQLiteActivity.class);

                startActivity(intent);

            }
        });
    }


}
