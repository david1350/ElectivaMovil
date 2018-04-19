package co.edu.uptc.swiftservicev3.fragments;

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

import co.edu.uptc.swiftservicev3.R;
import co.edu.uptc.swiftservicev3.entidades.Platillo;
import co.edu.uptc.swiftservicev3.entidades.Validacion;


public class Tab_EditarPlatilloFragment extends Fragment implements Response.Listener<JSONObject>,Response.ErrorListener {


    private static final String CARPETA_PRINCIPAL = "misImagenesApp/";//directorio principal
    private static final String CARPETA_IMAGEN = "imagenes";//carpeta donde se guardan las fotos
    private static final String DIRECTORIO_IMAGEN = CARPETA_PRINCIPAL + CARPETA_IMAGEN;//ruta carpeta de directorios
    private String path;//almacena la ruta de la imagen
    File fileImagen;
    Bitmap bitmap;

    private final int MIS_PERMISOS = 100;
    private static final int COD_SELECCIONA = 10;
    private static final int COD_FOTO = 20;

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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case COD_SELECCIONA:
                Uri miPath = data.getData();
                imagenEditar.setImageURI(miPath);

                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(),miPath);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                break;
            case COD_FOTO:
                MediaScannerConnection.scanFile(getContext(), new String[]{path}, null,
                        new MediaScannerConnection.OnScanCompletedListener() {
                            @Override
                            public void onScanCompleted(String path, Uri uri) {
                                Log.i("Path",""+path);
                            }
                        });

                bitmap= BitmapFactory.decodeFile(path);
                imagenEditar.setImageBitmap(bitmap);

                break;
        }

        bitmap = redimensionarImagen(bitmap, 400,600);
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

    private void abriCamara() {
        File miFile=new File(Environment.getExternalStorageDirectory(),DIRECTORIO_IMAGEN);
        boolean isCreada=miFile.exists();

        if(isCreada==false){
            isCreada=miFile.mkdirs();
        }

        if(isCreada==true){
            Long consecutivo= System.currentTimeMillis()/1000;
            String nombre=consecutivo.toString()+".jpg";

            path=Environment.getExternalStorageDirectory()+File.separator+DIRECTORIO_IMAGEN
                    +File.separator+nombre;//indicamos la ruta de almacenamiento

            fileImagen=new File(path);

            Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(fileImagen));
            startActivityForResult(intent,COD_FOTO);

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

        if (validacion.validateRegistro(getContext())){
           actualizarWebService();
        }else{

        }
    }


}
