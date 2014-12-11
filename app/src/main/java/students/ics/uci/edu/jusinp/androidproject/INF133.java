package students.ics.uci.edu.jusinp.androidproject;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class INF133 extends ActionBarActivity {

    private SensorManager mSensorManager;
    private SensorEventListener mOrientation;
    private TextView textField01;
    private float azimuthAngle;
    private float pitchAngle;
    private float rollAngle;

    private void updateUI()
    {
        runOnUiThread(new Runnable(){

            @Override
            public void run(){
                textField01.setText(" " + pitchAngle);
            }
        });

    }

    @Override
    protected void onResume(){
        super.onResume();
        mSensorManager.registerListener(mOrientation, mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),SensorManager.SENSOR_DELAY_NORMAL);
        // register the listener
    }

    @Override
    protected void onStop(){
        // unregister the listener

        super.onStop();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inf133);
        textField01 = (TextView) findViewById(R.id.textField01);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mOrientation = new SensorEventListener(){

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy)
            {

            }

            @Override
            public void onSensorChanged(SensorEvent event){

                azimuthAngle = event.values[0];
                pitchAngle = event.values[1];
                rollAngle = event.values[2];
                updateUI();
            }

        };
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_inf133, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
