package br.com.alura.exercicios.my_musics.DTO;

public record ResumoAlbumDTO(
        String nome,
        String artista,
        String anoLancamento,
        Double preco,
        String capa
) {
}