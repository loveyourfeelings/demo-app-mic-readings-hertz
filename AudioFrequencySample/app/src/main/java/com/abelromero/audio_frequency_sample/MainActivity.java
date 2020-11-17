package com.abelromero.audio_frequency_sample;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;

import com.abelromero.audio_frequency_sample.audio.calculators.AudioCalculator;
import com.abelromero.audio_frequency_sample.audio.core.Callback;
import com.abelromero.audio_frequency_sample.audio.core.Recorder;
public class MainActivity extends Activity {

    private Recorder recorder;
    private AudioCalculator audioCalculator;
    private Handler handler;

    private TextView textAmplitude;
    private TextView textDecibel;
    private TextView textFrequency;
    private TextView textTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        recorder = new Recorder(callback);
        audioCalculator = new AudioCalculator();
        handler = new Handler(Looper.getMainLooper());

        textAmplitude = (TextView) findViewById(R.id.textAmplitude);
        textDecibel = (TextView) findViewById(R.id.textDecibel);
        textFrequency = (TextView) findViewById(R.id.textFrequency);
        textTime = (TextView) findViewById(R.id.textTime);
    }

    private Callback callback = new Callback() {

        @Override
        public void onBufferAvailable(long start, long before, long after, byte[] buffer) {
            audioCalculator.setBytes(buffer);
            int amplitude = audioCalculator.getAmplitude();
            double decibel = audioCalculator.getDecibel();
            double frequency = audioCalculator.getFrequency();

            final String amp = String.valueOf(amplitude + " Amp");
            final String db = String.valueOf(decibel + " db");
            final String hz = String.valueOf(frequency + " Hz");
            final String time = String.valueOf(start + ", "  + before + ", " + after + " (" + (after - before) + " delay) ms");

            handler.post(new Runnable() {
                @Override
                public void run() {
                    textAmplitude.setText(amp);
                    textDecibel.setText(db);
                    textFrequency.setText(hz);
                    textTime.setText(time);
                }
            });
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        recorder.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        recorder.stop();
    }
}
