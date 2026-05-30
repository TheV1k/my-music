package br.com.alura.exercicios.my_musics.DTO;


public record ResumoMusicaDTO(
        Integer faixa,
        String titulo,
        String artista,
        String album,
        String linkMusica
) {
}