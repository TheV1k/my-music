package br.com.alura.exercicios.my_musics.Repository;

import br.com.alura.exercicios.my_musics.DTO.ResumoAlbumDTO;
import br.com.alura.exercicios.my_musics.DTO.ResumoMusicaDTO;
import br.com.alura.exercicios.my_musics.Models.Musica;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MusicaRepository extends JpaRepository<Musica, Long> {

    @Query("SELECT new br.com.alura.exercicios.my_musics.DTO.ResumoMusicaDTO" +
            "(m.faixa," +
            "m.titulo," +
            "a," +
            "al," +
            "m.linkMusica)" +
            "FROM Musica m " +
            "JOIN m.artista a " +
            "JOIN m.album al")
    List<ResumoMusicaDTO> listarResumoMusica();

    Page<Musica> findAll(Pageable pageable);

    //Buscar música pelo nome

    List<Musica> findByTituloEqualsIgnoreCase(String tituloMusica);

    List<Musica> findByAlbumNomeContainingIgnoreCase(String album);

    List<Musica> findByAlbumNomeEqualsIgnoreCase(String album);
}
