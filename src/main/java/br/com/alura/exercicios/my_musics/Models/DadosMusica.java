package br.com.alura.exercicios.my_musics.Models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//Filtro de pesquisa das músicas na api do iTunes
@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosMusica(@JsonAlias("trackName") String titulo,
                          @JsonAlias("trackNumber") Integer numeroFaixa,
                          @JsonAlias("collectionCensoredName") String album,
                          @JsonAlias("trackViewUrl") String linkMusica) {
}
