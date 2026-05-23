package br.com.alura.exercicios.my_musics.Controller;


import br.com.alura.exercicios.my_musics.DTO.MusicaDTO;
import br.com.alura.exercicios.my_musics.Models.Musica;
import br.com.alura.exercicios.my_musics.Service.MusicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/musica")
public class MusicaController {

    @Autowired
   private MusicaService service;

    @PostMapping
    public ResponseEntity<List<Musica>> salvar(
            @RequestBody List<MusicaDTO> dtos){

        List<Musica> musicas = service.salvar(dtos);

        return ResponseEntity.ok(musicas);
    }

}
