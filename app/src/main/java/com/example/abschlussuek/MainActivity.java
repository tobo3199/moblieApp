package com.example.abschlussuek;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private TextView textViewStepCounter;
    private SensorManager sensorManager;
    private Sensor sensor;
    boolean running = false;
    int reset;
    private int currentValue;
    private Button resetButton;
    private Button startButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_main );
        textViewStepCounter = (TextView) findViewById ( R.id.textViewStepCounter );
        resetButton = (Button) findViewById(R.id.resetButton);
        resetButton.setOnClickListener(clickListener);
        startButton = (Button) findViewById(R.id.startButton);
        startButton.setOnClickListener(clickListener);



        sensorManager = (SensorManager) getSystemService ( Context.SENSOR_SERVICE);
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.resetButton:
                    reset();
                    onPause();
                    break;
                case R.id.startButton:
                    onResume();
                    reset();
                    break;

            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume ();
        running = true;
        Sensor countSensor = sensorManager.getDefaultSensor ( sensor.TYPE_STEP_COUNTER );
        if(countSensor!= null){
            sensorManager.registerListener ( this,countSensor,SensorManager.SENSOR_DELAY_UI );
        }else {
            Toast.makeText ( this,"SENSOR NOT FOUND",Toast.LENGTH_SHORT ).show ();
        }

    }

    @Override
    protected void onPause() {
        super.onPause ();
        running = false;
        //if you unregister the hardware will stop detecting steps
    }

    public void reset(){
        reset = currentValue;
        textViewStepCounter.setText("0");
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        if (running){
            currentValue = (int) event.values[0];
            textViewStepCounter.setText ( String.valueOf ( currentValue - reset) );
            //textViewStepCounter.setText(reset);
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}