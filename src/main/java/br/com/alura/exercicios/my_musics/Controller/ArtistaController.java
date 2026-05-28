package br.com.alura.exercicios.my_musics.Controller;

import br.com.alura.exercicios.my_musics.DTO.ArtistaDTO;
import br.com.alura.exercicios.my_musics.Models.Artista;
import br.com.alura.exercicios.my_musics.Service.ArtistaService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.*;

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

    @GetMapping("/artistas")
    public Page<Artista> listar(@RequestParam(defaultValue = "0") int pagina,
                                @RequestParam(defaultValue = "5") int tamanho){

        Pageable pageable = PageRequest.of(
                pagina,
                tamanho,
                Sort.by("titulo").ascending());

        return service.listarArtistasPaginados(pagina, tamanho);
    }

}
