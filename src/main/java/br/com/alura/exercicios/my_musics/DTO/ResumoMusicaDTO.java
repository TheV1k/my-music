package br.com.alura.exercicios.my_musics.DTO;

import br.com.alura.exercicios.my_musics.Models.Artista;

public record ResumoMusicaDTO(int faixa,
                              String titulo,
                              String album,
                              Artista artista,
                              String linkMusica ) {
}
