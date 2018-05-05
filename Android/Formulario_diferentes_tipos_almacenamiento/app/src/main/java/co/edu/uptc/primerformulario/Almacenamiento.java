package co.edu.uptc.primerformulario;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.EditText;
import android.widget.RadioButton;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by Frank on 12/03/2018.
 */

public class Almacenamiento {

    private EditText txtNombre;
    private EditText txtCorreo;
    private EditText txtTelefono;
    private EditText txtFechaNac;
    private RadioButton rbMasculino;
    private RadioButton rbFemenino;
    private EditText txtLibreta;


    public Almacenamiento (EditText etName, EditText etMail, EditText eTelefono, EditText txtFechaNac,
                           RadioButton rbmasculino, RadioButton rbfemenino, EditText lib){
        this.txtNombre = etName;
        this.txtCorreo = etMail;
        this.txtTelefono = eTelefono;
        this.txtFechaNac = txtFechaNac;
        this.rbMasculino = rbmasculino;
        this.rbFemenino = rbfemenino;
        this.txtLibreta = lib;

    }

    /**
     * Metodo que realiza el almacenamiento por archivo XML.
     * @param mypreferences
     */
    public void almacenamientoPorArchivos (SharedPreferences mypreferences){
        SharedPreferences.Editor myEditor =   mypreferences.edit();
        myEditor.putString("nombre", txtNombre.getText().toString());
        myEditor.putString("correo",txtCorreo.getText().toString());
        myEditor.putString("telefono", txtTelefono.getText().toString());
        myEditor.putString("fechaNac",txtFechaNac.getText().toString());

        if (rbMasculino.isChecked()){
            myEditor.putString("genero", "masculino");
            myEditor.putString("Num_LibretaMilitar", txtLibreta.getText().toString());
        }else if (rbFemenino.isChecked()){
            myEditor.putString("genero","femenino");
        }else{
            myEditor.putString("genero","No_establecido");
        }

        myEditor.commit();
    }

    /**
     * Metodo que permite el almacenamiento interno creando un archivo.
     * @param f
     */
    public void almacenamientoInterno(File f){
        File example = new File(f,"ejemplo.csv");
        try {
            FileOutputStream fos = new FileOutputStream(example,false);

            String opcionales;

            if (rbMasculino.isChecked()){
                opcionales = "genero: masculino" +"\n"+
                             "libretaMilitar" + txtLibreta.getText().toString();
            }else if (rbFemenino.isChecked()){
                opcionales = "genero: femenino" +"\n";
            }else{
               opcionales = "genero no establecido";
            }

            String datos = "nombre: " + txtNombre.getText().toString()+" "+"\n" +
                           "email: " + txtCorreo.getText().toString()+" "+"\n" +
                           "telefono: " + txtTelefono.getText().toString()+" "+"\n"+
                           "fechaNacimiento: " + txtTelefono.getText().toString()+" "+"\n"
                             + opcionales;

            fos.write(datos.getBytes());
            fos.close();
        }catch (Exception e){
        }
    }

}
