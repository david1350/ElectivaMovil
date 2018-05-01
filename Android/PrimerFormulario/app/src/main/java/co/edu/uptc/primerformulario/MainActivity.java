package co.edu.uptc.primerformulario;

import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener{



    private EditText txtNombre;
    private EditText txtCorreo;
    private EditText txtTelefono;
    private EditText txtFechaNac;
    private RadioButton rbMasculino;
    private RadioButton rbFemenino;
    private RadioGroup radioGroup;
    private Button  btnGuardar;
    private EditText txtLibreta;

    private Spinner spinnerAlmacenar;
    private String [] itemsArrreglo;

    private Validacion validacion;
    private Almacenamiento almacenamiento;

    SharedPreferences myprePreferences;

    File internalStorage;

    DatabaseHandler db;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        validacion = new Validacion();

        //ARCHIVO DE PREFERENCIAS
        myprePreferences= PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        //ALMACENAMIENTO INTERNO
        internalStorage = getFilesDir();
        //BASE DE DATOS
        db = new DatabaseHandler(this);

        txtNombre  = (EditText)findViewById(R.id.txtNombre);
        txtCorreo = (EditText)findViewById(R.id.txtCorreo);
        txtTelefono = (EditText)findViewById(R.id.txtTelefono);
        txtFechaNac = (EditText)findViewById(R.id.txtFechaNac);
        rbMasculino = (RadioButton)findViewById(R.id.radButMasculino);
        rbFemenino = (RadioButton)findViewById(R.id.radButFemenino);
        radioGroup = (RadioGroup)findViewById(R.id.radGroupButtonGenero);
        txtLibreta = (EditText)findViewById(R.id.txtLibreta);
        btnGuardar = (Button)findViewById(R.id.btnGuardar);

        almacenamiento = new Almacenamiento(txtNombre,txtCorreo,txtTelefono,txtFechaNac,rbMasculino,rbFemenino,txtLibreta);

        spinnerAlmacenar = (Spinner)findViewById(R.id.spinnerAlmacenar);
        itemsArrreglo = getResources().getStringArray(R.array.opciones_almacenamiento);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getBaseContext(),android.R.layout.simple_spinner_item, itemsArrreglo);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAlmacenar.setAdapter(adapter);

        radioGroup.setOnCheckedChangeListener(this);

    }

    /**
     * Metodo que permite sacar el calendario cuando se enfoca en el campo.
     */
    public  void onStart(){
        super.onStart();
        txtFechaNac.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    DateDialog dialog = new DateDialog(v);
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    dialog.show(ft,"DatePicker");
                }
            }
        });

        if (rbMasculino.isChecked()==true){
            txtLibreta.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Metodo para validar Campos con la clase validacion.
     * @param view
     */
    public void validateFields (View view){

        if (txtNombre.getText().toString().length() >= 1) {
            if (validacion.validateEmail(txtCorreo)){
                if (validacion.validatePhone(txtTelefono)){
                    if (validacion.validateDate(txtFechaNac)){
                        if (spinnerAlmacenar.getSelectedItemPosition()==0){
                            almacenamiento.almacenamientoPorArchivos(myprePreferences);
                            Toast.makeText(getApplicationContext(),"Información por Archivo Guardada",Toast.LENGTH_LONG).show();
                        }else if (spinnerAlmacenar.getSelectedItemPosition()==1){
                            almacenamiento.almacenamientoInterno(internalStorage);
                            Toast.makeText(getApplicationContext(),"Información por Almacenamiento Interno Guardada",Toast.LENGTH_LONG).show();
                        }else {
                            Registro r = new Registro();
                            r.setNombre(txtNombre.getText().toString());
                            r.setCorreo(txtCorreo.getText().toString());
                            db.addRegistro(r);
                            Toast.makeText(getApplicationContext(),"Información en SQlite Guardada",Toast.LENGTH_LONG).show();
                        }

                    }else{
                        txtFechaNac.setError(getString(R.string.errorFecha));
                    }
                }else{
                    txtTelefono.setError(getString(R.string.errorTelefono));
                }
            }else{
                txtCorreo.setError(getString(R.string.errorCorreo));
            }
        } else{
            txtNombre.setError(getString(R.string.errorNombre));
        }

    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (rbMasculino.isChecked()){
            txtLibreta.setVisibility(View.VISIBLE);
        }
        if (rbFemenino.isChecked()){
            txtLibreta.setVisibility(View.GONE);
        }
    }

/**
 *
 * EJEMPLO DE INSERCION EN LA BD.
    public void ejemplo (){
        Registro r = new Registro();
        r.setNombre("francisco");
        r.setCorreo("f@u");
       // r.setTelefono(123);
        //r.setFecha(321);

        db.addRegistro(r);

        Registro r2 = new Registro();
        r2.setNombre("camilo");
        r2.setCorreo("c@u");
        //r2.setTelefono(111);
        //r2.setFecha(222);

        db.addRegistro(r2);


        List<Registro> registros = db.getRegistros();

        for (Registro reg: registros){
            String log = "Id: " + reg.getId() + "Nombre: " + reg.getNombre();

            Log.d("Registro: ",log);
        }

    }
 **/



}
