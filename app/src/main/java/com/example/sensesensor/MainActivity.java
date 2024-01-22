package com.example.sensesensor;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    SensorManager senseMan;
    Sensor lightSensor;
    Sensor proximitySensor;
    Sensor humiditySensor;
    TextView textview;
    TextView proximitytextview;
    TextView humiditytextview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //map textview to TextView resources
        textview = (TextView)findViewById(R.id.textview);
        proximitytextview = (TextView)findViewById(R.id.proximityTextView);
        humiditytextview = (TextView) findViewById(R.id.humidityTextView);

        //map sensor manager to system service
        senseMan = (SensorManager) getSystemService(SENSOR_SERVICE);

        lightSensor = senseMan.getDefaultSensor(Sensor.TYPE_LIGHT);

        proximitySensor = senseMan.getDefaultSensor(Sensor.TYPE_PROXIMITY);

        humiditySensor = senseMan.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);

        if (lightSensor != null){
            Toast.makeText(this,"Light Sensor Found", Toast.LENGTH_LONG).show();
            senseMan.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);

        } else if (lightSensor == null){
            Toast.makeText(this, "Light Sensor Not Found", Toast.LENGTH_LONG).show();
        }

        if (proximitySensor != null) {
            Toast.makeText(this, "Proximity Sensor Found", Toast.LENGTH_LONG).show();
            senseMan.registerListener(this, proximitySensor, SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            Toast.makeText(this, "Proximity Sensor Not Found", Toast.LENGTH_LONG).show();
        }

        if (humiditySensor != null) {
            Toast.makeText(this, "Humidity Sensor Found", Toast.LENGTH_LONG).show();
            senseMan.registerListener(this, humiditySensor, SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            Toast.makeText(this, "Humidity Sensor Not Found", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        textview.setText(Float.toString(event.values[0]));
        switch (event.sensor.getType()) {
            case Sensor.TYPE_LIGHT:
                textview.setText("Light Sensor: " + event.values[0]);
                break;
            case Sensor.TYPE_PROXIMITY:
                proximitytextview.setText("Proximity Sensor: " + event.values[0]);
                break;
            case Sensor.TYPE_RELATIVE_HUMIDITY:
                humiditytextview.setText("Humidity Sensor: " + event.values[0]);
                break;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    public void onPause(){
        super.onPause();
        senseMan.unregisterListener(this);
    }

    @Override
    public void onResume(){
        super.onResume();
        senseMan.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
        senseMan.registerListener(this, proximitySensor, SensorManager.SENSOR_DELAY_NORMAL);
        senseMan.registerListener(this, humiditySensor, SensorManager.SENSOR_DELAY_NORMAL);
    }
}