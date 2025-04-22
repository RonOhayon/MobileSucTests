package com.example.hw1_mobilesuc;

import android.content.Intent;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.Manifest;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import java.io.IOException;

public class NoiseCheckActivity extends AppCompatActivity {

    private static final int PERMISSION_CODE = 200;
    private static final double NOISE_THRESHOLD = 60.0;

    private MediaRecorder mediaRecorder;
    private TextView noiseLevelText;
    private Button checkNoiseButton;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noise_check);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, PERMISSION_CODE);
        initView();
    }
    private Runnable noiseUpdater = new Runnable() {
        @Override
        public void run() {
            if (mediaRecorder != null) {
                double amplitude = mediaRecorder.getMaxAmplitude();
                double db = 20 * Math.log10(amplitude / 270.0);
                noiseLevelText.setText("Volume: " + (int) db + " dB");

                if (db > NOISE_THRESHOLD) {
                    Toast.makeText(NoiseCheckActivity.this, "✅", Toast.LENGTH_SHORT).show();
                    stopRecording();
                    Intent intent = new Intent(NoiseCheckActivity.this,MainActivity.class);
                    startActivity(intent);
                } else {
                    handler.postDelayed(this, 1000);
                }
            }
        }
    };
    private void initView(){
        noiseLevelText = findViewById(R.id.noiseLevelText);
        checkNoiseButton = findViewById(R.id.checkNoiseButton);

        checkNoiseButton.setOnClickListener(v -> {
            startRecording();
            handler.post(noiseUpdater);
        });
    }
    private void startRecording() {
        if (mediaRecorder != null) return;

        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setOutputFile("/dev/null");
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mediaRecorder.prepare();
            mediaRecorder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void stopRecording() {
        if (mediaRecorder != null) {
            mediaRecorder.stop();
            mediaRecorder.release();
            mediaRecorder = null;
        }
        handler.removeCallbacks(noiseUpdater);
    }
    @Override
    protected void onPause() {
        super.onPause();
        stopRecording();
    }
}