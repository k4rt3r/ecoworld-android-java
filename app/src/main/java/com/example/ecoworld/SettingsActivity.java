package com.example.ecoworld;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SettingsActivity extends AppCompatActivity {

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

    int idiomaSeleccionado = -1;
    Jugador jugador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        final Button btnSave = findViewById(R.id.btnSave);
        final TextView txtSettings = findViewById(R.id.txtSettings);
        final TextView txtLanguageSettings = findViewById(R.id.txtLanguageSettings);
        final Button btnCatalanSettings = findViewById(R.id.btnCatalanSettings);
        final Button btnEspanolSettings = findViewById(R.id.btnEspanolSettings);
        final Button btnInglesSettings = findViewById(R.id.btnEnglishSettings);

        Intent obtenerIntentJugador = getIntent();
        jugador = (Jugador) obtenerIntentJugador.getExtras().getSerializable(JUGADOR);

        cambiarIdioma(btnSave, txtSettings, txtLanguageSettings);

        idiomaSeleccionado = jugador.getIdioma();


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent UserActivity;

                jugador.setIdioma(idiomaSeleccionado);

                switch (idiomaSeleccionado) {

                    case 0:
                        UserActivity = new Intent(SettingsActivity.this,
                                UserActivity.class);
                        UserActivity.putExtra(JUGADOR, jugador);
                        setResult(RESULT_OK, UserActivity);
                        startActivity(UserActivity);
                        finish();
                        break;

                    case 1:
                        UserActivity = new Intent(SettingsActivity.this,
                                UserActivity.class);
                        UserActivity.putExtra(JUGADOR, jugador);
                        setResult(RESULT_OK, UserActivity);
                        startActivity(UserActivity);
                        finish();
                        break;

                    case 2:
                        UserActivity = new Intent(SettingsActivity.this,
                                UserActivity.class);
                        UserActivity.putExtra(JUGADOR, jugador);
                        setResult(RESULT_OK, UserActivity);
                        startActivity(UserActivity);
                        finish();
                        break;
                    default:
                        finish();
                }
            }
        });

        btnCatalanSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                btnCatalanSettings.setBackgroundResource(R.drawable.catroundactivada);
                btnEspanolSettings.setBackgroundResource(R.drawable.spainround);
                btnInglesSettings.setBackgroundResource(R.drawable.ukround);
                idiomaSeleccionado = 0;
                btnSave.setText("Guardar");
                txtSettings.setText("Ajustos");
                txtLanguageSettings.setText("Idioma");
            }
        });

        btnEspanolSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnEspanolSettings.setBackgroundResource(R.drawable.spainroundactivada);
                btnCatalanSettings.setBackgroundResource(R.drawable.catround);
                btnInglesSettings.setBackgroundResource(R.drawable.ukround);
                idiomaSeleccionado = 1;
                btnSave.setText("Guardar");
                txtSettings.setText("Ajustes");
                txtLanguageSettings.setText("Idioma");
            }
        });
        btnInglesSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnInglesSettings.setBackgroundResource(R.drawable.ukroundactivada);
                btnCatalanSettings.setBackgroundResource(R.drawable.catround);
                btnEspanolSettings.setBackgroundResource(R.drawable.spainround);
                idiomaSeleccionado = 2;
                btnSave.setText("Save");
                txtSettings.setText("Settings");
                txtLanguageSettings.setText("Language");
            }
        });
    }

    //SETTEAMOS EL TEXTO DE LOS BOTONES SEGUN EL IDIOMA DEL JUGADOR.
    public void cambiarIdioma(Button btnSave, TextView txtSettings,
                              TextView txtLanguageSettings) {

        if (jugador.getIdioma() == 0) {
            btnSave.setText("Guardar");
            txtSettings.setText("Ajustos");
            txtLanguageSettings.setText("Idioma");
        } else if (jugador.getIdioma() == 1) {
            btnSave.setText("Guardar");
            txtSettings.setText("Ajustes");
            txtLanguageSettings.setText("Idioma");
        } else {
            btnSave.setText("Save");
            txtSettings.setText("Settings");
            txtLanguageSettings.setText("Language");
        }
    }

    //CON ESTO ANULAMOS EL BOTON DE VOLVER.
    @Override
    public void onBackPressed() {
    }
}