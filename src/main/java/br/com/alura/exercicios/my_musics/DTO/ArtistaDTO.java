package br.com.alura.exercicios.my_musics.DTO;

import br.com.alura.exercicios.my_musics.Models.Genero;

public class ArtistaDTO {

    private String nome;
    private Genero genero;

    public ArtistaDTO(String nome, Genero genero) {
        this.nome = nome;
        this.genero = genero;
    }

    public ArtistaDTO(){}



    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }
}
