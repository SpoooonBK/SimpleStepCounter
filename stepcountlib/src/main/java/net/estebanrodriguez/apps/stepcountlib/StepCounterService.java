package net.estebanrodriguez.apps.stepcountlib;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by spoooon on 4/27/17.
 */

public class StepCounterService extends IntentService implements SensorEventListener{

    private SensorManager mSensorManager;
    private Sensor mStepCounter;
    private static int sStepcount;
    private static List<StepEventListener> sStepEventListeners;



    public StepCounterService(){
        super("stepservice");
    }

    public StepCounterService(String name) {
        super(name);
    }

    public static int getStepcount() {
        return sStepcount;
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        mSensorManager = (SensorManager)  getSystemService(Context.SENSOR_SERVICE);
        mStepCounter = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        mSensorManager.registerListener(this,mStepCounter, SensorManager.SENSOR_DELAY_NORMAL);
    }


    @Override
    public void onSensorChanged(SensorEvent event) {

        sStepcount = (int)event.values[0];
        Log.v(StepCounterService.class.getSimpleName().toString(), "Steps: " + sStepcount);

            notifyListeners();


    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public static void registerStepListener(StepEventListener stepEventListener){
        if(sStepEventListeners == null){
            sStepEventListeners = new ArrayList<>();
        }
        sStepEventListeners.add(stepEventListener);

    }

    public static void unregisterStepListener(StepEventListener stepEventListener){
        if(sStepEventListeners != null){
            sStepEventListeners.remove(stepEventListener);
        }
    }

    private static void notifyListeners(){
        if(sStepEventListeners != null){
            for(StepEventListener stepEventListener: sStepEventListeners)
                {
                    stepEventListener.onStepListenEvent(getStepcount());
            }
        }
    }

}
