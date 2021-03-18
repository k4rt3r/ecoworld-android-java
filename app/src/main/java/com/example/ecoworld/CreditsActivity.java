package com.example.ecoworld;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;

public class CreditsActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_credits);

        Intent obtenerIntentJugador = getIntent();
        jugador = (Jugador) obtenerIntentJugador.getExtras().getSerializable(JUGADOR);

        final TextView chronoCredits = findViewById(R.id.chronoCredits);
        chronoCredits.setVisibility(View.GONE);

        new CountDownTimer(4000, 1000) {
            //onTick: Callback fired on regular interval.
            public void onTick(long millisUntilFinished) {
                chronoCredits.setText(String.format(Locale.getDefault(),
                        "%d", millisUntilFinished / 1000L));
            }

            //onFinish: Callback fired when the time is up.
            public void onFinish() {
                Intent ManagersActivity =
                        new Intent(CreditsActivity.this, ManagersActivity.class);
                ManagersActivity.putExtra(JUGADOR, jugador);
                startActivity(ManagersActivity);
            }
        }.start();
    }

    //CON ESTO ANULAMOS EL BOTON DE VOLVER.
    @Override
    public void onBackPressed() {
    }

}