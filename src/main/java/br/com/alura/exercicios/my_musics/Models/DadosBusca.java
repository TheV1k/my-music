package br.com.alura.exercicios.my_musics.Models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosBusca<T>(int resultCount, List<T> results) {
}