package com.example.ecoworld;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    int idiomaSeleccionado = -1;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Jugador jugador = new Jugador();
        final Button btnStart = findViewById(R.id.btnStart);
        final Button btnCatalan = findViewById(R.id.btnCatalan);
        final Button btnEspanol = findViewById(R.id.btnEspanol);
        final Button btnIngles = findViewById(R.id.btnIngles);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (idiomaSeleccionado == -1) {
                    Toast toast = Toast.makeText(MainActivity.this,
                            "You have not selected any language", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER_VERTICAL, 0, 262);
                    toast.show();
                } else {
                    Intent UserActivity;
                    jugador.setIdioma(idiomaSeleccionado);

                    UserActivity = new Intent(MainActivity.this,
                            UserActivity.class);
                    UserActivity.putExtra(JUGADOR, jugador);
                    startActivity(UserActivity);
                }
            }
        });

        btnCatalan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnCatalan.setBackgroundResource(R.drawable.banderacatactivada);
                btnEspanol.setBackgroundResource(R.drawable.banderaespanol);
                btnIngles.setBackgroundResource(R.drawable.banderaingles);
                btnStart.setText(R.string.comensar);
                idiomaSeleccionado = 0;
            }
        });

        btnEspanol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnEspanol.setBackgroundResource(R.drawable.banderaespanolactivada);
                btnCatalan.setBackgroundResource(R.drawable.banderacat);
                btnIngles.setBackgroundResource(R.drawable.banderaingles);
                btnStart.setText(R.string.empezar);
                idiomaSeleccionado = 1;
            }
        });

        btnIngles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnIngles.setBackgroundResource(R.drawable.banderainglesactivada);
                btnCatalan.setBackgroundResource(R.drawable.banderacat);
                btnEspanol.setBackgroundResource(R.drawable.banderaespanol);
                idiomaSeleccionado = 2;
                btnStart.setText(R.string.start);
            }
        });
    }
}