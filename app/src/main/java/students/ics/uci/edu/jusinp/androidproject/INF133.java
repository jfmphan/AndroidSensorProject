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
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.io.IOException;
import java.text.DecimalFormat;


public class INF133 extends ActionBarActivity{

    private MediaPlayer mediaPlayer;
    private AssetFileDescriptor afd1;
    private AssetFileDescriptor afd2;
    private AssetFileDescriptor afd3;
    private AssetFileDescriptor afd4;
    private AssetFileDescriptor afd5;
    private AssetFileDescriptor afd6;
    private SensorManager sensorManager;
    private SensorEventListener orientationS;
    private TextView textField01;
    private TextView textField02;
    private TextView textField03;

    private double dX;
    private double dY;
    private double dZ;

    private void updateUI()
    {
        runOnUiThread(new Runnable(){

            @Override
            public void run(){

                DecimalFormat df = new DecimalFormat("##.##");
                textField01.setText("x : " + df.format(dX));
                textField02.setText("y : " + df.format(dY));
                textField03.setText("z : " + df.format(dZ));
            }
        });

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
        sensorManager.registerListener(orientationS, sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION), SensorManager.SENSOR_DELAY_UI);
        // register the listener
    }

    @Override
    protected void onStop(){
        // unregister the listener
        sensorManager.unregisterListener(orientationS);
        super.onStop();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inf133);
        textField01 = (TextView) findViewById(R.id.textView);
        textField02 = (TextView) findViewById(R.id.textView2);
        textField03 = (TextView) findViewById(R.id.textView3);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        orientationS = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {

                dZ = event.values[0];
                dX = event.values[1];
                dY = event.values[2];
                updateUI();

                if(dZ > 310 && dZ < 330)
                {
                    playAudio(afd1);
                }
                else if(dZ > 140 && dZ < 155)
                {
                    playAudio(afd2);
                }
                else if(dY > 40 && dY < 50)
                {
                    playAudio(afd3);
                }
                else if(dY < -40 && dY > -50)
                {
                    playAudio(afd4);
                }
                else if(dX < -150 && dX > -180)
                {
                    playAudio(afd5);
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
