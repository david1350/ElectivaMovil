package co.edu.uptc.primerformulario;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Pattern;

/**
 * Created by Frank on 6/03/2018.
 */

public class Validacion extends AppCompatActivity  {



    /**
     * Metodo que permite validar que el email sea correcto.
     * @param txtEmail
     * @return
     */
    public boolean validateEmail (EditText txtEmail){

        if (Patterns.EMAIL_ADDRESS.matcher(txtEmail.getText().toString()).matches()==false) {
            return false;
        }
        return true;
    }


    /**
     * Metodo que permite validar que el campo telefono sea correcto.
     * @param txtPhone
     * @return
     */
    public boolean validatePhone (EditText txtPhone){

        if (Patterns.PHONE.matcher(txtPhone.getText().toString()).matches()==false) {
            return false;
        }
        return true;
    }

    public boolean validateDate (EditText txtDate){
       return true;
    }

    public boolean validateLibreta (EditText txtLibreta){
        if(txtLibreta.getText().toString().length()>=0){
            return true;
        }else{
            return false;
        }
    }


}
