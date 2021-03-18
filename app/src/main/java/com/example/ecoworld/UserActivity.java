package com.example.ecoworld;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class UserActivity extends AppCompatActivity {

    int dificultadSeleccionada = -1;
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

    Jugador jugador; //LO CREAMOS GLOBAL PORQUE LOS EVENTOS CLICK NO RECIBEN PARAMETROS.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        final Button btnSettings = findViewById(R.id.btnSettings);
        final TextView txtUsername = findViewById(R.id.txtUsername);
        final EditText editTxtUsername = findViewById(R.id.editTxtUsername);
        final Button btnEasy = findViewById(R.id.btnEasy);
        final Button btnNormal = findViewById(R.id.btnNormal);
        final Button btnHard = findViewById(R.id.btnHard);
        Button btnContinue = findViewById(R.id.btnContinue);

        Intent obtenerIntentJugador = getIntent();
        jugador = (Jugador) obtenerIntentJugador.getExtras().getSerializable(JUGADOR);

        cambiarIdioma(btnSettings, txtUsername, btnEasy, btnNormal, btnHard,
                btnContinue);

        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserActivity.this, SettingsActivity.class);
                intent.putExtra(JUGADOR, jugador);
                startActivityForResult(intent, SETTINGS_ACTIVITY);
            }
        });

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent IntroActivity;
                jugador.setDificultad(dificultadSeleccionada);

                if (editTxtUsername.getText().toString().isEmpty()) {
                    //vibratorButton(); //ESTO CUMPLE LA FUNCION DE VIBRACION (NUESTRA TABLET NO TIENE VIBRACION).
                    idiomaToastUsername();
                } else {
                    if (dificultadSeleccionada == -1) {
                        idiomaToastDificultad();
                    } else {
                        jugador.setNombre(editTxtUsername.getText().toString());

                        IntroActivity = new Intent(UserActivity.this,
                                IntroActivity.class);
                        IntroActivity.putExtra(JUGADOR, jugador);
                        startActivity(IntroActivity);
                    }
                }
            }
        });

        btnEasy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dificultadSeleccionada = 0;
                btnEasy.setBackgroundResource(R.drawable.nuvecoloractivada);
                btnNormal.setBackgroundResource(R.drawable.nuvecolor);
                btnHard.setBackgroundResource(R.drawable.nuvecolor);
            }
        });

        btnNormal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dificultadSeleccionada = 1;
                btnEasy.setBackgroundResource(R.drawable.nuvecolor);
                btnNormal.setBackgroundResource(R.drawable.nuvecoloractivada);
                btnHard.setBackgroundResource(R.drawable.nuvecolor);
            }
        });

        btnHard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dificultadSeleccionada = 2;
                btnEasy.setBackgroundResource(R.drawable.nuvecolor);
                btnNormal.setBackgroundResource(R.drawable.nuvecolor);
                btnHard.setBackgroundResource(R.drawable.nuvecoloractivada);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case SETTINGS_ACTIVITY: {
                if (resultCode == RESULT_OK) {
                    jugador = (Jugador) data.getSerializableExtra(JUGADOR);
                }
                break;
            }
        }
    }

    //SETTEAMOS EL TEXTO DE LOS BOTONES SEGUN EL IDIOMA DEL JUGADOR.
    public void cambiarIdioma(Button btnSettings, TextView txtUsername,
                              Button btnEasy, Button btnNormal, Button btnHard, Button btnContinue) {

        int idioma = jugador.getIdioma();

        if (idioma == 0) {
            btnSettings.setText("Ajustos");
            btnEasy.setText("Fàcil");
            btnNormal.setText("Normal");
            btnHard.setText("Difícil");
            txtUsername.setText("Usuari");
            btnContinue.setText("Continuar");
        } else if (idioma == 1) {
            btnSettings.setText("Ajustes");
            btnEasy.setText("Fácil");
            btnNormal.setText("Normal");
            btnHard.setText("Difícil");
            txtUsername.setText("Usuario");
            btnContinue.setText("Continuar");
        } else {
            btnSettings.setText("Settings");
            btnEasy.setText("Easy");
            btnNormal.setText("Normal");
            btnHard.setText("Hard");
            txtUsername.setText("Username");
            btnContinue.setText("Continue");
        }
    }

    //SI EL JUGADDOR NO HA SELECCIONADO LA DIFICULTAD MUESTRA UN TOAST CON EL IDIOMA QUE HA SELECCIONADO EL JUGADOR.
    public void idiomaToastDificultad() {
        int idioma = jugador.getIdioma();

        if (idioma == 0) {
            Toast toast = Toast.makeText(UserActivity.this,
                    "No ha seleccionat cap dificultad", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER_VERTICAL, 0, 137);
            toast.show();
        } else if (idioma == 1) {
            Toast toast = Toast.makeText(UserActivity.this,
                    "No ha seleccionado ninguna dificultad", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER_VERTICAL, 0, 137);
            toast.show();
        } else {
            Toast toast = Toast.makeText(UserActivity.this,
                    "You have not selected any difficulty", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER_VERTICAL, 0, 137);
            toast.show();
        }
    }

    //SI EL JUGADDOR NO HA INTRODUCIDO NOMBRE DE USUARIO MUESTRA UN TOAST CON EL IDIOMA QUE HA SELECCIONADO EL JUGADOR.
    public void idiomaToastUsername() {

        int idioma = jugador.getIdioma();

        if (idioma == 0) {
            Toast toast = Toast.makeText(UserActivity.this,
                    "Usuari buit", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER_VERTICAL, 0, 225);
            toast.show();
        } else if (idioma == 1) {
            Toast toast = Toast.makeText(UserActivity.this,
                    "Usuario vacío", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER_VERTICAL, 0, 225);
            toast.show();
        } else {
            Toast toast = Toast.makeText(UserActivity.this,
                    "Empty user", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER_VERTICAL, 0, 225);
            toast.show();
        }
    }

    //ESTA FUNCION HACE QUE VIBRE LOS BOTONES PERO (NUESTRA TABLET NO TIENE VIBRACION).
    /*public void vibratorButton() {
        Vibrator vibrator = (Vibrator) getApplicationContext()
                .getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(1000);
    }*/

    //CON ESTO ANULAMOS EL BOTON DE VOLVER.
    @Override
    public void onBackPressed() {
    }
}