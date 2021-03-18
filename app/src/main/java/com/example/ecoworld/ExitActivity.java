package com.example.ecoworld;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ExitActivity extends AppCompatActivity {

    public static final int MAP_ACTIVITY = 5;
    public static final String JUGADOR = "Jugador";
    public static final int QUESTIONS_ACTIVITY = 6;

    Jugador jugador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exit);

        Button btnNoExit = findViewById(R.id.btnNoExit);
        Button btnSiExit = findViewById(R.id.btnSiExit);
        TextView logoBocadilloIntroExit = findViewById(R.id.logoBocadilloIntroExit);

        Intent obtenerIntentJugador = getIntent();
        jugador = (Jugador) obtenerIntentJugador.getExtras().getSerializable(JUGADOR);

        cambiarIdioma(jugador, btnNoExit, btnSiExit, logoBocadilloIntroExit);

        btnNoExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnSiExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ExitActivity.this,
                        QuestionsActivity.class);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    //SETTEAMOS EL TEXTO DE LA PREGUNTA SEGUN EL IDIOMA DEL JUGADOR.
    public void cambiarIdioma(Jugador jugador, Button btnNoExit, Button btnSiExit,
                              TextView logoBocadilloIntroExit) {

        int idioma = jugador.getIdioma();

        if (idioma == 0) { //CAT
            btnNoExit.setText("No");
            btnSiExit.setText("Si");
            logoBocadilloIntroExit.setText(R.string.exitCAT);
        } else if (idioma == 1) { //ESP
            btnNoExit.setText("No");
            btnSiExit.setText("Si");
            logoBocadilloIntroExit.setText(R.string.exitESP);
        } else { //ENG
            btnNoExit.setText("No");
            btnSiExit.setText("Yes");
            logoBocadilloIntroExit.setText(R.string.exitENG);
        }
    }

    //CON ESTO ANULAMOS EL BOTON DE VOLVER.
    @Override
    public void onBackPressed() {
    }
}