package com.example.ecoworld;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class JugadorAdapter extends ArrayAdapter {

    private Jugador[] jugadores;
    int posicion = 0;

    public JugadorAdapter(Context context, Jugador[] jugadores) {
        super(context, R.layout.jugador_layout, jugadores);
        this.jugadores = jugadores;
    }

    //METODO (Override) GETVIEW DE LA CLASE ArrayAdapter
    public View getView(int position, View convertView, ViewGroup parent) {
        posicion++;
        //ASIGNAMOS NUESTRO JUGADOR LAYOUT A ITEM.
        View item = LayoutInflater.from(getContext()).
                inflate(R.layout.jugador_layout, parent, false);

        //OBTENEMOS UNA REFERENCIA A LOS 3 ELEMENTOS QUE QUEREMOS MOSTRAR EN ITEM.
        TextView txtNumero = item.findViewById(R.id.txtNumero);
        TextView txtNombreJugador = item.findViewById(R.id.txtNombreJugador);
        TextView txtPuntosJugador = item.findViewById(R.id.txtPuntosJugador);

        //ASIGNAMOS LOS 3 TEXTOS A MOSTRAR.
        txtNumero.setText(String.valueOf(posicion));
        txtNombreJugador.setText(jugadores[position].getNombre());
        txtPuntosJugador.setText(String.valueOf(jugadores[position].getPuntos()));

        //DEVOLVEMOS UN ITEM QUE ES DE TIPO JUGADOR ADAPTER.
        return item;
    }

}

