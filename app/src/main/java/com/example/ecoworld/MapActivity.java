package com.example.ecoworld;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MapActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_map);

        //ESTAS REFERENCIAS SE SETTEARAN SEGUN EL JSON SELECCIONADO.
        final TextView txtPreguntaMap = findViewById(R.id.txtPreguntaMap);
        final TextView txtExplicacionMap = findViewById(R.id.txtExplicacionMap);
        final Button respuesta1Map = findViewById(R.id.respuesta1Map);
        final Button respuesta2Map = findViewById(R.id.respuesta2Map);
        final Button respuesta3Map = findViewById(R.id.respuesta3Map);
        final Button respuesta4Map = findViewById(R.id.respuesta4Map);
        //ESTAS REFERENCIAS SE SETTEARAN MANUALMENTE.
        final TextView txtScoreMap = findViewById(R.id.txtScoreMap);
        final Button botonJugarMap = findViewById(R.id.botonJugarMap);
        final Button txtAyudasMap = findViewById(R.id.txtAyudasMap);
        final Button botonSalirMap = findViewById(R.id.botonSalirMap);
        final ImageView imgLogoMap = findViewById(R.id.imgLogoMap);
        imgLogoMap.setVisibility(View.INVISIBLE);
        txtExplicacionMap.setVisibility(View.INVISIBLE);
        botonJugarMap.setVisibility(View.INVISIBLE);
        final LinearLayout fondo = findViewById(R.id.layoutMapActivity);
        final TextView txtChronoMap = findViewById(R.id.txtChronoMap);
        final Drawable cambioEcoWorldAmerica = getResources()
                .getDrawable(R.drawable.siguienteamerica);
        final Drawable fondoMapActivityAmerica = getResources()
                .getDrawable(R.drawable.america);

        Intent obtenerIntentIntroActivity = getIntent();
        jugador = (Jugador) obtenerIntentIntroActivity.getExtras().getSerializable(JUGADOR);

        //COMO RECICLAMOS LA ACTIVITY HAY ELEMENTOS VISIBLES Y NO VISIBLES.
        txtChronoMap.setVisibility(View.GONE);
        respuesta1Map.setVisibility(View.GONE);
        respuesta2Map.setVisibility(View.GONE);
        respuesta3Map.setVisibility(View.GONE);
        respuesta4Map.setVisibility(View.GONE);

        botonJugarMap.setVisibility(View.VISIBLE);
        txtAyudasMap.setVisibility(View.VISIBLE);
        txtExplicacionMap.setVisibility(View.VISIBLE);

        imgLogoMap.setVisibility(View.INVISIBLE);
        txtPreguntaMap.setVisibility(View.INVISIBLE);

        botonJugarMap.setText(R.string.jugar);
        txtExplicacionMap.setText("");

        txtExplicacionMap.setBackground(cambioEcoWorldAmerica);
        fondo.setBackground(fondoMapActivityAmerica);

        refrescarPuntos(txtScoreMap);
        refrescarAyudas(txtAyudasMap);

        cambiarIdioma(botonSalirMap, botonJugarMap, txtAyudasMap);

        botonSalirMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ExitActivity = new Intent(MapActivity.this,
                        ExitActivity.class);
                ExitActivity.putExtra(JUGADOR, jugador);
                startActivity(ExitActivity);
            }
        });

        botonJugarMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent QuestionsActivity = new Intent(MapActivity.this,
                        QuestionsActivity.class);
                QuestionsActivity.putExtra(JUGADOR, jugador);
                startActivity(QuestionsActivity);
            }
        });

    }

    //SETTEAMOS EL TEXTO DE LOS BOTONES SEGUN EL IDIOMA DEL JUGADOR.
    public void cambiarIdioma(Button botonSalirMap, Button botonJugarMap,
                              TextView txtAyudasMap) {

        int idioma = jugador.getIdioma();

        if (idioma == 0) { //CAT
            botonSalirMap.setText(R.string.sortir);
            botonJugarMap.setText(R.string.jugar);
            txtAyudasMap.setText("Ajudes: " + jugador.getAyudas());
        } else if (idioma == 1) { //ESP
            botonSalirMap.setText(R.string.salir);
            botonJugarMap.setText(R.string.jugar);
            txtAyudasMap.setText("Ayudas: " + jugador.getAyudas());
        } else { //ENG
            botonSalirMap.setText(R.string.exit);
            botonJugarMap.setText(R.string.play);
            txtAyudasMap.setText("Helps: " + jugador.getAyudas());
        }
    }

    //ACTUALIZAMOS LAS AYUDAS RESTANTES.
    public void refrescarAyudas(TextView txtAyudasMap) {
        if (jugador.getIdioma() == 0) { //CAT
            txtAyudasMap.setText("Ajudes: " + jugador.getAyudas());
        } else if (jugador.getIdioma() == 1) { //ESP
            txtAyudasMap.setText("Ayudas: " + jugador.getAyudas());
        } else { //ENG
            txtAyudasMap.setText("Helps: " + jugador.getAyudas());
        }
    }

    //ACTUALIZAMOS LOS PUNTOS.
    public void refrescarPuntos(TextView txtScoreMap) {
        if (jugador.getIdioma() == 0) { //CAT
            txtScoreMap.setText("Punts: " + jugador.getPuntos());
        } else if (jugador.getIdioma() == 1) { //ESP
            txtScoreMap.setText("Puntos: " + jugador.getPuntos());
        } else { //ENG
            txtScoreMap.setText("Score: " + jugador.getPuntos());
        }
    }

    //CON ESTO ANULAMOS EL BOTON DE VOLVER.
    @Override
    public void onBackPressed() {
    }
}