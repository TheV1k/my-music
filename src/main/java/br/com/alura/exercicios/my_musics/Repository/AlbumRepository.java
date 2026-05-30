package br.com.alura.exercicios.my_musics.Repository;

import br.com.alura.exercicios.my_musics.DTO.ResumoAlbumDTO;
import br.com.alura.exercicios.my_musics.Models.Album;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AlbumRepository extends JpaRepository<Album, Long> {



    @Query("""
            
            SELECT new br.com.alura.exercicios.my_musics.DTO.ResumoAlbumDTO(
                                           a.nome,
                                           a.artista.nome,
                                           a.anoLancamento,
                                           a.preco,
                                           a.capa
                                       )
            FROM Album a
                ORDER BY a.preco DESC""")
    List<ResumoAlbumDTO> cincoAlbumsMaisCaros(Pageable pageable);

    @Query("SELECT new br.com.alura.exercicios.my_musics.DTO.ResumoAlbumDTO" +
            "(a.nome," +
            "b.nome," +
            "a.anoLancamento," +
            "a.preco," +
            "a.capa )" +
            "FROM Album a " +
            "JOIN a.artista b")
    List<ResumoAlbumDTO> listarResumoAlbum();

}
