package students.ics.uci.edu.jusinp.androidproject;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.io.FileDescriptor;
import java.io.IOException;


public class INF133 extends ActionBarActivity{

    private MediaPlayer mediaPlayer;
    private AssetFileDescriptor afd1;
    private AssetFileDescriptor afd2;
    private AssetFileDescriptor afd3;
    private AssetFileDescriptor afd4;
    private AssetFileDescriptor afd5;
    private AssetFileDescriptor afd6;
    private SensorManager sensorManager;
    private SensorEventListener acc;
    private TextView textField01;


    private double[] x = new double[2];
    private double[] y = new double[2];
    private double[] z = new double[2];
    private double dX;
    private double dY;
    private double dZ;

    private void updateUI()
    {
        runOnUiThread(new Runnable(){

            @Override
            public void run(){
                textField01.setText("x : " + dX + " y: " + dY + " z: " + dZ);
            }
        });

    }

    private double greatest(double x, double y, double z)
    {
        double greatest = 0;

        double dx = Math.abs(x);
        double dy = Math.abs(y);
        double dz = Math.abs(z);

        if(dx > dy)
        {
            greatest = x;
        }
        else
        {
            greatest = y;
        }

        if(greatest < dz)
        {
            greatest = z;
        }

        return greatest;
    }


    synchronized  void playAudio(AssetFileDescriptor afd){
        if(mediaPlayer.isPlaying())
        {
            return;
        }
        else
        {
            try{
                mediaPlayer.reset();
                mediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
                mediaPlayer.prepare();
                mediaPlayer.start();
            }
            catch (IllegalArgumentException e)
            {
                e.printStackTrace();
            }
            catch (IllegalStateException e)
            {
                e.printStackTrace();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

        }
    }


    @Override
    protected void onResume(){
        super.onResume();
        sensorManager.registerListener(acc, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_UI);
        // register the listener
    }

    @Override
    protected void onStop(){
        // unregister the listener
        sensorManager.unregisterListener(acc);
        super.onStop();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inf133);
        textField01 = (TextView) findViewById(R.id.textView);


        z[1] = 0;
        y[1] = 0;
        x[1] = 0;

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        acc = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {


                z[0] = z[1];
                y[0] = y[1];
                x[0] = x[1];

                z[1] = event.values[0];
                y[1] = event.values[1];
                x[1] = event.values[2];

                dX = x[1] - x[0];
                dY = y[1] - y[0];
                dZ = z[1] - z[0];
                updateUI();

                double n = greatest(dX, dY, dZ);

                if(dX == n)
                {
                    if(dX > 1){
                        playAudio(afd1);
                    }
                    else if(dX < -1){
                        playAudio(afd2);
                    }
                }
                else if(dY == n)
                {
                    if(dY > 1){
                        playAudio(afd3);
                    }
                    else if(dY < - 1){
                        playAudio(afd4);
                    }
                }

                else if(dZ == n)
                {
                    if(dZ > 1){
                        playAudio(afd5);
                    }
                    else if(dZ < -1){
                        playAudio(afd6);
                    }
                }

            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };

        mediaPlayer = new MediaPlayer();
        afd1 = getApplicationContext().getResources().openRawResourceFd(R.raw.classic);
        afd2 = getApplicationContext().getResources().openRawResourceFd(R.raw.alpha);
        afd3 = getApplicationContext().getResources().openRawResourceFd(R.raw.beeps);
        afd4 = getApplicationContext().getResources().openRawResourceFd(R.raw.bell);
        afd5 = getApplicationContext().getResources().openRawResourceFd(R.raw.bubble);
        afd6 = getApplicationContext().getResources().openRawResourceFd(R.raw.instance);


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
