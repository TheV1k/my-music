package br.com.alura.exercicios.my_musics.Models;

import br.com.alura.exercicios.my_musics.DTO.AlbumDTO;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "album")
public class Album {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;
   private Long idItunes;
   private String nome;
   private String capa;
   private Double preco;
   private String anoLancamento;

   public Album(){}


   @ManyToOne(optional = false)
   @JoinColumn(name = "artista_id", nullable = false)
   private Artista artista;


   @OneToMany (mappedBy = "album", cascade =CascadeType.ALL, fetch = FetchType.LAZY)
   private List<Musica> musicas = new ArrayList<>();

   public Album(DadosAlbum dados, Artista artista) {

      this.idItunes = dados.idAlbum();
      this.nome = dados.nome();
      this.capa = dados.capa();
      this.preco = dados.preco();
      this.artista = artista;

      if (dados.anoLancamento() != null) {
         this.anoLancamento =
                 dados.anoLancamento().substring(0, 4);
      }
   }

   public Album(AlbumDTO dto, Artista artista) {
      this.nome = dto.getNome();
      this.capa = dto.getCapa();
      this.preco = dto.getPreco();
      if (dto.getAnoLancamento() != null) {
         this.anoLancamento =
                 dto.getAnoLancamento().substring(0, 4);
         this.artista = artista;
         this.idItunes = dto.getIdITunes();
      }

   }




   public String getNome() {
      return nome;
   }

   public void setNome(String nome) {
      this.nome = nome;
   }


   public String getCapa() {
      return capa;
   }

   public void setCapa(String capa) {
      this.capa = capa;
   }

   public Double getPreco() {
      return preco;
   }

   public void setPreco(Double preco) {
      this.preco = preco;
   }

   public Artista getArtista() {
      return artista;
   }

   public void setArtista(Artista artista) {
      this.artista = artista;
   }

   public String getAnoLancamento() {
      return anoLancamento;
   }

   public void setAnoLancamento(String anoLancamento) {
      this.anoLancamento = anoLancamento;
   }

   public Long getIdItunes() {
      return idItunes;
   }

   @Override
   public String toString() {
      return "Album{" +
              "nome='" + nome + '\'' +
              ", capa='" + capa + '\'' +
              ", preco=" + preco +
              ", anoLancamento='" + anoLancamento + '\'' +
              '}';
   }
}
