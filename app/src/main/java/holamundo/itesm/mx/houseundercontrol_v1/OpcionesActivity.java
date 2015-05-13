package holamundo.itesm.mx.houseundercontrol_v1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ToggleButton;

import static holamundo.itesm.mx.houseundercontrol_v1.R.id.sonidoButton;


public class OpcionesActivity extends ActionBarActivity {

    ToggleButton tb;
    MediaPlayer buttonSound;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opciones);
        tb = (ToggleButton)this.findViewById(sonidoButton);


        Toast.makeText(getApplicationContext(), "Presione el botón para activar/desactivar el sonido de la aplicación", Toast.LENGTH_LONG).show();

        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(getApplicationContext());
        tb.setChecked(sharedPreferences.getBoolean("toggleButton", false));  //default is false



        tb.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                SharedPreferences sharedPreferences = PreferenceManager
                        .getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("toggleButton", tb.isChecked());
                editor.commit();

                // Is the toggle on?
                boolean on = ((ToggleButton)v).isChecked();

                if (on) {
                    AudioManager aManager=(AudioManager)getSystemService(AUDIO_SERVICE);
                    aManager.setStreamVolume(AudioManager.STREAM_MUSIC, 10, AudioManager.FLAG_PLAY_SOUND);

                } else {
                    AudioManager aManager=(AudioManager)getSystemService(AUDIO_SERVICE);
                    aManager.setStreamVolume(AudioManager.STREAM_MUSIC, 0, AudioManager.FLAG_PLAY_SOUND);
                }

            }
        });


    }


}
