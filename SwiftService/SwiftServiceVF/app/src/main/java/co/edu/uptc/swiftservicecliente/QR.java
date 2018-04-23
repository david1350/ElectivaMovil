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
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.Result;

import co.edu.uptc.swiftservicecliente.fragments.administrador.LoginAcitvity;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class QR extends AppCompatActivity implements ZXingScannerView.ResultHandler{

    private  ZXingScannerView scannerView;
    private Button saltar;
    int numeroMesa;
    private TextView admin;
    private View view;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr);
        saltar = (Button)findViewById(R.id.btnQR);
        admin = (TextView) findViewById(R.id.adminQR);


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
            if(!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)){
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA},2);
            }
        }


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            if(!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
            }
        }


        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QR.this.startActivity(new Intent(QR.this, LoginAcitvity.class));

            }
        });


    }


    public void esc(){
        scannerView = new ZXingScannerView(this);
        setContentView(scannerView);
        scannerView.setResultHandler(this);
        scannerView.startCamera();
    }

    public void escaner(View view){
        scannerView = new ZXingScannerView(this);
        setContentView(scannerView);
        scannerView.setResultHandler(this);
        scannerView.startCamera();
    }



    @Override
    public void handleResult(Result result) {


        if(result.getText().equals("1")||result.getText().equals("2")||result.getText().equals("3")||result.getText().equals("4")||result.getText().equals("5")) {

            Intent intent = new Intent(QR.this,MainActivityCliente.class);

            scannerView.playSoundEffect(1);

            numeroMesa = Integer.parseInt(result.getText());
            Bundle bundle  = new Bundle();
            bundle.putInt("NUMERO_MESA",numeroMesa);
            intent.putExtras(bundle);
            scannerView.stopCamera();
            startActivity(intent);
        }
        else{
            Toast t = Toast.makeText(getApplicationContext(), "CODIGO NO PERTENECE A NINGUNA MESA", Toast.LENGTH_LONG);
            t.show();
            esc();
        }
    }

//LoginAcitvity.this.startActivity(new Intent(LoginAcitvity.this, LoginAcitvity.class));
}
