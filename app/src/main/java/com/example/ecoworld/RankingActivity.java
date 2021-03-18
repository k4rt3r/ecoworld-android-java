package com.example.ecoworld;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;

public class RankingActivity extends AppCompatActivity {

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
    public static final String RUTA = "/data/data/com.example.ecoworld/files/jugadores.txt";

    Jugador jugador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        final TextView txtActivistName = findViewById(R.id.txtActivistName);
        final ImageView imgActivist = findViewById(R.id.imgActivist);
        final ListView lstRanking = findViewById(R.id.lstRanking);
        final Button btnCredits = findViewById(R.id.btnCredits);
        final Button btnPlayAgain = findViewById(R.id.btnPlayAgain);
        final Button btnExitRanking = findViewById(R.id.btnExitRanking);
        final TextView txtScoreRanking = findViewById(R.id.txtScoreRanking);
        final TextView txtClasificacion = findViewById(R.id.txtClasificacion);
        final TextView txtNombreUsuario = findViewById(R.id.txtNombreUsuario);
        final TextView txtPuntosUsuario = findViewById(R.id.txtPuntosUsuario);

        Intent obtenerIntentJugador = getIntent();
        jugador = (Jugador) obtenerIntentJugador.getExtras().getSerializable(JUGADOR);

        Jugador[] jugadores = leerArchivoJugadores();
        jugadores = ordenarRanking(jugadores);

        try {
            grabarArchivoJugadores(jugadores);
        } catch (Exception e) {

        }

        //Obtenemos una referencia al ListView
        ListView lstJugadores = findViewById(R.id.lstRanking);
        //Creamos nuestro adapter modificado
        JugadorAdapter adapter = new JugadorAdapter(this, jugadores);
        //Pasamos los datos al adapter modificado para que los muestre en la View de forma correcta
        lstJugadores.setAdapter(adapter);

        lstRanking.setEnabled(false);

        txtActivistName.setText(jugador.getNombreActivista());
        imgActivist.setImageResource(jugador.getFoto());

        cambiarIdioma(btnCredits, btnPlayAgain, btnExitRanking, txtScoreRanking,
                txtClasificacion, txtNombreUsuario, txtPuntosUsuario);

        btnCredits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent CreditsActivity = new Intent(RankingActivity.this,
                        CreditsActivity.class);
                CreditsActivity.putExtra(JUGADOR, jugador);
                startActivity(CreditsActivity);
            }
        });

        btnPlayAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent MainActivity = new Intent(RankingActivity.this,
                        MainActivity.class);
                startActivity(MainActivity);
            }
        });

        btnExitRanking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ExitActivity = new Intent(RankingActivity.this,
                        ExitActivity.class);
                ExitActivity.putExtra(JUGADOR, jugador);
                startActivity(ExitActivity);
            }
        });
    }

    //ORDENA EL ARRAY STATIC DE JUGADORES Y LO DEVUELVE ORDENADO (MAYOR A MENOR PUNTOS).
    public Jugador[] ordenarRanking(Jugador[] jugadores) {

        Jugador aux;
        int contador = 0;

        for (int i = 0; i < jugadores.length; i++) {
            if (jugadores[i] != null) {
                contador++;
            }
        }

        for (int i = 1; i < contador; i++) {
            for (int j = contador - 1; j >= i; j--) {
                if (jugadores[j - 1].getPuntos() < jugadores[j].getPuntos()) {
                    aux = jugadores[j];
                    jugadores[j] = jugadores[j - 1];
                    jugadores[j - 1] = aux;
                }
            }
        }

        return jugadores;
    }

    //SETTEAMOS EL TEXTO DE LOS BOTONES SEGUN EL IDIOMA DEL JUGADOR.
    public void cambiarIdioma(Button btnCredits, Button btnPlayAgain, Button btnExitRanking,
                              TextView txtScoreRanking, TextView txtClasificacion,
                              TextView txtNombreUsuario, TextView txtPuntosUsuario) {

        if (jugador.getIdioma() == 0) {  //CAT
            btnCredits.setText(R.string.credits);
            btnPlayAgain.setText(R.string.jugarNou);
            btnExitRanking.setText(R.string.sortir);
            txtScoreRanking.setText("Punts: " + jugador.getPuntos());
            txtClasificacion.setText(R.string.posicio);
            txtNombreUsuario.setText(R.string.usuari);
            txtPuntosUsuario.setText("Punts: ");
        } else if (jugador.getIdioma() == 1) { //ESP
            btnCredits.setText(R.string.creditos);
            btnPlayAgain.setText(R.string.jugarNuevo);
            btnExitRanking.setText(R.string.salir);
            txtScoreRanking.setText("Puntos: " + jugador.getPuntos());
            txtClasificacion.setText(R.string.posicion);
            txtNombreUsuario.setText(R.string.usuario);
            txtPuntosUsuario.setText("Puntos: ");
        } else { //ENG
            btnCredits.setText(R.string.creditseng);
            btnPlayAgain.setText(R.string.playAgain);
            btnExitRanking.setText(R.string.exit);
            txtScoreRanking.setText("Score: " + jugador.getPuntos());
            txtClasificacion.setText(R.string.position);
            txtNombreUsuario.setText(R.string.username);
            txtPuntosUsuario.setText("Score: ");
        }
    }

    //GUARDAMOS EL ARRAY DE JUGADORES EN UN .TXT
    public void grabarArchivoJugadores(Jugador[] jugadores)
            throws Exception {

        try {
            FileWriter fw = new FileWriter(RUTA);

            for (int i = 0; i < jugadores.length; i++) {
                fw.write(jugadores[i].toString() + "\n");
            }

            fw.close();
        } catch (Exception e) {

        }
    }


    public Jugador[] leerArchivoJugadores() {

        int contador = 0;

        try {

            FileReader fr = new FileReader(RUTA);
            BufferedReader br = new BufferedReader(fr);

            String line = "";

            while (line != null) {

                line = br.readLine();

                if (line != null && !line.equals("")) {

                    contador++;

                }
            }
            fr.close();

        } catch (Exception e) {

        }
        //AQUI ASIGNAMOS UNA POSICIÓN EXTRA Y ESTARÁN TODOS NULL
        Jugador[] jugadores = new Jugador[contador + 1];


        int contadorJugadores = 0;
        //AQUI LEEMOS DEL ARCHIVO Y ESCRIBIMOS EN EL ARRAY
        try {

            String[] parts = {};
            String nombre = "";
            String puntos = "";

            FileReader fr = new FileReader(RUTA);
            BufferedReader br = new BufferedReader(fr);

            String line = "";

            while (contadorJugadores <= contador) {

                if (contadorJugadores == 0 && contador != 0) {
                    line = br.readLine();
                    parts = line.split(";");
                    nombre = parts[0];
                    puntos = parts[1];

                    Jugador nuevoJugador = new Jugador
                            (nombre, Integer.parseInt(puntos));

                    jugadores[contadorJugadores] = nuevoJugador;
                    contadorJugadores++;
                }

                if (contadorJugadores < contador) {
                    line = br.readLine();
                    parts = line.split(";");
                    nombre = parts[0];
                    puntos = parts[1];

                    Jugador nuevoJugador = new Jugador
                            (nombre, Integer.parseInt(puntos));

                    jugadores[contadorJugadores] = nuevoJugador;
                } else {
                    jugadores[contador] = jugador;
                }

                contadorJugadores++;

            }

            fr.close();

        } catch (Exception e) {

        }

        return jugadores;
    }

    //CON ESTO ANULAMOS EL BOTON DE VOLVER.
    @Override
    public void onBackPressed() {
    }
}