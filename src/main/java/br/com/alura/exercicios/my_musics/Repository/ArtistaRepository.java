package br.com.alura.exercicios.my_musics.Repository;

import br.com.alura.exercicios.my_musics.DTO.AlbumDTO;
import br.com.alura.exercicios.my_musics.DTO.ResumoArtistaDTO;
import br.com.alura.exercicios.my_musics.Models.Artista;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ArtistaRepository extends JpaRepository <Artista, Long> {

    @Query("SELECT new br.com.alura.exercicios.my_musics.DTO.ResumoArtistaDTO" +
            "(a.nome,a.genero)" +
            "FROM Artista a")
        List<ResumoArtistaDTO> listarResumoArtista();

    Page<Artista> findAll(Pageable pageable);

//Busca Artista usando parte do nome

    Optional<Artista> findByNomeContainsIgnoreCase(String nomeArtista);


    @Query("""
       SELECT a
       FROM Album a
       JOIN a.artista ar
       WHERE UPPER(ar.nome) = UPPER(:pesquisa)
       """)
    List<AlbumDTO> listaAlbunsPorArtista(@Param("pesquisa") String pesquisa);
}
