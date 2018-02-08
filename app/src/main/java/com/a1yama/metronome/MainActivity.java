package com.a1yama.metronome;

import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private SoundPool soundPool;
    private TextView speedText;
    private int soundId, streamId;
    private float speed;
    private String speedString;
    private boolean isPlaying = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button startButton = findViewById(R.id.start);
        Button stopButton = findViewById(R.id.stop);

        AudioAttributes attr = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build();

        soundPool = new SoundPool.Builder()
                .setAudioAttributes(attr)
                .setMaxStreams(2)
                .build();

        soundId = soundPool.load(this, R.raw.sound, 1);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isPlaying) {
                    speedText = findViewById(R.id.speed);
                    speedString = speedText.getText().toString();
                    if (speedString.length() == 0){
                        speedString = "0";
                    }
                    if (Integer.parseInt(speedString) < 30) {
                        speedString = "30";
                        speedText.setText("30");
                    } else if (Integer.parseInt(speedString) > 250) {
                        speedString = "250";
                        speedText.setText("250");
                    }
                    speed = Float.parseFloat(speedString) / 120f;
                    // 1fで157bpmくらい
                    streamId = soundPool.play(soundId, 1.0f, 1.0f, 0, -1, speed);
                    isPlaying = true;
                }
            }
        });
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                soundPool.stop(streamId);
                isPlaying = false;
            }
        });
    }
}
