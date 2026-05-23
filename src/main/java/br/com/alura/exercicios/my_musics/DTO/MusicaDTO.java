package br.com.alura.exercicios.my_musics.DTO;

public class MusicaDTO {
    private String titulo;
    private int faixa;
    private String linkMusica;

    public MusicaDTO(int faixa, String titulo, String linkMusica) {
        this.faixa = faixa;
        this.titulo = titulo;
        this.linkMusica = linkMusica;
    }

    public MusicaDTO() {

    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getFaixa() {
        return faixa;
    }

    public void setFaixa(int faixa) {
        this.faixa = faixa;
    }

    public String getLinkMusica() {
        return linkMusica;
    }

    public void setLinkMusica(String linkMusica) {
        this.linkMusica = linkMusica;
    }
}
