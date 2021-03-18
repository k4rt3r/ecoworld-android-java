package com.example.ecoworld;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;

public class DevelopersActivity extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developers);

        Intent obtenerIntentJugador = getIntent();
        jugador = (Jugador) obtenerIntentJugador.getExtras().getSerializable(JUGADOR);

        final TextView chronoDevelopers = findViewById(R.id.chronoDevelopers);
        final TextView txtDevelopers = findViewById(R.id.txtDevelopers);

        chronoDevelopers.setVisibility(View.GONE);

        cambiarIdioma(txtDevelopers);

        new CountDownTimer(4000, 1000) {
            //onTick: Callback fired on regular interval.
            public void onTick(long millisUntilFinished) {
                chronoDevelopers.setText(String.format(Locale.getDefault(),
                        "%d", millisUntilFinished / 1000L));
            }

            //onFinish: Callback fired when the time is up.
            public void onFinish() {

                finishAffinity();
            }

        }.start();
    }

    //SETTEAMOS EL TEXTO DE LOS BOTONES SEGUN EL IDIOMA DEL JUGADOR.
    public void cambiarIdioma(TextView txtDevelopers) {

        if (jugador.getIdioma() == 0) {  //CAT
            txtDevelopers.setText(R.string.desarrolladors);
        } else if (jugador.getIdioma() == 1) { //ESP
            txtDevelopers.setText(R.string.desarrolladores);
        } else { //ENG
            txtDevelopers.setText(R.string.developers);
        }

    }

    //CON ESTO ANULAMOS EL BOTON DE VOLVER.
    @Override
    public void onBackPressed() {
    }
}