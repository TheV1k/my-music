package br.com.alura.exercicios.my_musics.Repository;

import br.com.alura.exercicios.my_musics.Models.Musica;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MusicaRepository extends JpaRepository<Musica, Long> {

    //Buscar música pelo nome

    List<Musica> findByTituloEqualsIgnoreCase(String tituloMusica);
}
