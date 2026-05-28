package br.com.alura.exercicios.my_musics.Repository;

import br.com.alura.exercicios.my_musics.Models.Album;
import br.com.alura.exercicios.my_musics.Models.Artista;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ArtistaRepository extends JpaRepository <Artista, Long> {


    Page<Artista> findAll(Pageable pageable);

//Busca Artista usando parte do nome

    Optional<Artista> findByNomeContainsIgnoreCase(String nomeArtista);


    @Query("""
       SELECT a
       FROM Album a
       JOIN a.artista ar
       WHERE UPPER(ar.nome) = UPPER(:pesquisa)
       """)
    List<Album> listaAlbunsPorArtista(@Param("pesquisa") String pesquisa);
}
