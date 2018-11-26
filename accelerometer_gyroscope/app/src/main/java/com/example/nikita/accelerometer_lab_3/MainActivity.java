package com.example.nikita.accelerometer_lab_3;

import android.content.Context;
import android.os.SystemClock;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.hardware.SensorEventListener;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private static final String TAG = "MainActivity";

    SensorManager sensorManager; //this manager give me access the device`s sensors
    // like accelerometer, gyroscope, light, gravity and etc.
    Sensor accelerometer, gyroscope;

    //accelerometer params
    TextView x_value, y_value, z_value, result_value;
    //gyroscope params
    TextView x_value_scope, y_value_scope, z_value_scope, result_value_scope;

    private long last_update = 0;
    private long last_update_scope = 0;
    static float last_x, last_y, last_z;
    static float last_x_scope, last_y_scope, last_z_scope;
    double distance = 0;
    double distance_scope = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        x_value = findViewById(R.id.x_value); // get id of textView
        y_value = findViewById(R.id.y_value);
        z_value = findViewById(R.id.z_value);
        result_value = findViewById(R.id.result_value);

        x_value_scope = findViewById(R.id.x_value_scope);
        y_value_scope = findViewById(R.id.y_value_scope);
        z_value_scope = findViewById(R.id.z_value_scope);
        result_value_scope = findViewById(R.id.result_value_scope);

        Log.d(TAG, "Init sensor service."); // log message

        //Init sensor services;
        sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);

        //get for "accelerometer" id of type accelerometer sensor
        //sensorManager check did this device support this sensor
        //if not supported - getting "null" for "accelerometer"
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (accelerometer != null) {
            sensorManager.registerListener(MainActivity.this, accelerometer,
                    SensorManager.SENSOR_DELAY_NORMAL);
            Log.d(TAG, "Registered accelerometer listener.");
        } else {
            result_value.setText("Accelerometer not supported.");
        }

        //like accelerometer
        gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        if (gyroscope != null) {
            sensorManager.registerListener(MainActivity.this, gyroscope,
                    SensorManager.SENSOR_DELAY_NORMAL);
            Log.d(TAG, "Registered gyroscope listener.");
        } else {
            result_value.setText("Gyroscope not supported.");
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onSensorChanged(SensorEvent event) { //this function called, if position
        // of device changed
        Sensor my_sensor = event.sensor;

        //check what type of sensor int this event
        if (my_sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            Log.d(TAG, "onSensorChanged : X: " + event.values[0] +
                    " Y: " + event.values[1] +
                    " Z: " + event.values[2]);

            x_value.setText("x value:" + event.values[0]);
            y_value.setText("y value:" + event.values[1]);
            z_value.setText("z value:" + event.values[2]);

            long cur_time = SystemClock.elapsedRealtime();

            long diff_time = (cur_time - last_update); //in seconds

            if ((diff_time > 50) && (diff_time < 100)) {
                distance = distance + find_acc_distance(event.values, diff_time);
                result_value.setText("result value: " + distance + " m;");

                last_x = event.values[0];
                last_y = event.values[1];
                last_z = event.values[2];
            }
            last_update = cur_time;
        }

        if (my_sensor.getType() == Sensor.TYPE_GYROSCOPE) {
            Log.d(TAG, "onSensorChanged : X: " + event.values[0] +
                    " Y: " + event.values[1] +
                    " Z: " + event.values[2]);

            x_value_scope.setText("x value:" + event.values[0]);
            y_value_scope.setText("y value:" + event.values[1]);
            z_value_scope.setText("z value:" + event.values[2]);

            long cur_time = SystemClock.elapsedRealtime();

            long diff_time = (cur_time - last_update_scope); // in seconds

            if ((diff_time > 50) && (diff_time < 1000)) {
                distance_scope = distance_scope + find_gyro_distance(event.values, diff_time);
                result_value_scope.setText("result value: " + distance_scope + " rad;");

                last_x_scope = event.values[0];
                last_y_scope = event.values[1];
                last_z_scope = event.values[2];
            }

            last_update_scope = cur_time;
        }
    }

    double find_acc_distance (float values[], long diff_time) {
        if ((Math.abs(values[0] - last_x) > 0.5) ||
                (Math.abs(values[1] - last_y) > 0.5) ||
                (Math.abs(values[2] - last_z) > 0.5)) {

            double dist = ((Math.sqrt((values[0] - last_x)*(values[0] - last_x)
                            + (values[1] - last_y)*(values[1] - last_y)
                            + (values[2] - last_z)*(values[2] - last_z)))
                            * diff_time * diff_time)/(2 * 1000 * 1000);

            return dist;
        }
        return 0;
    }

    double find_gyro_distance (float values[], long diff_time) {
        if ((Math.abs(values[0] - last_x_scope) > 0.3) ||
                (Math.abs(values[1] - last_y_scope) > 0.3) ||
                (Math.abs(values[2] - last_z_scope) > 0.3)) {
            double dist = (Math.sqrt((values[0] - last_x_scope)
                    * (values[0] - last_x_scope)
                            + (values[1] - last_y_scope)
                    * (values[1] - last_y_scope)
                            + (values[2] - last_z_scope)
                    * (values[2] - last_z_scope))
                            * diff_time) / 1000;
            return dist;
        }
        return 0;
    }
}
