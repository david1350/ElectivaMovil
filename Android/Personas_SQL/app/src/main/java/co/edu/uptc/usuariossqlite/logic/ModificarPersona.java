package co.edu.uptc.usuariossqlite.logic;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import co.edu.uptc.usuariossqlite.entidades.DialogoFecha;
import co.edu.uptc.usuariossqlite.persistencia.DatabaseHandler;
import co.edu.uptc.usuariossqlite.entidades.Persona;
import co.edu.uptc.usuariossqlite.R;


public class ModificarPersona extends AppCompatActivity  {

    private EditText edModificarNombre;
    private EditText edModificarCorreo;
    private EditText edModificarTelefono;
    private EditText edModifcarEdad;

    private Button buttonActualizar;
    private Button buttonEliminar;

    private Validacion validacion;

    private DatabaseHandler db;


    @Override
    protected void onCreate (Bundle saveInstance){
        super.onCreate(saveInstance);
        setContentView(R.layout.modificar_miembro);

        this.db =  new DatabaseHandler(this);

        this.edModificarNombre = (EditText) findViewById(R.id.txtNombreModificar);
        this.edModificarCorreo = (EditText) findViewById(R.id.txtCorreoModificar);
        this.edModificarTelefono = (EditText) findViewById(R.id.txtTelefonoModificar);
        this.edModifcarEdad = (EditText) findViewById(R.id.txtFechaNaModificar);

        this.buttonActualizar = (Button) findViewById(R.id.buttonActualizar);
        this.buttonEliminar = (Button) findViewById(R.id.buttonEliminar);

        this.validacion = new Validacion(this.edModificarNombre, this.edModificarCorreo,this.edModificarTelefono,
                this.edModifcarEdad,
                getString(R.string.errorNombre),
                getString(R.string.errorCorreo),
                getString(R.string.errorTelefono),
                getString(R.string.errorFecha));


        Intent intent = getIntent();
        int idPersona = intent.getIntExtra("ID",-1);

        final Persona persona = db.getPersona(idPersona);

        this.edModificarNombre.setText(persona.getNombre());
        this.edModificarCorreo.setText(persona.getCorreo());
        this.edModificarTelefono.setText(String.valueOf(persona.getTelefono()));
        this.edModifcarEdad.setText(persona.getFecha());

        //ACTUALIZAR REGISTRO.
        this.buttonActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validacion.validacionGeneral()){

                    persona.setNombre(edModificarNombre.getText().toString());
                    persona.setCorreo(edModificarCorreo.getText().toString());
                    persona.setTelefono(edModificarTelefono.getText().toString());
                    persona.setFecha(edModifcarEdad.getText().toString());

                    db.updatePersona(persona);
                    Toast.makeText(getApplicationContext(), "Usuario Actualizado Correctamente", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(ModificarPersona.this, ListaSQLiteActivity.class);
                    startActivity(intent);
                }
            }
        });


        //ELIMINAR REGISTRO.
        this.buttonEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.deleteRegistro(persona);
                Toast.makeText(getApplicationContext(), "Usuario Eliminado Correctamente", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(ModificarPersona.this, ListaSQLiteActivity.class);
                startActivity(intent);
            }
        });

    }


    /**
     * Metodo que permite sacar el calendario cuando se enfoca en el campo.
     */
    public void onStart() {
        super.onStart();
        edModifcarEdad.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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

}
