package co.edu.uptc.swiftservicecliente;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class QR extends AppCompatActivity implements ZXingScannerView.ResultHandler{

    private  ZXingScannerView scannerView;
    private Button saltar;
    int numeroMesa;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr);
        saltar = (Button)findViewById(R.id.btnQR);



        //saltar.setOnClickListener(new View.OnClickListener() {
        //  @Override
        // public void onClick(View v) {
        //escaner(v);
        //QR.this.startActivity(new Intent(QR.this, MainActivity.class));
        //}
        //});

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
            if(!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)){
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA},2);
            }
        }

    }

    public void escaner(View view){
        scannerView = new ZXingScannerView(this);
        setContentView(scannerView);
        scannerView.setResultHandler(this);
        scannerView.startCamera();
    }

    @Override
    protected void onPause() {
        super.onPause();
        scannerView.stopCamera();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void handleResult(Result result) {
        // AlertDialog.Builder mensaje = new AlertDialog.Builder(this);
        // mensaje.setTitle("DAVID CHUPAMELO, SU RESULTADO ES:  ");
        // mensaje.setMessage(result.getText());
        // AlertDialog alertDialog = mensaje.create();
        //alertDialog.show();
        //scannerView.resumeCameraPreview(this);
        if(result.getText().equals("1")) {
           // scannerView.stopCamera();
           // QR.this.startActivity(new Intent(QR.this, MainActivityCliente.class));
            //QR.this.finish();
        }
        if(result.getText().equals("2")) {

            Intent intent = new Intent(QR.this,MainActivity.class);

            scannerView.stopCamera();
            numeroMesa = Integer.parseInt(result.getText());
            Bundle bundle  = new Bundle();
            bundle.putInt("NUMERO_MESA",numeroMesa);

            intent.putExtras(bundle);

            startActivity(intent);
        }
    }


}
