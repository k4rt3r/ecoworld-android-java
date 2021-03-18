package com.example.ecoworld;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class IntroActivity extends AppCompatActivity {

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
    int contadorSi = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        final Button btnSi = findViewById(R.id.btnSi);
        final Button btnNo = findViewById(R.id.btnNo);
        final TextView logoBocadilloIntro = findViewById(R.id.logoBocadilloIntro);
        final TextView txtIntroduccion = findViewById(R.id.txtIntroduccion);
        final ImageView logotriste = findViewById(R.id.logotriste);
        final Drawable imgExplicacion = getResources()
                .getDrawable(R.drawable.logoexplicacion);

        Intent obtenerIntentJugador = getIntent();
        jugador = (Jugador) obtenerIntentJugador.getExtras().getSerializable(JUGADOR);

        cambiarIdioma(btnSi, btnNo, logoBocadilloIntro, txtIntroduccion);

        btnSi.setOnClickListener(new View.OnClickListener() {
            Intent MapActivity;

            @Override
            public void onClick(View v) {
                contadorSi++;
                if (contadorSi > 1) {
                    MapActivity = new Intent(IntroActivity.this,
                            MapActivity.class);
                    MapActivity.putExtra(JUGADOR, jugador);
                    startActivity(MapActivity);
                } else {
                    btnNo.setVisibility(View.GONE);
                    //AQUI CAMBIAMOS LOS PARAMETROS DEL LAYOUT DESDE CODIGO.
                    LinearLayout.LayoutParams params =
                            (LinearLayout.LayoutParams) btnSi.getLayoutParams();
                    params.setMargins(525, 0, 0, 10);
                    btnSi.setLayoutParams(params);
                    btnSi.setTextSize(20);
                    logotriste.setBackground(imgExplicacion);
                    params = (LinearLayout.LayoutParams) logoBocadilloIntro.getLayoutParams();
                    params.setMargins(200, 0, 0, 0);
                    logoBocadilloIntro.setLayoutParams(params);
                    logoBocadilloIntro.setPadding(10, 5, 10, 5);
                    params = (LinearLayout.LayoutParams) logotriste.getLayoutParams();
                    params.setMargins(100, 120, 0, 0);
                    logotriste.setLayoutParams(params);

                    //SETTEAMOS TODOS LOS TEXTOS CON EL IDIOMA DEL JUGADOR QUE SELECCIONO ANTERIORMENTE
                    if (jugador.getIdioma() == 0) {  //CAT
                        txtIntroduccion.setText(R.string.introCAT2);
                        logoBocadilloIntro.setText(R.string.explicacionCAT);
                        btnSi.setText(R.string.seguent);
                    } else if (jugador.getIdioma() == 1) {  //ESP
                        txtIntroduccion.setText(R.string.introESP2);
                        logoBocadilloIntro.setText(R.string.explicacionESP);
                        btnSi.setText(R.string.siguiente);
                    } else {  //ENG
                        txtIntroduccion.setText(R.string.introENG2);
                        logoBocadilloIntro.setText(R.string.explicacionENG);
                        btnSi.setText(R.string.next);
                    }
                }
            }
        });

        btnNo.setOnClickListener(new View.OnClickListener() {
            Intent MainActivity;

            @Override
            public void onClick(View v) {
                MainActivity = new Intent(IntroActivity.this,
                        MainActivity.class);
                startActivity(MainActivity);
            }
        });
    }

    //SETTEAMOS EL TEXTO DE LA INTRODUCCIÓN SEGUN EL IDIOMA DEL JUGADOR.
    public void cambiarIdioma(Button btnSi, Button btnNo,
                              TextView logoBocadilloIntro, TextView txtIntroduccion) {

        int idioma = jugador.getIdioma();

        if (idioma == 0) { //CAT
            btnSi.setText(R.string.si);
            btnNo.setText(R.string.no);
            logoBocadilloIntro.setText(R.string.introduccionCAT);
            txtIntroduccion.setText(R.string.introCAT);
        } else if (idioma == 1) { //ESP
            btnSi.setText(R.string.sii);
            btnNo.setText(R.string.no);
            logoBocadilloIntro.setText(R.string.introduccionESP);
            txtIntroduccion.setText(R.string.introESP);
        } else { //ENG
            btnSi.setText(R.string.yes);
            btnNo.setText(R.string.no);
            logoBocadilloIntro.setText(R.string.introduccionENG);
            txtIntroduccion.setText(R.string.introENG);
        }
    }

    //CON ESTO ANULAMOS EL BOTON DE VOLVER.
    @Override
    public void onBackPressed() {
    }
}