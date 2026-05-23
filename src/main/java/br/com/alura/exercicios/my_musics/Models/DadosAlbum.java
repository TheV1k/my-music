package br.com.alura.exercicios.my_musics.Models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//Informações do sobre albuns que são retornadas pela API
@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosAlbum(@JsonAlias("collectionName") String nome,
                         @JsonAlias("releaseDate") String anoLancamento,
                         @JsonAlias("artworkUrl60") String capa,
                         @JsonAlias("collectionId") Long idAlbum,
                         @JsonAlias("collectionPrice") Double preco) {
}
