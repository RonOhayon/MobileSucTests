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

public class BatteryTest extends AppCompatActivity {

    private EditText passwordEditText;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_battary_test);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initView();
    }
    private void initView(){
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(view -> {
            String input = passwordEditText.getText().toString();
            int batteryLevel = getBatteryPercentage();

            if (input.equals(String.valueOf(batteryLevel))) {
                Toast.makeText(this, "Successes", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(BatteryTest.this, MainActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(this, "failed (try to input the battery level)", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private int getBatteryPercentage(){
        IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = registerReceiver(null, filter);
        int level = batteryStatus != null ? batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) : -1;
        int scale = batteryStatus != null ? batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1) : -1;

        if (level >= 0 && scale > 0) {
            return (int) ((level / (float) scale) * 100);
        }
        return -1;
    }
}