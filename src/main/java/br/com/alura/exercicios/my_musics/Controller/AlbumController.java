package br.com.alura.exercicios.my_musics.Controller;

import br.com.alura.exercicios.my_musics.DTO.AlbumDTO;
import br.com.alura.exercicios.my_musics.Models.Album;
import br.com.alura.exercicios.my_musics.Service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/album")
public class AlbumController {

    @Autowired
    private AlbumService service;

    @PostMapping
    public ResponseEntity<List<Album>> salvar(
            @RequestBody List<AlbumDTO> dtos){

        List<Album> albums = service.salvar(dtos);

        return ResponseEntity.ok(albums);    }
}
