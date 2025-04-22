package com.example.hw1_mobilesuc;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hw1_mobilesuc.MainActivity;
import com.example.hw1_mobilesuc.R;

public class LightCheckActivity extends AppCompatActivity {

    private SensorManager sensorManager;
    private Sensor lightSensor;
    private SensorEventListener lightListener;

    private TextView lightText;
    private Button checkLightButton;

    private float currentLux = -1;
    private static final float THRESHOLD_LUX = 30.0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light_check);

        initView();
    }
    private void initView(){
        lightText = findViewById(R.id.lightText);
        checkLightButton = findViewById(R.id.checkLightButton);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        if (lightSensor == null) {
            Toast.makeText(this, "this phone don't have light sensor 😢", Toast.LENGTH_LONG).show();
            checkLightButton.setEnabled(false);
            return;
        }

        lightListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                currentLux = event.values[0];
                lightText.setText("lux level: " + currentLux + " lx");
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {}
        };

        checkLightButton.setOnClickListener(v -> {
            if (currentLux >= THRESHOLD_LUX) {
                Toast.makeText(this, "✅", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, MainActivity.class));
            } else {
                Toast.makeText(this, "❌", Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(lightListener, lightSensor, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(lightListener);
    }
}
