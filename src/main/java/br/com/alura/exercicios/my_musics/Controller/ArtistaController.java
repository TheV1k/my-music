package br.com.alura.exercicios.my_musics.Controller;

import br.com.alura.exercicios.my_musics.DTO.ArtistaDTO;
import br.com.alura.exercicios.my_musics.DTO.ResumoArtistaDTO;
import br.com.alura.exercicios.my_musics.Models.Artista;
import br.com.alura.exercicios.my_musics.Service.ArtistaService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.*;
import java.util.List;

@RestController
@RequestMapping("/artista")
public class ArtistaController {

    @Autowired
    private  ArtistaService service;

    @PostMapping
    public ResponseEntity<ResumoArtistaDTO> salvar(@RequestBody ArtistaDTO dto){

             ResumoArtistaDTO artista = service.salvar(dto);

             return ResponseEntity.status(HttpStatus.CREATED).body(artista);

    }

    @GetMapping
        public List<ResumoArtistaDTO> listar(){
            return service.listarResumo();
        }


    @GetMapping("/artista")
    public Page<Artista> listar(@RequestParam(defaultValue = "0") int pagina,
                                @RequestParam(defaultValue = "5") int tamanho){

        Pageable pageable = PageRequest.of(
                pagina,
                tamanho,
                Sort.by("titulo").ascending());

        return service.listarArtistasPaginados(pagina, tamanho);
    }

}
