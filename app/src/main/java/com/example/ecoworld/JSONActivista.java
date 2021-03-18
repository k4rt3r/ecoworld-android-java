package com.example.ecoworld;

public class JSONActivista {
    private String nombre;
    private int puntuacion;
    private String rutaImagen;
    private String frasePersonaCat;
    private String frasePersonaEsp;
    private String frasePersonaEng;

    public JSONActivista() {
    }

    public JSONActivista(String nombre, int puntuacion, String rutaImagen,
                         String frasePersonaCat, String frasePersonaEsp, String frasePersonaEng) {
        this.nombre = nombre;
        this.puntuacion = puntuacion;
        this.rutaImagen = rutaImagen;
        this.frasePersonaCat = frasePersonaCat;
        this.frasePersonaEsp = frasePersonaEsp;
        this.frasePersonaEng = frasePersonaEng;
    }

    public String getNombre() {
        return nombre;
    }

    public int getPuntuacion() {
        return puntuacion;
    }

    public String getFrasePersonaCat() {
        return frasePersonaCat;
    }

    public String getFrasePersonaEsp() {
        return frasePersonaEsp;
    }

    public String getFrasePersonaEng() {
        return frasePersonaEng;
    }
}
