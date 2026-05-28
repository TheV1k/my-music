package br.com.alura.exercicios.my_musics.Controller;

import br.com.alura.exercicios.my_musics.DTO.AlbumDTO;
import br.com.alura.exercicios.my_musics.Models.Album;
import br.com.alura.exercicios.my_musics.Models.Artista;
import br.com.alura.exercicios.my_musics.Service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/album")
public class AlbumController {

    @Autowired
    private AlbumService service;

    @PostMapping
    public ResponseEntity<List<Album>> salvar(
            @RequestBody List<AlbumDTO> dtos, Artista artista){

        List<Album> albums = service.salvar(dtos, artista );

        return ResponseEntity.ok(albums);    }


    @GetMapping("/album")
    public Page<Album> listar(@RequestParam(defaultValue = "0") int pagina,
                                @RequestParam(defaultValue = "5") int tamanho){

        Pageable pageable = PageRequest.of(
                pagina,
                tamanho,
                Sort.by("nome").ascending());

        return service.listarAlbumsPaginados(pagina, tamanho);
    }
}

