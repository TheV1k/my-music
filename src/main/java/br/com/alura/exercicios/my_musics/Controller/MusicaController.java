package br.com.alura.exercicios.my_musics.Controller;


import br.com.alura.exercicios.my_musics.DTO.MusicaDTO;
import br.com.alura.exercicios.my_musics.DTO.ResumoMusicaDTO;
import br.com.alura.exercicios.my_musics.Models.Album;
import br.com.alura.exercicios.my_musics.Models.Artista;
import br.com.alura.exercicios.my_musics.Models.Musica;
import br.com.alura.exercicios.my_musics.Service.MusicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/musica")
public class MusicaController {

    @Autowired
   private MusicaService service;

    @PostMapping
    public ResponseEntity<List<ResumoMusicaDTO>> salvar(
            @RequestBody List<MusicaDTO> dtos,
            @RequestParam Album album,
            @RequestParam Artista artista){


        List<ResumoMusicaDTO> musicasSalvas = service.salvar(dtos, album, artista);

        return  ResponseEntity.status(HttpStatus.CREATED).body(musicasSalvas);
    }

    @GetMapping("/musicas")
    public Page<Musica> listar(@RequestParam(defaultValue = "0") int pagina,
                              @RequestParam(defaultValue = "5") int tamanho) {
            Pageable pageable = PageRequest.of(
                    pagina,
                    tamanho,
                    Sort.by("titulo").ascending());

            return service.listarMusicasPaginados(pagina, tamanho);
        }

}
