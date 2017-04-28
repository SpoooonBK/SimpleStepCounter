package net.estebanrodriguez.apps.stepcounter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import net.estebanrodriguez.apps.stepcountlib.StepCounterService;
import net.estebanrodriguez.apps.stepcountlib.StepEventListener;

import timber.log.Timber;

public class MainActivity extends AppCompatActivity  implements StepEventListener{

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Timber.plant(new Timber.DebugTree());

        Intent intent = new Intent(this, StepCounterService.class);
        startService(intent);
        mTextView = (TextView) findViewById(R.id.text_stepcount);


    }

    @Override
    public void onStepListenEvent(int stepCount) {
        mTextView.setText("Step Count: " +stepCount);

    }

    @Override
    protected void onResume() {
        super.onResume();
        StepCounterService.registerStepListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        StepCounterService.unregisterStepListener(this);
    }
}
