package br.com.alura.exercicios.my_musics.Models;

public class Artista {

    private String nome;
   private String Genero;


    public Artista(String nome, String genero) {
        this.nome = nome;
        Genero = genero;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getGenero() {
        return Genero;
    }

    public void setGenero(String genero) {
        Genero = genero;
    }
}
