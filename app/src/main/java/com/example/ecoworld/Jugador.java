package com.example.ecoworld;

import java.io.Serializable;

public class Jugador implements Serializable {

    private String nombre;
    private int dificultad;
    private int puntos;
    private int foto;
    private String nombreActivista;
    private int idioma;
    private int ayudas;

    public Jugador() {
        this.puntos = 0;
        this.ayudas = 3;
    }

    public Jugador(String nombre, int puntos) {
        this.nombre = nombre;
        this.puntos = puntos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getDificultad() {
        return dificultad;
    }

    public void setDificultad(int dificultad) {
        this.dificultad = dificultad;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    public int getFoto() {
        return foto;
    }

    public void setFoto(int foto) {
        this.foto = foto;
    }

    public int getIdioma() {
        return idioma;
    }

    public void setIdioma(int idioma) {
        this.idioma = idioma;
    }

    public int getAyudas() {
        return ayudas;
    }

    public void setAyudas(int ayudas) {
        this.ayudas = ayudas;
    }

    public String getNombreActivista() {
        return nombreActivista;
    }

    public void setNombreActivista(String nombreActivista) {
        this.nombreActivista = nombreActivista;
    }

    @Override
    public String toString() {
        return nombre + ";" + puntos;
    }
}
