package br.com.alura.exercicios.my_musics.Models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

//estrutura padrão da resposta da API do Apple iTunes,
// armazenando a quantidade de resultados e uma lista genérica de objetos retornados (artistas, álbuns ou músicas).
@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosBusca<T>(int resulCount, List<T> results) {
}