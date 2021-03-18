package com.example.ecoworld;

import java.util.List;

public class JSONPregunta {

    private String enunciado;
    private List<String> respuestas;
    private String fundamentosRespuestaCorrecta;

    public JSONPregunta(String enunciado, List<String> respuestas, String fundamentosRespuestaCorrecta) {
        this.enunciado = enunciado;
        this.respuestas = respuestas;
        this.fundamentosRespuestaCorrecta = fundamentosRespuestaCorrecta;
    }

    public String getEnunciado() {
        return enunciado;
    }

    public List<String> getRespuestas() {
        return respuestas;
    }

    public String getFundamentosRespuestaCorrecta() {
        return fundamentosRespuestaCorrecta;
    }

}
