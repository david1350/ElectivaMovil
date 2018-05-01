package co.edu.uptc.swiftservicecliente.fragments.administrador;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

    import co.edu.uptc.swiftservicecliente.R;
import co.edu.uptc.swiftservicecliente.entidades.Validacion;


public class RegistrarPlatilloFragment extends Fragment {

    private static final String CARPETA_PRINCIPAL = "misImagenesApp/";//directorio principal
    private static final String CARPETA_IMAGEN = "imagenes";//carpeta donde se guardan las fotos
    private static final String DIRECTORIO_IMAGEN = CARPETA_PRINCIPAL + CARPETA_IMAGEN;//ruta carpeta de directorios
    private String path;//almacena la ruta de la imagen
    File fileImagen;
    Bitmap bitmap;

    private final int MIS_PERMISOS = 100;
    private static final int COD_SELECCIONA = 10;
    private static final int COD_FOTO = 20;


    EditText  campoNombre, camponDescripcion,campoPrecio;
    ImageView imagenView;
    RadioGroup radioGroup;
    String categoria;
    Button buttonRegistro, buttonFoto;
    StringRequest stringRequest;
    ProgressDialog progreso;
    JsonObjectRequest jsonObjectRequest;
    RequestQueue request;
    Validacion validacion;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_registro_platillo, container, false);
        this.campoNombre = (EditText) v.findViewById(R.id.campoNombre);
        this.camponDescripcion = (EditText) v.findViewById(R.id.campoDescripcion);
        this.campoPrecio = (EditText) v.findViewById(R.id.campoPrecio);
        this.radioGroup = (RadioGroup) v.findViewById(R.id.rbCategoria);
        this.categoria = "NULL";

        this.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId){
                    case R.id.rbEnsalada:
                        categoria  = "ensalada";
                        break;
                    case R.id.rbPlatilloPrincipal:
                        categoria = "platilloPrincipal";
                        break;
                    case R.id.rbPostre:
                        categoria = "postre";
                        break;
                    default:
                        categoria = "null";
                        break;

                }
            }
        });

        this.imagenView = (ImageView)v.findViewById(R.id.imgFoto);
        this.buttonFoto = (Button) v.findViewById(R.id.btnFoto);


        this.buttonRegistro = (Button) v.findViewById(R.id.btnRegistrar);

        request = Volley.newRequestQueue(getContext());

        this.buttonRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                realizarServicio();
            }
        });
        this.buttonFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarDialogOpciones();
            }
        });

        return v;
    }


    private void mostrarDialogOpciones() {
        final CharSequence[] opciones={"Tomar Foto","Elegir de Galeria","Cancelar"};
        final AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
        builder.setTitle("Elige una Opción");
        builder.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (opciones[i].equals("Tomar Foto")){
                    abriCamara();
                } else if (opciones[i].equals("Elegir de Galeria")){
                    Intent intent=new Intent(Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/");
                    startActivityForResult(intent.createChooser(intent,"Seleccione"),COD_SELECCIONA);
                }else{
                    dialogInterface.dismiss();
                }
            }
        });
        builder.show();
    }


    /**
     * metodo para tomar foto.
     */
    private void abriCamara() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, COD_FOTO);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (data!=null){
            switch (requestCode) {
                case COD_SELECCIONA:
                    Uri miPath = data.getData();
                    imagenView.setImageURI(miPath);

                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(),miPath);
                        imagenView.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    break;
                case COD_FOTO:
                    Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                    bitmap = thumbnail;
                    imagenView.setImageBitmap(bitmap);
                    break;
            }
        }


        bitmap = redimensionarImagen(bitmap, 200,300);
    }

    private Bitmap redimensionarImagen(Bitmap bitmap, float anchoNuevo, float altoNuevo) {

        if (bitmap!=null){
            int ancho = bitmap.getWidth();
            int alto = bitmap.getHeight();

            if (ancho>anchoNuevo || alto>altoNuevo){
                float escalaAncho = anchoNuevo/ancho;
                float escalaAlto = altoNuevo/alto;
                Matrix matrix = new Matrix();
                matrix.postScale(escalaAncho,escalaAlto);

                return  Bitmap.createBitmap(bitmap,0,0,ancho,alto,matrix,false);
            }else{
                return  bitmap;
            }
        }
        return null;
    }






    private void cargarWebService() {

        progreso = new ProgressDialog(getContext());
        progreso.setMessage("Cargando...");
        progreso.show();

        String ip = getString(R.string.ip);
        String url = ip+"/BDremotaSwiftService/wsJSONRegistroMovil.php?";

        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progreso.hide();

                if (response.trim().equalsIgnoreCase("registra")){
                    campoNombre.setText("");
                    camponDescripcion.setText("");
                    radioGroup.clearCheck();
                    campoPrecio.setText("");
                    imagenView.setImageResource(R.drawable.img_base);

                    Toast.makeText(getContext(),"Se ha registrado con exito",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getContext(),"No_se_pudo_registrar",Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.err.println("erorrrrrrrrrrrrrrrrrrrrrrr"  + error) ;
                Toast.makeText(getContext(),"No_se_puede_conectar",Toast.LENGTH_SHORT).show();
                progreso.hide();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {


                String nombre = campoNombre.getText().toString();
                String descripcion = camponDescripcion.getText().toString();
                String catego = categoria;
                String precio = campoPrecio.getText().toString();
                String imagen = convertirImgString(bitmap);
                Map<String,String> parametros = new HashMap<>();



                parametros.put("nombre",nombre);
                parametros.put("descripcion",descripcion);
                parametros.put("categoria",catego);
                parametros.put("imagen",imagen);
                parametros.put("precio",precio);

                return parametros;
            }
        };
        request.add(stringRequest);

    }

    private String convertirImgString(Bitmap bitmap) {
        ByteArrayOutputStream array=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,80,array);
        byte[] imagenByte=array.toByteArray();
        String imagenString= Base64.encodeToString(imagenByte,Base64.DEFAULT);

        return imagenString;
    }



    public void realizarServicio (){
        this.validacion = new Validacion(campoNombre,camponDescripcion,campoPrecio,
                getString(R.string.errorNombre),getString(R.string.errorDescripcion),
                getString(R.string.errorPrecio),categoria, bitmap);

        if (validacion.validateRegistro(getContext())){
            cargarWebService();
        }else{

        }
    }




}