package br.com.alura.exercicios.my_musics.DTO;

import br.com.alura.exercicios.my_musics.Models.Artista;

public record ResumoAlbumDTO (String nome,
                              Artista artista,
                              Double preco,
                              String anoLancamento,
                              String capa){
}
