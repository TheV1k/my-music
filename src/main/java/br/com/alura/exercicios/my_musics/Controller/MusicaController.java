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
import org.springframework.data.web.PageableDefault;
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
            Album album,
            Artista artista){


        List<Musica> musicasSalvas = dtos.stream()
                .map(dto -> new Musica(dto,album,artista))
                .toList();

        List<ResumoMusicaDTO> resposta = service.salvar(musicasSalvas);

        return  ResponseEntity.status(HttpStatus.CREATED).body(resposta);
    }

    @GetMapping

    public Page<ResumoMusicaDTO> listar(@RequestParam(defaultValue = "0") int pagina,
                                        @RequestParam(defaultValue = "10") int tamanho) {
            Pageable pageable = PageRequest.of(
                    pagina,
                    tamanho,
                    Sort.by("titulo").ascending());

            return service.listarMusicasPaginados(pageable);
        }

        //Busca músicas pelo titulo
    @GetMapping("/titulo/{titulo}")
    public Page<ResumoMusicaDTO> buscarPorTitulo(@PathVariable String titulo, Pageable pageable){


        return service.buscarMusicaPorTitulo(titulo, pageable);
    }

    //Lista as músicas de um album
    @GetMapping("/album/{album}")
    public List<MusicaDTO> buscarMusicasAlbum(@PathVariable String album){
        return service.musicasPorAlbum(album);
    }

    //Busca músicas de um album usando apenas parte do nome do album
    @GetMapping("/album/buscar-por-album")
    public ResponseEntity<List<ResumoMusicaDTO>> buscarPorNomeParcial(@RequestParam("album")String album){

        List<ResumoMusicaDTO> musicas = service.musicasPorAlbumParteTitulo(album);

        return ResponseEntity.ok(musicas);

    }
}

