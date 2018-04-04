package co.edu.uptc.sensores;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import java.util.List;

public class MainActivity2 extends AppCompatActivity implements SensorEventListener {

    private SensorManager mSensorManager;

    //sensores
    private Sensor mSensorProximity;
    private Sensor mSensorLight;
    private Sensor mAcelerometro;
    private Sensor mOrientacion;

    // valores sensores
    private TextView sensorLuzTextView;
    private Switch sensorProximidadSwicth;
    private TextView sensorAcelerometroTextView;
    private EditText sensorOrientacionEditText;



    private int contador = 0;
    private float textSize = 0;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        sensorLuzTextView = (TextView) findViewById(R.id.luz);
        sensorProximidadSwicth = (Switch) findViewById(R.id.proximidad);
        sensorAcelerometroTextView = (TextView) findViewById(R.id.acelerometro);
        sensorOrientacionEditText = (EditText) findViewById(R.id.orientacion);

        textSize = sensorLuzTextView.getTextSize();

        mSensorProximity = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        mSensorLight = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        mAcelerometro = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mOrientacion = mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);

        if (mSensorLight == null) {
            sensorLuzTextView.setText("No se encontro Sensor");
        }

        if (mSensorProximity == null) {
            sensorProximidadSwicth.setText("No se encontro Sensor");
        }

        if (mAcelerometro == null) {
            sensorProximidadSwicth.setText("No se encontro Sensor");
        }

        if (mOrientacion == null) {
            sensorOrientacionEditText.setText("No se encontro Sensor");
        }
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        int sensorType = sensorEvent.sensor.getType();
        float currentValue = sensorEvent.values[0];

        switch (sensorType) {

            case Sensor.TYPE_ACCELEROMETER:
                if(currentValue>10)
                    this.contador++;

                sensorAcelerometroTextView.setText(this.contador+"");
                break;

            case Sensor.TYPE_LIGHT:
                sensorLuzTextView.setTextSize(textSize+currentValue);
                break;

            case Sensor.TYPE_PROXIMITY:
                if (currentValue==0)
                    sensorProximidadSwicth.setChecked(true);
                else
                    sensorProximidadSwicth.setChecked(false);

                break;

            case Sensor.TYPE_ORIENTATION:
                sensorOrientacionEditText.setRotation(currentValue);
                break;
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mSensorProximity != null) {
            mSensorManager.registerListener(this, mSensorProximity, SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (mSensorLight != null) {
            mSensorManager.registerListener(this, mSensorLight, SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (mAcelerometro != null) {
            mSensorManager.registerListener(this, mAcelerometro, SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (mOrientacion != null) {
            mSensorManager.registerListener(this, mOrientacion, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override protected void onStop() {
        super.onStop();
        mSensorManager.unregisterListener(this);
    }
}
