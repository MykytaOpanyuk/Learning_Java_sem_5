package com.example.nikita.accelerometer_lab_3;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.Rule;


import static org.junit.Assert.*;
import android.support.test.rule.ActivityTestRule;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.example.nikita.accelerometer_lab_3", appContext.getPackageName());
    }

    @Test
    public void initSensors() {
        SensorManager sensorManager;
        Sensor accelerometer, gyroscope;

        Context appContext = InstrumentationRegistry.getTargetContext();

        sensorManager = (SensorManager)appContext.getSystemService(Context.SENSOR_SERVICE);

        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        assertNotEquals(null, gyroscope);
        assertNotEquals(null, accelerometer);
    }

    @Test
    public void test_acc_gyro_distance() {

        float my_values[] = {10,10,10};

        double check_distance = activeTestRule.getActivity().find_acc_distance(my_values, 50);

        assertEquals(0.0216, check_distance, 0.01);

        check_distance = activeTestRule.getActivity().find_gyro_distance(my_values, 50);

        assertEquals(0.8660, check_distance, 0.01);

    }

    @Rule
    public ActivityTestRule<MainActivity> activeTestRule =
            new ActivityTestRule<MainActivity>(MainActivity.class);

    @Test
    public void test_change_distance() throws InterruptedException {

        double check_distance = activeTestRule.getActivity().distance;
        double check_distance_scope = activeTestRule.getActivity().distance_scope;

        Thread.sleep(5000);

        assertNotEquals(check_distance, activeTestRule.getActivity().distance);
        assertNotEquals(check_distance_scope, activeTestRule.getActivity().distance_scope);

    }
}
