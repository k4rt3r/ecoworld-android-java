package com.example.ecoworld;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class ActivistActivity extends AppCompatActivity {

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
    JSONActivista[] activistas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activistas);

        TextView txtScoreActivistas = findViewById(R.id.txtScoreActivistas);
        ImageView imgActivistas = findViewById(R.id.imgActivistas);
        TextView txtExplicacionActivistas = findViewById(R.id.txtExplicacionActivistas);
        Button btnRankingActivistas = findViewById(R.id.btnRankingActivistas);


        Intent obtenerIntentJugador = getIntent();
        jugador = (Jugador) obtenerIntentJugador.getExtras().getSerializable(JUGADOR);

        activistas = leerJSONActivista();

        asignarActivistaCambiarIdioma(txtScoreActivistas, imgActivistas, btnRankingActivistas,
                txtExplicacionActivistas);

        btnRankingActivistas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent RankingActivity = new Intent(ActivistActivity.this,
                        RankingActivity.class);
                RankingActivity.putExtra(JUGADOR, jugador);
                startActivity(RankingActivity);
            }
        });
    }

    //AQUI LEEMOS EL JSON ACTIVISTA, LO CARGAMOS A UNA ARRAY STATIC Y LO RETORNAMOS.
    public JSONActivista[] leerJSONActivista() {

        String filePath;
        BufferedReader br;
        JSONActivista[] activistas = {};

        filePath = "/data/data/com.example.ecoworld/files/activistas.json";

        try {
            br = new BufferedReader(new FileReader(filePath));
            activistas = new Gson().fromJson(br, JSONActivista[].class);
            br.close();
        } catch (Exception e) {

        }
        return activistas;
    }

    //ASIGNAMOS ACTIVISTA AL JUGADOR Y SEGUN EL IDIOMA DEL JUGADOR SETTEAMOS EL TEXTO DEL ACTIVISTA, PUNTOS Y RANKING
    public void asignarActivistaCambiarIdioma(TextView txtScoreActivistas,
                                              ImageView imgActivistas,
                                              Button btnRankingActivistas,
                                              TextView txtExplicacionActivistas) {

        //RETORNAMOS UN ACTIVISTA SEGUN LA PUNTUACION
        JSONActivista activista = asignarActivista(activistas);

        jugador.setNombreActivista(activista.getNombre());

        //SETTEAMOS EL TEXTO DE LA FRASE ACTIVISTA, NOMBRE DEL ACTIVISTA Y LOS PUNTOS SEGUN EL IDIOMA DEL JUGADOR
        if (jugador.getIdioma() == 0) {  //CAT
            txtExplicacionActivistas.setText("ENHORABONA!\n\nHAS ACONSEGUIT EL NIVELL" +
                    " D'ACTIVISTA DE "
                    + activista.getNombre() + "\n\n" + "'" + activista.getFrasePersonaCat() +
                    "'");

            txtScoreActivistas.setText(("Punts: " + jugador.getPuntos()));
            btnRankingActivistas.setText(R.string.classificacio);
        } else if (jugador.getIdioma() == 1) { //ESP
            txtExplicacionActivistas.setText("¡ENHORABUENA!\n\nHAS CONSEGUIDO EL NIVEL" +
                    " DE ACTIVISTA DE "
                    + activista.getNombre() + "\n\n" + "'" + activista.getFrasePersonaEsp() + "'");
            txtScoreActivistas.setText(("Puntos: " + jugador.getPuntos()));
            btnRankingActivistas.setText(R.string.clasificacion);
        } else { //ENG
            txtExplicacionActivistas.setText("CONGRATULATIONS!\n\nYOU HAVE ACHIEVED" +
                    " THE ACTIVIST LEVEL OF "
                    + activista.getNombre() + "\n\n" + "'" + activista.getFrasePersonaEng()
                    + "'");
            txtScoreActivistas.setText(("Score: " + jugador.getPuntos()));
            btnRankingActivistas.setText(R.string.ranking);
        }

        //SEGUN LOS PUNTOS DEL JUGADOR ASIGNAMOS LA FOTO DEL ACTIVISTA.
        asignarFotoActivista();

        //SETTEAMOS LA IMAGEN DEL ACTIVISTA AL JUGADOR.
        imgActivistas.setImageResource(jugador.getFoto());
    }

    //SEGUN LOS PUNTOS DEL JUGADOR ASIGNAMOS LA FOTO DEL ACTIVISTA.
    public void asignarFotoActivista() {

        int puntuacion = jugador.getPuntos();

        if (puntuacion < 100) {
            jugador.setFoto(R.drawable.sunitanarain);
        } else if (puntuacion >= 100 && puntuacion < 200) {
            jugador.setFoto(R.drawable.sheilawattcloutier);
        } else if (puntuacion >= 200 && puntuacion < 300) {
            jugador.setFoto(R.drawable.mariasilbyllamerian);
        } else if (puntuacion >= 300 && puntuacion < 400) {
            jugador.setFoto(R.drawable.bertacaceres);
        } else if (puntuacion >= 400 && puntuacion < 500) {
            jugador.setFoto(R.drawable.vandanashiva);
        } else if (puntuacion >= 500 && puntuacion < 600) {
            jugador.setFoto(R.drawable.loisgibbs);
        } else if (puntuacion >= 600 && puntuacion < 700) {
            jugador.setFoto(R.drawable.wangarimaathai);
        } else if (puntuacion >= 700 && puntuacion < 800) {
            jugador.setFoto(R.drawable.rachelcarson);
        } else if (puntuacion >= 800 && puntuacion < 900) {
            jugador.setFoto(R.drawable.gretathunberg);
        } else {
            jugador.setFoto(R.drawable.janegoodall);
        }
    }

    //SI HAY MAS DE UN ACTIVISTA DEL MISMO RANGO DE PUNTOS LE ASIGNAMOS AL JUGADOR UNO ALEATORIO.
    public JSONActivista asignarActivista(JSONActivista[] activistas) {

        ArrayList<JSONActivista> activistasRepetidosRango = new ArrayList<JSONActivista>();

        int maximo = 0;
        int puntuacionJugador = jugador.getPuntos();
        JSONActivista activista;

        //BUSCA EL RANGO MÁXIMO AL QUE LA PUNTUACIÓN OBTENIDA POR EL JUGADOR LE TOCA
        //POR EJEMPLO SI EL JUGADOR SACA 550PUNTOS SE LE ASIGNA EL MÁXIMO A 500
        //HAY 10 RANGOS EN TOTAL
        for (int i = 0; i < activistas.length; i++) {
            if (puntuacionJugador >= activistas[i].getPuntuacion()) {
                maximo = activistas[i].getPuntuacion();
            }
        }
        //DEL RANGO MÁXIMO ASIGNADO AÑADIMOS AL ARRAYLIST LOS ACTIVISTAS QUE ESTEN EN ESE RANGO
        for (int i = 0; i < activistas.length; i++) {
            if (maximo == activistas[i].getPuntuacion()) {
                activistasRepetidosRango.add(activistas[i]);
            }
        }
        //AHORA DE LOS ACTIVISTAS DENTRO DEL RANGO, SELECCIONAMOS UNO DE MANERA ALEATORIA
        int randomMAX = activistasRepetidosRango.size();
        int random = (int) Math.floor(Math.random() * randomMAX);

        activista = activistasRepetidosRango.get(random);

        return activista;
    }

    //CON ESTO ANULAMOS EL BOTON DE VOLVER.
    @Override
    public void onBackPressed() {
    }
}