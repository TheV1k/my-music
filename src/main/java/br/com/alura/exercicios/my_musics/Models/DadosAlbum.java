package br.com.alura.exercicios.my_musics.Models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;

//Informações do sobre albuns que são retornadas pela API
@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosAlbum(@JsonAlias("collectionName") String nome,
                         @JsonAlias("primaryGenreName") String genero,
                         @JsonAlias("releaseDate") LocalDate anoLancamento,
                         @JsonAlias("artworkUrl60") String capa,
                         @JsonAlias("collectionId") Long idAlbum,
                         @JsonAlias("collectionPrice") Double preco,
                         @JsonAlias("trackName") List<DadosMusica> musicas) {
}
