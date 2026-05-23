package br.com.alura.exercicios.my_musics.Controller;

import br.com.alura.exercicios.my_musics.DTO.ArtistaDTO;
import br.com.alura.exercicios.my_musics.Models.Artista;
import br.com.alura.exercicios.my_musics.Service.ArtistaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/artista")
public class ArtistaController {

    @Autowired
    private  ArtistaService service;

    @PostMapping
    public ResponseEntity<Artista> salvar(@RequestBody ArtistaDTO dto){

             Artista artista = service.salvar(dto);

             return ResponseEntity.ok(artista);

    }

}
