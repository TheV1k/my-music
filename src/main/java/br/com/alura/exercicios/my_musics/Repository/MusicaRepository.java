package br.com.alura.exercicios.my_musics.Repository;

import br.com.alura.exercicios.my_musics.Models.Musica;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MusicaRepository extends JpaRepository<Musica, Long> {

    Page<Musica> findAll(Pageable pageable);

    //Buscar música pelo nome

    List<Musica> findByTituloEqualsIgnoreCase(String tituloMusica);

    List<Musica> findByAlbumNomeContainingIgnoreCase(String album);

    List<Musica> findByAlbumNomeEqualsIgnoreCase(String album);
}
