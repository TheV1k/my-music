package br.com.alura.exercicios.my_musics.Models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//Informações do artista que são retornadas pela API
@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosArtista(@JsonAlias("artistName") String nome,
                           @JsonAlias("primaryGenreName") String genero,
                           @JsonAlias("artistId") Long idArtista) {}
