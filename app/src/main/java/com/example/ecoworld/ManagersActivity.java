package com.example.ecoworld;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;

public class ManagersActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_managers);

        Intent obtenerIntentJugador = getIntent();
        jugador = (Jugador) obtenerIntentJugador.getExtras().getSerializable(JUGADOR);

        final TextView chronoManagers = findViewById(R.id.chronoManagers);
        chronoManagers.setVisibility(View.GONE);

        final TextView txtManagers = findViewById(R.id.txtManagers);

        cambiarIdioma(txtManagers);

        new CountDownTimer(4000, 1000) {
            //onTick: Callback fired on regular interval.
            public void onTick(long millisUntilFinished) {
                chronoManagers.setText(String.format(Locale.getDefault(),
                        "%d", millisUntilFinished / 1000L));
            }

            //onFinish: Callback fired when the time is up.
            public void onFinish() {
                Intent TeachersActivity =
                        new Intent(ManagersActivity.this, TeachersActivity.class);
                TeachersActivity.putExtra(JUGADOR, jugador);
                startActivity(TeachersActivity);
            }

        }.start();
    }

    //SETTEAMOS EL TEXTO DE LOS BOTONES SEGUN EL IDIOMA DEL JUGADOR.
    public void cambiarIdioma(TextView txtManagers) {

        if (jugador.getIdioma() == 0) {  //CAT
            txtManagers.setText(R.string.capsProjecte);
        } else if (jugador.getIdioma() == 1) { //ESP
            txtManagers.setText(R.string.jefesProyecto);
        } else { //ENG
            txtManagers.setText(R.string.projectManagers);
        }

    }

    //CON ESTO ANULAMOS EL BOTON DE VOLVER.
    @Override
    public void onBackPressed() {
    }
}