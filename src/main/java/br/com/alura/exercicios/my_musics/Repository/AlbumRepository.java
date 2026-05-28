package br.com.alura.exercicios.my_musics.Repository;

import br.com.alura.exercicios.my_musics.Models.Album;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AlbumRepository extends JpaRepository<Album, Long> {

    Page<Album> findAll(Pageable pageable);

    @Query(value = "select * from album " +
            "ORDER BY preco " +
            "DESC LIMIT 5",
            nativeQuery = true)
    List<Album> cincoAlbumsMaisCaros();
}
