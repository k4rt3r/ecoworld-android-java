package com.example.ecoworld;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;

public class QuestionsActivity extends AppCompatActivity {

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
    public static final int EXIT_ACTIVITY = 10;
    public static final String CONTADOR_PREGUNTAS = "contadorPreguntas";

    Jugador jugador;
    JSONPregunta[] preguntas;
    CountDownTimer countDownTimer;
    String respuestaCorrectaTexto;
    Intent obtenerIntentJugador;
    int contadorNull = 0;
    int contadorPreguntas = 1;
    int obtenerPosicionCorrecta;
    int cantidadPreguntas = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

        obtenerIntentJugador = getIntent();
        jugador = (Jugador) obtenerIntentJugador.getExtras().getSerializable(JUGADOR);

        //ESTAS REFERENCIAS SE SETTEARAN SEGUN EL JSON SELECCIONADO.
        final TextView txtPreguntaQuestions = findViewById(R.id.txtPreguntaQuestions);
        final TextView txtExplicacionQuestions = findViewById(R.id.txtExplicacionQuestions);
        final Button respuesta1Questions = findViewById(R.id.respuesta1Questions);
        final Button respuesta2Questions = findViewById(R.id.respuesta2Questions);
        final Button respuesta3Questions = findViewById(R.id.respuesta3Questions);
        final Button respuesta4Questions = findViewById(R.id.respuesta4Questions);
        //ESTAS REFERENCIAS SE SETTEARAN MANUALMENTE.
        final TextView txtScoreQuestions = findViewById(R.id.txtScoreQuestions);
        final Button botonJugarQuestions = findViewById(R.id.botonJugarQuestions);
        final Button txtAyudasQuestions = findViewById(R.id.txtAyudasQuestions);
        final Button botonSalirQuestions = findViewById(R.id.botonSalirQuestions);
        final ImageView imgLogoPreguntas = findViewById(R.id.imgLogoPreguntas);
        final TextView txtNumQuestions = findViewById(R.id.txtNumQuestions);
        final LinearLayout fondo = findViewById(R.id.layoutQuestionsActivity);
        final TextView txtChrono = findViewById(R.id.txtChrono);
        final int rojo = getResources().getColor(R.color.rojo);
        final int black = getResources().getColor(R.color.black);
        final Drawable respuestaCorrecta = getResources()
                .getDrawable(R.drawable.buttonrespuestacorrectaconborde);
        final Drawable respuestaIncorrecta = getResources()
                .getDrawable(R.drawable.buttonrespuestaincorrectaconborde);
        final Drawable respuestaInicial = getResources()
                .getDrawable(R.drawable.buttonrespuestagenericaconborde);
        final Drawable cambioTxtExplicacion = getResources()
                .getDrawable(R.drawable.cuadroexplicacionconborde);
        final Drawable fondoQuestionsActivityAfrica = getResources()
                .getDrawable(R.drawable.africaselected);
        final Drawable fondoQuestionsActivityEuropa = getResources()
                .getDrawable(R.drawable.europaselected);
        final Drawable fondoQuestionsActivityAsia = getResources()
                .getDrawable(R.drawable.asiaselected);
        final Drawable fondoQuestionsActivityOceania = getResources()
                .getDrawable(R.drawable.oceaniaselected);
        final Drawable fondoMapActivityAfrica = getResources()
                .getDrawable(R.drawable.africa);
        final Drawable fondoMapActivityEuropa = getResources()
                .getDrawable(R.drawable.europa);
        final Drawable fondoMapActivityAsia = getResources()
                .getDrawable(R.drawable.asia);
        final Drawable fondoMapActivityOceania = getResources()
                .getDrawable(R.drawable.oceania);
        final Drawable cambioEcoWorldAfrica = getResources()
                .getDrawable(R.drawable.siguienteafrica);
        final Drawable cambioEcoWorldEuropa = getResources()
                .getDrawable(R.drawable.siguienteeuropa);
        final Drawable cambioEcoWorldAsia = getResources()
                .getDrawable(R.drawable.siguienteasia);
        final Drawable cambioEcoWorldOceania = getResources()
                .getDrawable(R.drawable.siguienteoceania);

        //COMO RECICLAMOS LA ACTIVITY HAY ELEMENTOS VISIBLES Y NO VISIBLES.
        imgLogoPreguntas.setVisibility(View.INVISIBLE);
        txtExplicacionQuestions.setVisibility(View.INVISIBLE);
        botonJugarQuestions.setVisibility(View.INVISIBLE);

        obtenerIntentJugador = getIntent();
        jugador = (Jugador) obtenerIntentJugador.getExtras().getSerializable(JUGADOR);

        txtNumQuestions.setText(cantidadPreguntas + "/20");

        cambiarIdioma(txtScoreQuestions, botonJugarQuestions, txtAyudasQuestions,
                botonSalirQuestions, txtExplicacionQuestions, respuesta1Questions,
                respuesta2Questions, respuesta3Questions, respuesta4Questions);

        leerJSON();

        generarPreguntasRespuestasAleatorias(txtPreguntaQuestions,
                txtExplicacionQuestions,
                respuesta1Questions,
                respuesta2Questions, respuesta3Questions,
                respuesta4Questions);

        chrono(black, txtChrono, rojo, respuesta1Questions, respuesta2Questions, respuesta3Questions,
                respuesta4Questions, respuestaCorrecta, respuestaIncorrecta, imgLogoPreguntas,
                txtExplicacionQuestions, botonJugarQuestions, txtAyudasQuestions);

        botonSalirQuestions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ExitActivity = new Intent(QuestionsActivity.this,
                        ExitActivity.class);
                ExitActivity.putExtra(JUGADOR, jugador);
                startActivityForResult(ExitActivity, EXIT_ACTIVITY);
            }
        });

        respuesta1Questions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //MÉTODO PARA CONTROLAR ERRORES DE USUARIO UNA VEZ FINALIZA EL TIEMPO O HACE CLICK EN UNA RESPUESTA
                controlarQuestionsActivityChrono0ButtonClick(respuesta1Questions,
                        respuesta2Questions, respuesta3Questions, respuesta4Questions,
                        respuestaCorrecta, respuestaIncorrecta, imgLogoPreguntas,
                        txtExplicacionQuestions, botonJugarQuestions, txtAyudasQuestions);
                if (obtenerPosicionCorrecta == 0) {
                    jugador.setPuntos(jugador.getPuntos() + 50);
                    refrescarPuntos(txtScoreQuestions);
                    soundButtonOK();
                } else {
                    soundButtonNOTOK();
                }

            }
        });

        respuesta2Questions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //MÉTODO PARA CONTROLAR ERRORES DE USUARIO UNA VEZ FINALIZA EL TIEMPO O HACE CLICK EN UNA RESPUESTA
                controlarQuestionsActivityChrono0ButtonClick(respuesta1Questions,
                        respuesta2Questions, respuesta3Questions, respuesta4Questions,
                        respuestaCorrecta, respuestaIncorrecta, imgLogoPreguntas,
                        txtExplicacionQuestions, botonJugarQuestions, txtAyudasQuestions);
                if (obtenerPosicionCorrecta == 1) {
                    jugador.setPuntos(jugador.getPuntos() + 50);
                    refrescarPuntos(txtScoreQuestions);
                    soundButtonOK();
                } else {
                    soundButtonNOTOK();
                }
            }
        });

        respuesta3Questions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //MÉTODO PARA CONTROLAR ERRORES DE USUARIO UNA VEZ FINALIZA EL TIEMPO O HACE CLICK EN UNA RESPUESTA
                controlarQuestionsActivityChrono0ButtonClick(respuesta1Questions,
                        respuesta2Questions, respuesta3Questions, respuesta4Questions,
                        respuestaCorrecta, respuestaIncorrecta, imgLogoPreguntas,
                        txtExplicacionQuestions, botonJugarQuestions, txtAyudasQuestions);
                if (obtenerPosicionCorrecta == 2) {
                    jugador.setPuntos(jugador.getPuntos() + 50);
                    refrescarPuntos(txtScoreQuestions);
                    soundButtonOK();
                } else {
                    soundButtonNOTOK();
                }
            }
        });

        respuesta4Questions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //MÉTODO PARA CONTROLAR ERRORES DE USUARIO UNA VEZ FINALIZA EL TIEMPO O HACE CLICK EN UNA RESPUESTA
                controlarQuestionsActivityChrono0ButtonClick(respuesta1Questions,
                        respuesta2Questions, respuesta3Questions, respuesta4Questions,
                        respuestaCorrecta, respuestaIncorrecta, imgLogoPreguntas,
                        txtExplicacionQuestions, botonJugarQuestions, txtAyudasQuestions);
                if (obtenerPosicionCorrecta == 3) {
                    jugador.setPuntos(jugador.getPuntos() + 50);
                    refrescarPuntos(txtScoreQuestions);
                    soundButtonOK();
                } else {
                    soundButtonNOTOK();
                }
            }
        });

        txtAyudasQuestions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //AQUI PROGRAMAMOS LA AYUDA Y QUE SE BORREN DOS
                ArrayList<Integer> posicionesRespuestas = new ArrayList<>();
                int randomRes;
                int posicionBucle;
                Boolean find = false;
                ListIterator lit = posicionesRespuestas.listIterator();
                jugador.setAyudas(jugador.getAyudas() - 1);

                /*ALGORITMO PARA CONTROLAR QUE GENERE DE MANERA ALETAORIA LAS RESPUESTAS
                Y PONGA INVISIBLES DOS Y COMPROBAR QUE NO PONGA INVISIBLE LA CORRECTA*/
                for (int i = 0; i < 2; i++) {

                    find = false;

                    do {
                        randomRes = (int) Math.floor(Math.random() * 3);
                    } while (obtenerPosicionCorrecta == randomRes);

                    lit = posicionesRespuestas.listIterator();

                    if (i == 0) {
                        posicionesRespuestas.add(randomRes);

                        switch (randomRes) {
                            case 0:
                                respuesta1Questions.setVisibility(View.INVISIBLE);
                                break;
                            case 1:
                                respuesta2Questions.setVisibility(View.INVISIBLE);
                                break;
                            case 2:
                                respuesta3Questions.setVisibility(View.INVISIBLE);
                                break;
                            case 3:
                                respuesta4Questions.setVisibility(View.INVISIBLE);
                                break;
                        }
                    }
                    do {
                        if (find) {

                            do {
                                randomRes = (int) Math.floor(Math.random() * 3);
                            } while (obtenerPosicionCorrecta == randomRes);

                            lit = posicionesRespuestas.listIterator();
                        }

                        find = false;

                        if (i != 0) {
                            while (lit.hasNext()) {
                                posicionBucle = (int) lit.next();
                                if (posicionBucle == randomRes) {
                                    find = true;
                                }
                            }

                            if (!find) {
                                posicionesRespuestas.add(randomRes);
                                switch (randomRes) {
                                    case 0:
                                        respuesta1Questions.setVisibility(View.INVISIBLE);
                                        break;
                                    case 1:
                                        respuesta2Questions.setVisibility(View.INVISIBLE);
                                        break;
                                    case 2:
                                        respuesta3Questions.setVisibility(View.INVISIBLE);
                                        break;
                                    case 3:
                                        respuesta4Questions.setVisibility(View.INVISIBLE);
                                        break;
                                }
                            }
                        }
                    } while (find);

                }

                txtAyudasQuestions.setVisibility(View.INVISIBLE);

            }
        });

        botonJugarQuestions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contadorPreguntas++;
                cantidadPreguntas++;
                refrescarNumPreguntas();
                Intent retrocederMapActivity =
                        new Intent(QuestionsActivity.this, MapActivity.class);
                retrocederMapActivity.putExtra(JUGADOR, jugador);
                retrocederMapActivity.putExtra(CONTADOR_PREGUNTAS, contadorPreguntas);

                if (contadorPreguntas < 25) {
                    if (contadorPreguntas == 5) {
                        txtChrono.setVisibility(View.INVISIBLE);
                        txtPreguntaQuestions.setVisibility(View.INVISIBLE);
                        respuesta1Questions.setVisibility(View.GONE);
                        respuesta2Questions.setVisibility(View.GONE);
                        respuesta3Questions.setVisibility(View.GONE);
                        respuesta4Questions.setVisibility(View.GONE);
                        botonJugarQuestions.setText(R.string.jugar);
                        imgLogoPreguntas.setVisibility(View.INVISIBLE);
                        txtAyudasQuestions.setVisibility(View.VISIBLE);
                        txtNumQuestions.setVisibility(View.INVISIBLE);
                        cantidadPreguntas--;
                        txtExplicacionQuestions.setBackground(cambioEcoWorldAfrica);
                        txtExplicacionQuestions.setText("");
                        fondo.setBackground(fondoMapActivityAfrica);

                        cambiarIdioma(txtScoreQuestions, botonJugarQuestions,
                                txtAyudasQuestions, botonSalirQuestions, txtExplicacionQuestions,
                                respuesta1Questions, respuesta2Questions, respuesta3Questions,
                                respuesta4Questions);

                    } else if (contadorPreguntas == 10) {
                        txtChrono.setVisibility(View.INVISIBLE);
                        txtPreguntaQuestions.setVisibility(View.INVISIBLE);
                        respuesta1Questions.setVisibility(View.GONE);
                        respuesta2Questions.setVisibility(View.GONE);
                        respuesta3Questions.setVisibility(View.GONE);
                        respuesta4Questions.setVisibility(View.GONE);
                        botonJugarQuestions.setText(R.string.jugar);
                        imgLogoPreguntas.setVisibility(View.INVISIBLE);
                        txtAyudasQuestions.setVisibility(View.VISIBLE);
                        txtNumQuestions.setVisibility(View.INVISIBLE);
                        cantidadPreguntas--;
                        txtExplicacionQuestions.setBackground(cambioEcoWorldEuropa);
                        txtExplicacionQuestions.setText("");
                        fondo.setBackground(fondoMapActivityEuropa);

                        cambiarIdioma(txtScoreQuestions, botonJugarQuestions,
                                txtAyudasQuestions, botonSalirQuestions, txtExplicacionQuestions,
                                respuesta1Questions, respuesta2Questions, respuesta3Questions,
                                respuesta4Questions);
                    } else if (contadorPreguntas == 15) {
                        txtChrono.setVisibility(View.INVISIBLE);
                        txtPreguntaQuestions.setVisibility(View.INVISIBLE);
                        respuesta1Questions.setVisibility(View.GONE);
                        respuesta2Questions.setVisibility(View.GONE);
                        respuesta3Questions.setVisibility(View.GONE);
                        respuesta4Questions.setVisibility(View.GONE);
                        botonJugarQuestions.setText(R.string.jugar);
                        imgLogoPreguntas.setVisibility(View.INVISIBLE);
                        txtAyudasQuestions.setVisibility(View.VISIBLE);
                        txtNumQuestions.setVisibility(View.INVISIBLE);
                        cantidadPreguntas--;
                        txtExplicacionQuestions.setBackground(cambioEcoWorldAsia);
                        txtExplicacionQuestions.setText("");
                        fondo.setBackground(fondoMapActivityAsia);

                        cambiarIdioma(txtScoreQuestions, botonJugarQuestions,
                                txtAyudasQuestions, botonSalirQuestions, txtExplicacionQuestions,
                                respuesta1Questions, respuesta2Questions, respuesta3Questions,
                                respuesta4Questions);

                    } else if (contadorPreguntas == 20) {
                        txtChrono.setVisibility(View.INVISIBLE);
                        txtPreguntaQuestions.setVisibility(View.INVISIBLE);
                        respuesta1Questions.setVisibility(View.GONE);
                        respuesta2Questions.setVisibility(View.GONE);
                        respuesta3Questions.setVisibility(View.GONE);
                        respuesta4Questions.setVisibility(View.GONE);
                        botonJugarQuestions.setText(R.string.jugar);
                        imgLogoPreguntas.setVisibility(View.INVISIBLE);
                        txtAyudasQuestions.setVisibility(View.VISIBLE);
                        txtNumQuestions.setVisibility(View.INVISIBLE);
                        cantidadPreguntas--;
                        txtExplicacionQuestions.setBackground(cambioEcoWorldOceania);
                        txtExplicacionQuestions.setText("");
                        fondo.setBackground(fondoMapActivityOceania);

                        cambiarIdioma(txtScoreQuestions, botonJugarQuestions,
                                txtAyudasQuestions, botonSalirQuestions, txtExplicacionQuestions,
                                respuesta1Questions, respuesta2Questions, respuesta3Questions,
                                respuesta4Questions);

                    } else {
                        chrono(black, txtChrono, rojo, respuesta1Questions, respuesta2Questions, respuesta3Questions,
                                respuesta4Questions, respuestaCorrecta, respuestaIncorrecta, imgLogoPreguntas,
                                txtExplicacionQuestions, botonJugarQuestions, txtAyudasQuestions);
                        generarPreguntasRespuestasAleatorias(txtPreguntaQuestions,
                                txtExplicacionQuestions,
                                respuesta1Questions,
                                respuesta2Questions, respuesta3Questions,
                                respuesta4Questions);
                        refrescarQuestions(txtExplicacionQuestions, botonJugarQuestions, txtAyudasQuestions,
                                imgLogoPreguntas, respuesta1Questions,
                                respuesta2Questions, respuesta3Questions, respuesta4Questions,
                                respuestaInicial);
                        cambiarIdioma(txtScoreQuestions, botonJugarQuestions,
                                txtAyudasQuestions, botonSalirQuestions, txtExplicacionQuestions,
                                respuesta1Questions, respuesta2Questions, respuesta3Questions,
                                respuesta4Questions);
                    }
                }

                //AQUI CAMBIAMOS EL FONDO DEL QUESTIONS ACTIVITY CADA 4 PREGUNTAS Y EN LA ÚLTIMA NOS LLEVA A LA SIGUIENTE ACTIVITY
                switch (contadorPreguntas) {
                    case 6:
                        fondo.setBackground(fondoQuestionsActivityAfrica);
                        txtPreguntaQuestions.setVisibility(View.VISIBLE);
                        txtNumQuestions.setVisibility(View.VISIBLE);
                        txtChrono.setVisibility(View.VISIBLE);
                        botonJugarQuestions.setText(R.string.siguiente);
                        txtExplicacionQuestions.setBackground(cambioTxtExplicacion);
                        cambiarIdioma(txtScoreQuestions, botonJugarQuestions,
                                txtAyudasQuestions, botonSalirQuestions, txtExplicacionQuestions,
                                respuesta1Questions, respuesta2Questions, respuesta3Questions,
                                respuesta4Questions);
                        break;
                    case 11:
                        fondo.setBackground(fondoQuestionsActivityEuropa);
                        txtPreguntaQuestions.setVisibility(View.VISIBLE);
                        txtNumQuestions.setVisibility(View.VISIBLE);
                        txtChrono.setVisibility(View.VISIBLE);
                        botonJugarQuestions.setText(R.string.siguiente);
                        txtExplicacionQuestions.setBackground(cambioTxtExplicacion);
                        cambiarIdioma(txtScoreQuestions, botonJugarQuestions,
                                txtAyudasQuestions, botonSalirQuestions, txtExplicacionQuestions,
                                respuesta1Questions, respuesta2Questions, respuesta3Questions,
                                respuesta4Questions);
                        break;
                    case 16:
                        fondo.setBackground(fondoQuestionsActivityAsia);
                        txtPreguntaQuestions.setVisibility(View.VISIBLE);
                        txtChrono.setVisibility(View.VISIBLE);
                        txtNumQuestions.setVisibility(View.VISIBLE);
                        botonJugarQuestions.setText(R.string.siguiente);
                        txtExplicacionQuestions.setBackground(cambioTxtExplicacion);
                        cambiarIdioma(txtScoreQuestions, botonJugarQuestions,
                                txtAyudasQuestions, botonSalirQuestions, txtExplicacionQuestions,
                                respuesta1Questions, respuesta2Questions, respuesta3Questions,
                                respuesta4Questions);

                        break;
                    case 21:
                        fondo.setBackground(fondoQuestionsActivityOceania);
                        txtPreguntaQuestions.setVisibility(View.VISIBLE);
                        txtChrono.setVisibility(View.VISIBLE);
                        txtNumQuestions.setVisibility(View.VISIBLE);
                        botonJugarQuestions.setText(R.string.siguiente);
                        txtExplicacionQuestions.setBackground(cambioTxtExplicacion);
                        cambiarIdioma(txtScoreQuestions, botonJugarQuestions,
                                txtAyudasQuestions, botonSalirQuestions, txtExplicacionQuestions,
                                respuesta1Questions, respuesta2Questions, respuesta3Questions,
                                respuesta4Questions);
                        break;
                    case 25:
                        Intent WinActivity =
                                new Intent(QuestionsActivity.this, WinActivity.class);
                        WinActivity.putExtra(JUGADOR, jugador);
                        startActivity(WinActivity);
                        break;
                }
            }
        });
    }
/*
//SOLO FUNCIONA EN MÓVILES
    public void vibratorButton() {
        Vibrator vibrator = (Vibrator) getApplicationContext()
                .getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(2000);
    }
*/

    //ACTUALIZAMOS EL NUMERO DE PREGUNTA ACTUAL
    public void refrescarNumPreguntas() {

        final TextView txtNumQuestions = findViewById(R.id.txtNumQuestions);

        if (contadorPreguntas < 25) {
            txtNumQuestions.setText(cantidadPreguntas + "/20");
        }
    }

    //SI LA RESPUESTA ES CORRECTA SUENA ESTE SONIDO.
    public void soundButtonOK() {
        MediaPlayer mp = MediaPlayer.create(QuestionsActivity.this, R.raw.bien);
        mp.start();
    }

    //SI LA RESPUESTA ES INCORRECTA SUENA ESTE SONIDO.
    public void soundButtonNOTOK() {
        MediaPlayer mp = MediaPlayer.create(QuestionsActivity.this, R.raw.mal);
        mp.start();
    }

    //SI FINALIZA EL CRONO SUENA ESTE SONIDO.
    public void soundButtonChrono() {
        MediaPlayer mp = MediaPlayer.create(QuestionsActivity.this, R.raw.despertador);
        mp.start();
    }

    //SETTEAMOS EL TEXTO DE LOS BOTONES SEGUN EL IDIOMA DEL JUGADOR.
    public void cambiarIdioma(TextView txtScoreQuestions,
                              Button botonJugarQuestions, Button txtAyudasQuestions,
                              Button botonSalirQuestions, TextView txtExplicacionQuestions,
                              Button respuesta1Questions, Button respuesta2Questions,
                              Button respuesta3Questions, Button respuesta4Questions) {

        int idioma = jugador.getIdioma();

        if (idioma == 0) { //CAT
            txtScoreQuestions.setText("Punts: " + jugador.getPuntos());
            botonJugarQuestions.setText(R.string.seguent);
            txtAyudasQuestions.setText("Ajudes: " + jugador.getAyudas());
            botonSalirQuestions.setText(R.string.sortir);
        } else if (idioma == 1) { //ESP
            txtScoreQuestions.setText("Puntos: " + jugador.getPuntos());
            botonJugarQuestions.setText(R.string.siguiente);
            txtAyudasQuestions.setText("Ayudas: " + jugador.getAyudas());
            botonSalirQuestions.setText(R.string.salir);
        } else { //ENG
            txtScoreQuestions.setText("Score: " + jugador.getPuntos());
            botonJugarQuestions.setText(R.string.next);
            txtAyudasQuestions.setText("Helps: " + jugador.getAyudas());
            botonSalirQuestions.setText(R.string.exit);
        }
        //AQUI CAMBIAMOS LOS PARAMETROS DEL LAYOUT DESDE CODIGO.
        txtExplicacionQuestions.setPadding(50, 40, 40, 50);
        respuesta1Questions.setPadding(40, 30, 30, 40);
        respuesta2Questions.setPadding(40, 30, 30, 40);
        respuesta3Questions.setPadding(40, 30, 30, 40);
        respuesta4Questions.setPadding(40, 30, 30, 40);
        respuesta1Questions.setTextSize(12);
        respuesta2Questions.setTextSize(12);
        respuesta3Questions.setTextSize(12);
        respuesta4Questions.setTextSize(12);
    }


    //ACTUALIZA LOS PUNTOS DEL JUGADOR
    public void refrescarPuntos(TextView txtScoreQuestions) {

        if (jugador.getIdioma() == 0) { //CAT
            txtScoreQuestions.setText("Punts: " + jugador.getPuntos());
        } else if (jugador.getIdioma() == 1) { //ESP
            txtScoreQuestions.setText("Puntos: " + jugador.getPuntos());
        } else { //ENG
            txtScoreQuestions.setText("Score: " + jugador.getPuntos());
        }
    }

    //AQUI LEEMOS EL JSON DEPENDIENDO DEL IDIOMA Y DIFICULTAD DEL JUGADOR QUE PREVIAMENTE SELECCIONO
    public void leerJSON() {

        int dificultadJSON = jugador.getDificultad();
        int idiomaJSON = jugador.getIdioma();
        String filePath;
        BufferedReader br;

        if (dificultadJSON == 0 && idiomaJSON == 0) {//AQUI CARGAMOS CATALÁN FÁCIL

            filePath = "/data/data/com.example.ecoworld/files/catalanFacil.json";

            try {
                br = new BufferedReader(new FileReader(filePath));
                preguntas = new Gson().fromJson(br, JSONPregunta[].class);
                br.close();
            } catch (Exception e) {

            }

        } else if (dificultadJSON == 1 && idiomaJSON == 0) {//AQUI CARGAMOS CATALÁN NORMAL

            filePath = "/data/data/com.example.ecoworld/files/catalanNormal.json";

            try {
                br = new BufferedReader(new FileReader(filePath));
                preguntas = new Gson().fromJson(br, JSONPregunta[].class);
                br.close();
            } catch (Exception e) {

            }

        } else if (dificultadJSON == 2 && idiomaJSON == 0) {    //AQUI CARGAMOS CATALÁN DIFÍCIL

            filePath = "/data/data/com.example.ecoworld/files/catalanDificil.json";

            try {
                br = new BufferedReader(new FileReader(filePath));
                preguntas = new Gson().fromJson(br, JSONPregunta[].class);
                br.close();
            } catch (Exception e) {

            }

        } else if (dificultadJSON == 0 && idiomaJSON == 1) {    //AQUI CARGAMOS ESPAÑOL FÁCIL

            filePath = "/data/data/com.example.ecoworld/files/castellanoFacil.json";

            try {
                br = new BufferedReader(new FileReader(filePath));
                preguntas = new Gson().fromJson(br, JSONPregunta[].class);
                br.close();
            } catch (Exception e) {

            }

        } else if (dificultadJSON == 1 && idiomaJSON == 1) {    //AQUI CARGAMOS ESPAÑOL NORMAL

            filePath = "/data/data/com.example.ecoworld/files/castellanoNormal.json";

            try {
                br = new BufferedReader(new FileReader(filePath));
                preguntas = new Gson().fromJson(br, JSONPregunta[].class);
                br.close();
            } catch (Exception e) {

            }

        } else if (dificultadJSON == 2 && idiomaJSON == 1) {    //AQUI CARGAMOS ESPAÑOL DIFÍCIL

            filePath = "/data/data/com.example.ecoworld/files/castellanoDificil.json";

            try {
                br = new BufferedReader(new FileReader(filePath));
                preguntas = new Gson().fromJson(br, JSONPregunta[].class);
                br.close();
            } catch (Exception e) {

            }

        } else if (dificultadJSON == 0 && idiomaJSON == 2) {    //AQUI CARGAMOS ENGLISH FÁCIL

            filePath = "/data/data/com.example.ecoworld/files/englishFacil.json";

            try {
                br = new BufferedReader(new FileReader(filePath));
                preguntas = new Gson().fromJson(br, JSONPregunta[].class);
                br.close();
            } catch (Exception e) {

            }

        } else if (dificultadJSON == 1 && idiomaJSON == 2) {    //AQUI CARGAMOS ENGLISH NORMAL

            filePath = "/data/data/com.example.ecoworld/files/englishNormal.json";

            try {
                br = new BufferedReader(new FileReader(filePath));
                preguntas = new Gson().fromJson(br, JSONPregunta[].class);
                br.close();
            } catch (Exception e) {

            }

        } else {
            filePath = "/data/data/com.example.ecoworld/files/englishDificil.json";

            try {
                br = new BufferedReader(new FileReader(filePath));
                preguntas = new Gson().fromJson(br, JSONPregunta[].class);
                br.close();
            } catch (Exception e) {

            }
        }

    }

    //GENERA LAS PREGUNTAS Y RESPUESTAS ALEATORIAS Y SETTEA EL TEXTO DE LOS BOTONES CORRESPONDIENTES SIN QUE SE REPITAN LAS PREGUNTAS.
    public void generarPreguntasRespuestasAleatorias(TextView txtPreguntaQuestions,
                                                     TextView txtExplicacionQuestions,
                                                     Button respuesta1Questions,
                                                     Button respuesta2Questions, Button respuesta3Questions,
                                                     Button respuesta4Questions) {

        int randomMAX = preguntas.length;
        int random = -1;

        if (contadorNull != 0) {
            do {
                //Esto genera un número aleatorío entre 0 y 19
                random = (int) Math.floor(Math.random() * randomMAX);
            } while (preguntas[random] == null);
        } else {
            random = (int) Math.floor(Math.random() * randomMAX);
            contadorNull++;
        }

        //ESTE ARRAY ES MENOS 1 PARA COPIAR EL CONTENIDO DEL ARRAY PREGUNTAS Y BORRAR UNA
        String[] preguntasAUX = new String[randomMAX - 1];

        txtPreguntaQuestions.setText(preguntas[random].getEnunciado());
        txtExplicacionQuestions.setText(preguntas[random]
                .getFundamentosRespuestaCorrecta());
        List<String> textoRespuestas = preguntas[random].getRespuestas();
        String[] textoRespuestasAUX = new String[4];
        Boolean repetido = false;
        ArrayList<Integer> posiciones = new ArrayList<>();
        ListIterator li = posiciones.listIterator();
        int posicionWhile = -1;

        //AQUI GUARDAMOS LA RESPUESTA CORRECTA
        respuestaCorrectaTexto = textoRespuestas.get(textoRespuestas.size() - 1);

        respuesta1Questions.setText("");
        respuesta2Questions.setText("");
        respuesta3Questions.setText("");
        respuesta4Questions.setText("");

        int randomRespuestas;

        /*ALGORITMO PARA CONTROLAR QUE GENERE DE MANERA ALEATORIA LAS RESPUESTAS
         Y HAGA SU RESPECTIVO SETTER PARA CADA RESPUESTA*/
        for (int i = 0; i < 4; i++) {

            repetido = false;

            randomRespuestas = (int) Math.floor(Math.random() * 4);
            li = posiciones.listIterator();

            if (i == 0) {
                posiciones.add(randomRespuestas);
                textoRespuestasAUX[randomRespuestas] = textoRespuestas.get(i);
            }
            do {

                if (repetido) {
                    randomRespuestas = (int) Math.floor(Math.random() * 4 + 0);
                    li = posiciones.listIterator();
                }

                repetido = false;

                if (i != 0) {
                    while (li.hasNext()) {
                        posicionWhile = (int) li.next();
                        if (posicionWhile == randomRespuestas) {
                            repetido = true;
                        }
                    }

                    if (!repetido) {
                        posiciones.add(randomRespuestas);
                        textoRespuestasAUX[randomRespuestas] = textoRespuestas.get(i);
                    }
                }
            } while (repetido);

            if (i == 3) {
                //AQUI GUARDAMOS LA RESPUESTA CORRECTA
                obtenerPosicionCorrecta = randomRespuestas;
            }

        }

        respuesta1Questions.setText(textoRespuestasAUX[0]);
        respuesta2Questions.setText(textoRespuestasAUX[1]);
        respuesta3Questions.setText(textoRespuestasAUX[2]);
        respuesta4Questions.setText(textoRespuestasAUX[3]);

        for (int i = 0; i < randomMAX; i++) {
            if (i == random) {
                preguntas[random] = null;
            }
        }

    }

    /* CONTROLAMOS CUANDO HACEMOS CLICK EN UNA RESPUESTA O EL CRONO LLEGA A 0,
    INDICAMOS LA RESPUESTA CORRECTA E INCORRECTAS, LUEGO VOLVEMOS A SETTEAR */
    public void controlarQuestionsActivityChrono0ButtonClick(Button respuesta1Questions,
                                                             Button respuesta2Questions,
                                                             Button respuesta3Questions, Button respuesta4Questions,
                                                             Drawable respuestaCorrecta, Drawable respuestaIncorrecta,
                                                             ImageView imgLogoPreguntas, TextView txtExplicacionQuestions,
                                                             Button botonJugarQuestions, Button txtAyudasQuestions) {

        for (int i = 0; i < 4; i++) {
            if (respuesta1Questions.getText().equals(respuestaCorrectaTexto)) {
                respuesta1Questions.setBackground(respuestaCorrecta);
                respuesta2Questions.setBackground(respuestaIncorrecta);
                respuesta3Questions.setBackground(respuestaIncorrecta);
                respuesta4Questions.setBackground(respuestaIncorrecta);

            } else if (respuesta2Questions.getText().equals(respuestaCorrectaTexto)) {
                respuesta1Questions.setBackground(respuestaIncorrecta);
                respuesta2Questions.setBackground(respuestaCorrecta);
                respuesta3Questions.setBackground(respuestaIncorrecta);
                respuesta4Questions.setBackground(respuestaIncorrecta);
            } else if (respuesta3Questions.getText().equals(respuestaCorrectaTexto)) {
                respuesta1Questions.setBackground(respuestaIncorrecta);
                respuesta2Questions.setBackground(respuestaIncorrecta);
                respuesta3Questions.setBackground(respuestaCorrecta);
                respuesta4Questions.setBackground(respuestaIncorrecta);
            } else {
                respuesta1Questions.setBackground(respuestaIncorrecta);
                respuesta2Questions.setBackground(respuestaIncorrecta);
                respuesta3Questions.setBackground(respuestaIncorrecta);
                respuesta4Questions.setBackground(respuestaCorrecta);
            }
        }

        //UNA VEZ RESPONDIDO LA PREGUNTA O ACABADO EL TIEMPO PONEMOS VISIBLES EL LOGO, EXPLICACION Y BOTON JUGAR.
        imgLogoPreguntas.setVisibility(View.VISIBLE);
        txtExplicacionQuestions.setVisibility(View.VISIBLE);
        botonJugarQuestions.setVisibility(View.VISIBLE);

        //AQUI BLOQUEAMOS LOS EVENTOS CLICK
        respuesta1Questions.setEnabled(false);
        respuesta2Questions.setEnabled(false);
        respuesta3Questions.setEnabled(false);
        respuesta4Questions.setEnabled(false);
        txtAyudasQuestions.setEnabled(false);

        //DETENEMOS EL CRONO
        countDownTimer.cancel();

        //AQUI CAMBIAMOS LOS PARAMETROS DEL LAYOUT DESDE CODIGO.
        respuesta1Questions.setPadding(40, 30, 30, 40);
        respuesta2Questions.setPadding(40, 30, 30, 40);
        respuesta3Questions.setPadding(40, 30, 30, 40);
        respuesta4Questions.setPadding(40, 30, 30, 40);
        respuesta1Questions.setTextSize(12);
        respuesta2Questions.setTextSize(12);
        respuesta3Questions.setTextSize(12);
        respuesta4Questions.setTextSize(12);

    }

    //LA GESTION DEL CRONO, CUANDO TIENE QUE PARARSE
    public void chrono(final int black, final TextView txtChrono, final int rojo, final Button respuesta1Questions,
                       final Button respuesta2Questions, final Button respuesta3Questions,
                       final Button respuesta4Questions, final Drawable respuestaCorrecta,
                       final Drawable respuestaIncorrecta, final ImageView imgLogoPreguntas,
                       final TextView txtExplicacionQuestions, final Button botonJugarQuestions,
                       final Button txtAyudasQuestions) {

        txtChrono.setTextColor(black);

        countDownTimer = new CountDownTimer(30000, 1000) {
            //onTick: Callback fired on regular interval.
            public void onTick(long millisUntilFinished) {
                txtChrono.setText(String.format(Locale.getDefault(),
                        "%d", millisUntilFinished / 1000L));
            }

            //onFinish: Callback fired when the time is up.
            public void onFinish() {
                soundButtonChrono();
                txtChrono.setText("0");
                txtChrono.setTextColor(rojo);

                //MÉTODO PARA CONTROLAR ERRORES DE USUARIO UNA VEZ FINALIZA EL TIEMPO O HACE CLICK EN UNA RESPUESTA
                controlarQuestionsActivityChrono0ButtonClick(respuesta1Questions,
                        respuesta2Questions, respuesta3Questions, respuesta4Questions,
                        respuestaCorrecta, respuestaIncorrecta, imgLogoPreguntas,
                        txtExplicacionQuestions, botonJugarQuestions, txtAyudasQuestions);

            }

        }.start();
    }

    //REFRESCAMOS EL LAYOUT DE PREGUNTAS PARA LA SIGUIENTE PREGUNTA.
    public void refrescarQuestions(final TextView txtExplicacionQuestions,
                                   final Button botonJugarQuestions,
                                   final Button txtAyudasQuestions,
                                   final ImageView imgLogoPreguntas,
                                   final Button respuesta1Questions,
                                   final Button respuesta2Questions,
                                   final Button respuesta3Questions,
                                   final Button respuesta4Questions,
                                   final Drawable respuestaInicial) {

        //CAMBIAMOS EL FONDO DE LAS RESPUESTAS AL ASPECTO INICIAL(CELESTE)
        respuesta1Questions.setBackground(respuestaInicial);
        respuesta2Questions.setBackground(respuestaInicial);
        respuesta3Questions.setBackground(respuestaInicial);
        respuesta4Questions.setBackground(respuestaInicial);

        //HABILITAMOS EL EVENTO CLICK
        respuesta1Questions.setEnabled(true);
        respuesta2Questions.setEnabled(true);
        respuesta3Questions.setEnabled(true);
        respuesta4Questions.setEnabled(true);

        //PONEMOS VISIBLE LOS BOTONES DE RESPUESTA Y AYUDA
        txtAyudasQuestions.setVisibility(View.VISIBLE);
        respuesta1Questions.setVisibility(View.VISIBLE);
        respuesta2Questions.setVisibility(View.VISIBLE);
        respuesta3Questions.setVisibility(View.VISIBLE);
        respuesta4Questions.setVisibility(View.VISIBLE);

        //PONEMOS INVISIBLES EL LOGO, LA EXPLICACION Y BOTON JUGAR.
        imgLogoPreguntas.setVisibility(View.INVISIBLE);
        txtExplicacionQuestions.setVisibility(View.INVISIBLE);
        botonJugarQuestions.setVisibility(View.INVISIBLE);


        //SI BOTON DE AYUDAS LLEGA 0 EL BOTON DESAPARECE.
        if (jugador.getAyudas() >= 1) {
            txtAyudasQuestions.setEnabled(true);
        } else {
            txtAyudasQuestions.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case EXIT_ACTIVITY: {
                if (resultCode == RESULT_OK) {
                    countDownTimer.cancel();
                    finishAffinity();
                }
            }
            break;
        }
    }

    //CON ESTO ANULAMOS EL BOTON DE VOLVER.
    @Override
    public void onBackPressed() {
    }
}
