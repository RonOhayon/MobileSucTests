package com.example.hw1_mobilesuc;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class WifiCheckActivity extends AppCompatActivity {

    private Button checkButton;
    private TextView wifiStatusText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi_check);
        initView();
    }
    private void initView(){
        checkButton = findViewById(R.id.checkWifiButton);
        wifiStatusText = findViewById(R.id.wifiStatusText);
        checkButton.setOnClickListener(v -> {
            if (isWifiConnected()) {
                wifiStatusText.setText("✅ Wi-Fi");
                Toast.makeText(this, "Wifi is contacted ", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(WifiCheckActivity.this, MainActivity.class);
                startActivity(intent);
            } else {
                wifiStatusText.setText("❌ Wi-Fi");
                Toast.makeText(this, "Wifi is not contacted ", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private boolean isWifiConnected() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivityManager == null) return false;

            Network network = connectivityManager.getActiveNetwork();
            NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(network);

            return capabilities != null && capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI);
        } else {
            // check if API under 23
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            android.net.NetworkInfo networkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            return networkInfo != null && networkInfo.isConnected();
        }
    }
}