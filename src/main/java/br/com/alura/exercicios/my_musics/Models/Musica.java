package br.com.alura.exercicios.my_musics.Models;

import br.com.alura.exercicios.my_musics.DTO.MusicaDTO;
import jakarta.persistence.*;

@Entity
@Table(name = "musicas")
public class Musica {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long Id;
   private String titulo;
   private int faixa;
   private String linkMusica;

   @ManyToOne
   @JoinColumn(name = "artista_id")
   private Artista artista;

   public Musica(){}



   public Musica(MusicaDTO dto, Album album, Artista artista) {
      this.titulo = dto.getTitulo();
      this.faixa = dto.getFaixa();
      this.linkMusica = dto.getLinkMusica();
      this.album = album;
      this.artista = artista;
   }

   @ManyToOne(optional = false)
   @JoinColumn(name = "album_id", nullable = false)
   private Album album;

   public Musica(DadosMusica dadosMusica, Album album, Artista artista) {
      this.titulo = dadosMusica.titulo();
      this.faixa = dadosMusica.numeroFaixa();
      this.linkMusica = dadosMusica.linkMusica();
      this.album = album;
      this.artista = artista;
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

   public Album getAlbum() {

      return album;
   }

   public Artista getArtista() {
      return artista;
   }

   public void setAlbum(Album album) {
      this.album = album;
   }

   @Override
   public String toString() {
      return "Musica{" +
              "titulo='" + titulo + '\'' +
              ", faixa=" + faixa +
              ", linkMusica='" + linkMusica + '\'' +
              '}';
   }
}