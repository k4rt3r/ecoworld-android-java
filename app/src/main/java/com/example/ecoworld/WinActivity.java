package com.example.ecoworld;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

public class WinActivity extends AppCompatActivity {

    public static final String JUGADOR = "Jugador";
    public static final int MAIN_ACTIVITY = 1;
    public static final int USER_ACTIVITY = 2;
    public static final int SETTINGS_ACTIVITY = 3;
    public static final int INTRO_ACTIVITY = 4;
    public static final int MAP_ACTIVITY = 5;
    public static final int QUESTIONS_ACTIVITY = 6;
    public static final int WIN_ACTIVITY = 7;
    public static final int ACTIVISTAS_ACTIVITY = 8;
    public static final int RANKING_ACTIVITY = 9;

    Jugador jugador;
    CountDownTimer countDownTimer;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_win);

        //CREAMOS UN OBJETO MEDIAPLAYER Y LE ASIGNAMOS LA MUSICA
        mediaPlayer = MediaPlayer.create(this, R.raw.musica);
        mediaPlayer.start();

        final TextView txtExplicacionWin = findViewById(R.id.txtExplicacionWin);
        final TextView chronoWin = findViewById(R.id.chronoWin);
        final Button btnSkipWin = findViewById(R.id.btnSkipWin);

        //GONE(APARTE DE ESTAR INVISIBLE NO OCUPA ESPACIO)
        chronoWin.setVisibility(View.GONE);

        Intent obtenerIntentJugador = getIntent();
        jugador = (Jugador) obtenerIntentJugador.getExtras().getSerializable(JUGADOR);

        cambiarIdioma(txtExplicacionWin, btnSkipWin);
        chrono(chronoWin);

        btnSkipWin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ActivityActivitas =
                        new Intent(WinActivity.this, ActivistActivity.class);
                ActivityActivitas.putExtra(JUGADOR, jugador);
                startActivity(ActivityActivitas);
                mediaPlayer.pause();
                finish();
            }
        });
    }

    //SETTEAMOS EL TEXTO DE LOS BOTONES SEGUN EL IDIOMA DEL JUGADOR.
    public void cambiarIdioma(TextView txtExplicacionWin, Button btnSkipWin) {

        int idioma = jugador.getIdioma();

        if (idioma == 0) { //CAT
            txtExplicacionWin.setText(R.string.felicidadesCAT);
            btnSkipWin.setText(R.string.seguent);
        } else if (idioma == 1) {  //ESP
            txtExplicacionWin.setText(R.string.felicidadesESP);
            btnSkipWin.setText(R.string.siguiente);
        } else { //ENG
            txtExplicacionWin.setText(R.string.felicidadesENG);
            btnSkipWin.setText(R.string.next);
        }
    }

    //LA GESTION DEL CRONO, CUANDO TIENE QUE PARARSE
    public void chrono(final TextView chronoWin) {

        countDownTimer = new CountDownTimer(334000, 1000) {
            //onTick: Callback fired on regular interval.
            public void onTick(long millisUntilFinished) {
                chronoWin.setText(String.format(Locale.getDefault(),
                        "%d", millisUntilFinished / 1000L));
            }

            //onFinish: Callback fired when the time is up.
            public void onFinish() {
                Intent ActivityActivitas =
                        new Intent(WinActivity.this, ActivistActivity.class);
                ActivityActivitas.putExtra(JUGADOR, jugador);
                startActivity(ActivityActivitas);
                mediaPlayer.pause();
            }
        }.start();
    }

    //CON ESTO ANULAMOS EL BOTON DE VOLVER.
    @Override
    public void onBackPressed() {
    }
}