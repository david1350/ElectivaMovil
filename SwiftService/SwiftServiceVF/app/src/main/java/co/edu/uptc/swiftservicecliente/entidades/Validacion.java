package co.edu.uptc.swiftservicecliente.entidades;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Toast;


public class Validacion extends AppCompatActivity  {


    EditText textNombre, Descripcion, precio;
    String errorNombre,errorDescripcion,errorPrecio;
    String categoria;
    Bitmap bitmap;

    public Validacion(EditText nombre, EditText descripcion, EditText pre, String errorN, String errorD,
                      String errorp, String cat, Bitmap b){
        this.textNombre = nombre;
        this.Descripcion = descripcion;
        this.precio = pre;
        this.errorNombre = errorN;
        this.errorDescripcion = errorD;
        this.errorPrecio = errorp;
        this.categoria = cat;
        this.bitmap = b;


    }


    public  boolean validateRegistro (Context c){

        if (textNombre.getText().toString().length()>1){
            if (Descripcion.getText().toString().length()>1){
                if (categoria!="NULL"){
                    if (precio.getText().toString().length()>1){
                        if (bitmap!=null){
                            return true;
                        }else{
                            Toast.makeText(c, "Debe Agregar o Actualizar Foto" , Toast.LENGTH_SHORT).show();
                            return  false;
                        }

                    }else{
                        precio.setError(errorPrecio);
                        return  false;
                    }

                }else{
                    Toast.makeText(c, "Debe Asignar categoria " , Toast.LENGTH_SHORT).show();
                    return false;
                }
            }else{
                Descripcion.setError(errorDescripcion);
                return false;
            }
        }else{
            textNombre.setError(errorNombre);
            return  false;
        }

    }


}
