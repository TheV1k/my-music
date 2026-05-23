package br.com.alura.exercicios.my_musics.Models;

import java.util.Map;

public enum Genero {

    ROCK("Rock"),
    POP("Pop"),
    SERTANEJO("Sertanejo"),
    COUNTRY("Country"),
    MPB("MPB"),
    GOSPEL("Gospel"),
    ELETRONICO("Eletrônico"),
    RAP("Rap"),
    TRAP("Trap"),
    FOLK("Folk"),
    OUTROS;

    private String descricao;

    Genero(String descricao) {
        this.descricao = descricao;
    }

    Genero() {

    }

    public String getDescricao() {
        return descricao;
    }

    private static final Map<String, Genero> MAPA_GENEROS = Map.of(
            "Rock", ROCK,
            "Pop", POP,
            "Sertanejo", SERTANEJO,
            "Country", COUNTRY,
            "MPB", MPB,
            "Eletrônico", ELETRONICO,
            "Gospel", GOSPEL,
            "Rap", RAP,
            "Trap", TRAP,
            "Folk", FOLK

    );
    public static Genero fromString(String genero){
        return MAPA_GENEROS.getOrDefault(genero, OUTROS);
    }
}
