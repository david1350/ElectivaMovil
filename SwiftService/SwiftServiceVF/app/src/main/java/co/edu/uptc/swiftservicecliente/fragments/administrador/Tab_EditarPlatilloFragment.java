package co.edu.uptc.swiftservicecliente.fragments.administrador;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import co.edu.uptc.swiftservicecliente.R;
import co.edu.uptc.swiftservicecliente.entidades.Platillo;
import co.edu.uptc.swiftservicecliente.entidades.Validacion;


public class Tab_EditarPlatilloFragment extends Fragment implements Response.Listener<JSONObject>,Response.ErrorListener {


    private static final String IMAGE_DIRECTORY = "/AppCamara";//directorio principal
    private int GALLERY = 1,CAMARA = 2;

    Bitmap bitmap;


    TextView campoEditarID;
    EditText campoEditarNombre, campoEditarDesc,campoEditarPrecio;
    RadioGroup radioGroupCategoria;
    ImageView imagenEditar;
    Button buttonBuscarEditar, buttonEditarFoto,buttonActualizar;
    RequestQueue request;
    StringRequest stringRequest;
    JsonObjectRequest jsonObjectRequest;
    ProgressDialog progreso;
    String categoria;

    Validacion validacion;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_tab_editar_platillo, container, false);

        this.campoEditarID = (TextView) v.findViewById(R.id.campoEditarID);
        this.campoEditarNombre = (EditText) v.findViewById(R.id.campoEditarNombre);
        this.campoEditarDesc = (EditText) v.findViewById(R.id.campoEditarDescripcion);
        this.campoEditarPrecio = (EditText)v.findViewById(R.id.campoEditarPrecio);
        this.imagenEditar = (ImageView)v.findViewById(R.id.imgEditarFoto);

        this.buttonBuscarEditar = (Button) v.findViewById(R.id.btnBuscarIdEditar);
        this.buttonEditarFoto = (Button)v.findViewById(R.id.btnEditarFoto);
        this.buttonActualizar = (Button) v.findViewById(R.id.btnActualizarPlatillo);

        this.radioGroupCategoria = (RadioGroup) v.findViewById(R.id.rbEditarCategoria);

        this.buttonBuscarEditar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                cargarWebService();
            }
        });
        this.buttonEditarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarDialogOpciones();
            }
        });
        this.buttonActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               realizarServicio();
            }
        });
        this.radioGroupCategoria.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId){
                    case R.id.rbEEnsalada:
                        categoria  = "ensalada";
                        break;
                    case R.id.rbEPlatilloPrincipal:
                        categoria = "platilloPrincipal";
                        break;
                    case R.id.rbEPostre:
                        categoria = "postre";
                        break;
                    default:
                        categoria = "null";
                        break;

                }
            }
        });

        request = Volley.newRequestQueue(getContext());

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
                    elegirdeGaleria();
                }else{
                    dialogInterface.dismiss();
                }
            }
        });
        builder.show();
    }


    private void abriCamara() {
        Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,CAMARA);
    }

    private void elegirdeGaleria (){
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, GALLERY);
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(data!=null) {

            if (requestCode ==GALLERY){
                if (data !=null){
                    Uri  contentUri = data.getData();
                    imagenEditar.setImageURI(contentUri);
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), contentUri);
                        imagenEditar.setImageBitmap(bitmap);

                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }
            }else if (requestCode == CAMARA ){
                Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                bitmap = thumbnail;
                imagenEditar.setImageBitmap(bitmap);
            }
            bitmap = redimensionarImagen(bitmap, 400, 600);
        }

    }

    private Bitmap redimensionarImagen(Bitmap bitmap, float anchoNuevo, float altoNuevo) {

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

    private void cargarWebService() {

        progreso = new ProgressDialog(getContext());
        progreso.setMessage("Consultando...");
        progreso.show();
        String ip = getString(R.string.ip);
        String url = ip+"/BDremotaSwiftService/wsJSONConsultarRegistroImagen.php?id_platillo="+
                campoEditarID.getText().toString();
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        request.add(jsonObjectRequest);

    }


    @Override
    public void onErrorResponse(VolleyError error) {
        progreso.hide();
        System.err.println("eeeee " + error.toString());
        Toast.makeText(getContext(),"No se ha podido CONSULTAR" + error.toString(),Toast.LENGTH_SHORT).show();
        Log.i("error",error.toString());

    }

    @Override
    public void onResponse(JSONObject response) {
        progreso.hide();
        Platillo miPlatillo = new Platillo();

        JSONArray json = response.optJSONArray("platillo");
        JSONObject jsonObject = null;

        try {
            jsonObject = json.getJSONObject(0);
            miPlatillo.setID(jsonObject.optInt("id_platillo"));
            miPlatillo.setNombre(jsonObject.optString("nombre"));
            miPlatillo.setDescripcion(jsonObject.optString("descripcion"));
            miPlatillo.setCategoria(jsonObject.optString("categoria"));
            miPlatillo.setMetadataImagen(jsonObject.optString("imagen"));
            miPlatillo.setPrecio(jsonObject.optInt("precio"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        campoEditarNombre.setText(miPlatillo.getNombre());
        campoEditarDesc.setText(miPlatillo.getDescripcion());

        switch (miPlatillo.getCategoria()){
            case "ensalada":
                radioGroupCategoria.check(R.id.rbEEnsalada);
                break;
            case "platilloPrincipal":
                radioGroupCategoria.check(R.id.rbEPlatilloPrincipal);
                break;
            case "postre":
                radioGroupCategoria.check(R.id.rbEPostre);
                break;
        }

        campoEditarPrecio.setText(miPlatillo.getPrecio().toString());

        if (miPlatillo.getImagen()!=null){
            imagenEditar.setImageBitmap(miPlatillo.getImagen());
        }else{
            imagenEditar.setImageResource(R.drawable.img_base);
        }

    }


    private void actualizarWebService() {
        progreso = new ProgressDialog(getContext());
        progreso.setMessage("Cargando...");
        progreso.show();

        String ip = getString(R.string.ip);
        String url =ip+"/BDremotaSwiftService/wsJSONUpdateMovil.php?";

        stringRequest  = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progreso.hide();

                if (response.trim().equalsIgnoreCase("actualiza")) {
                    campoEditarID.setText("");
                    campoEditarNombre.setText("");
                    campoEditarDesc.setText("");
                    radioGroupCategoria.clearCheck();
                    campoEditarPrecio.setText("");
                    imagenEditar.setImageResource(R.drawable.img_base);

                    Toast.makeText(getContext(), "Se ha actualizado correctamente", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "No se pudo actualizar", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "No se ha podido conectar", Toast.LENGTH_SHORT).show();
                progreso.hide();
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                String ID = campoEditarID.getText().toString();
                String nombre = campoEditarNombre.getText().toString();
                String descripcion = campoEditarDesc.getText().toString();
                String imagen = convertirImgString(bitmap);
                String precio = campoEditarPrecio.getText().toString();

                Map<String,String> parametros = new HashMap<>();
                parametros.put("id_platillo",ID);
                parametros.put("nombre",nombre);
                parametros.put("descripcion",descripcion);
                parametros.put("categoria",categoria);
                parametros.put("imagen",imagen);
                parametros.put("precio",precio);

                return  parametros;

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
        this.validacion = new Validacion(campoEditarNombre,campoEditarDesc,campoEditarPrecio,
                getString(R.string.errorNombre),getString(R.string.errorDescripcion),
                getString(R.string.errorPrecio),categoria, bitmap);

        if (campoEditarID.getText().toString().length()>=1){
            if (!campoEditarNombre.getText().toString().equals("no registra")){
                if (validacion.validateRegistro(getContext())){
                    actualizarWebService();
                }
            }else{
                Toast.makeText(getContext(),"Platillo No existe ",Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(getContext(),"Debe indicar un Identificador de platillo ",Toast.LENGTH_SHORT).show();
        }


    }


}
