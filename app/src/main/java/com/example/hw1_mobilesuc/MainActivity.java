package com.example.hw1_mobilesuc;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private  Button batteryTestButton,compassTestButton,wifiTestButton,noiseTestButton ,lightTestButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        compassTestButton = findViewById(R.id.compassTestButton);
        batteryTestButton = findViewById(R.id.batteryTestButton);
        wifiTestButton = findViewById(R.id.wifiTestButton);
        noiseTestButton = findViewById(R.id.noiseTestButton);
        lightTestButton = findViewById(R.id.lightTestButton);

        batteryTestButton.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this,BatteryTest.class);
            startActivity(intent);
        });
        compassTestButton.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this,CompassCheckActivity.class);
            startActivity(intent);
        });
        wifiTestButton.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this,WifiCheckActivity.class);
            startActivity(intent);
        });
        noiseTestButton.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this,NoiseCheckActivity.class);
            startActivity(intent);
        });
        lightTestButton.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this,NoiseCheckActivity.class);
            startActivity(intent);
        });
    }

}