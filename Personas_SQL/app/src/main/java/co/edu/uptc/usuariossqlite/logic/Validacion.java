package co.edu.uptc.usuariossqlite.logic;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

import co.edu.uptc.usuariossqlite.R;
import co.edu.uptc.usuariossqlite.entidades.DialogoFecha;
import co.edu.uptc.usuariossqlite.entidades.Persona;

/**
 * Clase que permite manejar las restricciones de los campos de formulario.
 */

public class Validacion extends AppCompatActivity  {

    private EditText txtNombre;
    private EditText txtCorreo;
    private EditText txtTelefono;
    private EditText txtFecha;

    private String errorNombre;
    private String errorCorreo;
    private String errorTelefono;
    private String errorFecha;

    public Validacion(EditText txtNombre, EditText txtCorreo, EditText txtTelefono, EditText txtFecha, String errorNombre, String errorCorreo, String errorTelefono, String errorFecha) {
        this.txtNombre = txtNombre;
        this.txtCorreo = txtCorreo;
        this.txtTelefono = txtTelefono;
        this.txtFecha = txtFecha;
        this.errorNombre = errorNombre;
        this.errorCorreo = errorCorreo;
        this.errorTelefono = errorTelefono;
        this.errorFecha = errorFecha;

        SimpleMaskFormatter smf = new SimpleMaskFormatter("(NN)NNNN-NNNN");
        MaskTextWatcher mtw = new MaskTextWatcher(txtTelefono,smf);
        txtTelefono.addTextChangedListener(mtw);
    }

    /**
     * Metodo que permite sacar el calendario cuando se enfoca en el campo.
     */
    public void onStart() {
        super.onStart();
        txtFecha.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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
     * Metodo que se encarga de la validacionGeneral.
     */
    public boolean  validacionGeneral (){

        if (txtNombre.getText().toString().length() >= 1) {
            if (Patterns.EMAIL_ADDRESS.matcher(txtCorreo.getText().toString()).matches()==true) {
                if (txtTelefono.getText().toString().length()==13) {
                    if (txtFecha.getText().toString().length()>=1) {
                            return true;
                    } else {
                        txtFecha.setError(errorFecha);
                        return false;
                    }
                } else {
                    txtTelefono.setError(errorTelefono);
                    return false;
                }
            } else {
                txtCorreo.setError(errorCorreo);
                return false;
            }
        } else {
            txtNombre.setError(errorNombre);
            return false;
        }
    }






}
