package br.com.alura.exercicios.my_musics.DTO;

import br.com.alura.exercicios.my_musics.Models.Album;
import br.com.alura.exercicios.my_musics.Models.Artista;

public record ResumoMusicaDTO(
        Integer faixa,
        String titulo,
        Artista artista,
        Album album,
        String linkMusica
) {
}