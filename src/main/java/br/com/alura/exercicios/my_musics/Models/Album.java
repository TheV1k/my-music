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
   private Long Id;
   private String nome;
   private String capa;
   private Double preco;
   private String anoLancamento;

   public Album(){}


   @ManyToOne
   private Artista artista;


   @OneToMany (mappedBy = "album", cascade =CascadeType.ALL, fetch = FetchType.LAZY)
   private List<Musica> musicas = new ArrayList<>();

   public Album(DadosAlbum dados) {
      this.nome = dados.nome();
      this.capa = dados.capa();
      this.preco = dados.preco();

      if (dados.anoLancamento() != null) {
         this.anoLancamento =
                 dados.anoLancamento().substring(0, 4);
      }
   }

   public Album(AlbumDTO dto) {
      this.nome = dto.getNome();
      this.capa = dto.getCapa();
      this.preco = dto.getPreco();
      if (dto.getAnoLancamento() != null) {
         this.anoLancamento =
                 dto.getAnoLancamento().substring(0, 4);
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
