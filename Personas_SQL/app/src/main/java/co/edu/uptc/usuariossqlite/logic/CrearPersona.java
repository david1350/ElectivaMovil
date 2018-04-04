package co.edu.uptc.usuariossqlite.logic;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

import co.edu.uptc.usuariossqlite.persistencia.DatabaseHandler;
import co.edu.uptc.usuariossqlite.entidades.DialogoFecha;
import co.edu.uptc.usuariossqlite.entidades.Persona;
import co.edu.uptc.usuariossqlite.R;


public class CrearPersona extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {


    private EditText txtNombre;
    private EditText txtCorreo;
    private EditText txtTelefono;
    private EditText txtFechaNac;
    private RadioButton rbMasculino;
    private RadioButton rbFemenino;
    private RadioGroup radioGroup;
    private Button btnGuardar;
    private Button btnCancelar;


    private Validacion validacion;


    DatabaseHandler db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

        //BASE DE DATOS
        db = new DatabaseHandler(this);

        txtNombre = (EditText) findViewById(R.id.txtNombre);
        txtCorreo = (EditText) findViewById(R.id.txtCorreo);
        txtTelefono = (EditText) findViewById(R.id.txtTelefono);
        txtFechaNac = (EditText) findViewById(R.id.txtFechaNac);
        rbMasculino = (RadioButton) findViewById(R.id.radButMasculino);
        rbFemenino = (RadioButton) findViewById(R.id.radButFemenino);
        radioGroup = (RadioGroup) findViewById(R.id.radGroupButtonGenero);
        btnGuardar = (Button) findViewById(R.id.buttonGuardar);
        btnCancelar = (Button) findViewById(R.id.buttonCancelar);


        //clase de validaciones.
        validacion = new Validacion(this.txtNombre,this.txtCorreo,this.txtTelefono,this.txtFechaNac,
                getString(R.string.errorNombre),
                getString(R.string.errorCorreo),
                getString(R.string.errorTelefono),
                getString(R.string.errorFecha));


        radioGroup.setOnCheckedChangeListener(this);
        escucharButtonCancelar(btnCancelar);

    }

    /**
     * Metodo que permite sacar el calendario cuando se enfoca en el campo.
     */
    public void onStart() {
        super.onStart();
        txtFechaNac.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                     DialogoFecha dialog = new DialogoFecha(v);
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    dialog.show(ft, "DatePicker");
                }
            }
        });

    }

    /**
     * Metodo para validar Campos con la clase validacion.
     *
     * @param view
     */
    public void validarCamposGuardar(View view) {

        if (validacion.validacionGeneral()){
            Persona r = new Persona();
            r.setNombre(txtNombre.getText().toString());
            r.setCorreo(txtCorreo.getText().toString());
            r.setTelefono(txtTelefono.getText().toString());
            r.setFecha(txtFechaNac.getText().toString());
            db.addPersona(r);
            Toast.makeText(getApplicationContext(), "Información en SQlite Guardada", Toast.LENGTH_LONG).show();

            Intent intent = new Intent(CrearPersona.this, ListaSQLiteActivity.class);
            startActivity(intent);
        }

    }


    public void escucharButtonCancelar (Button buttonCancelar){

        buttonCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //se crea el Intent, para redireccionar al otro activity.

                Intent intent = new Intent(CrearPersona.this, PantallaPrincipal.class);

                startActivity(intent);

            }
        });
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

    }
}

