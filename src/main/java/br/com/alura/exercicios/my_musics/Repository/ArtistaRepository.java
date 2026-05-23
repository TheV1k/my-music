package br.com.alura.exercicios.my_musics.Repository;

import br.com.alura.exercicios.my_musics.Models.Artista;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ArtistaRepository extends JpaRepository <Artista, Long> {

//Busca Artista usando parte do nome

    Optional<Artista> findByNomeContainsIgnoreCase(String nomeArtista);

   
}
