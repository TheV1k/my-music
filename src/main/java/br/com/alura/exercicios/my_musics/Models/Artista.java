package br.com.alura.exercicios.my_musics.Models;

import br.com.alura.exercicios.my_musics.DTO.ArtistaDTO;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "artista")
public class Artista {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    @Enumerated(EnumType.STRING)
    private Genero genero;
    @OneToMany(mappedBy = "artista", cascade =CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Album> albuns = new ArrayList<>();

    public Artista(){}

    public Artista(ArtistaDTO dto) {
        this.nome = dto.getNome();
        this.genero = dto.getGenero();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }


    public void setNome(String nome) {
        this.nome = nome;
    }

    public Genero getGenero() {
        return genero;
    }

    public String toString() {
        return
                "id: " + id +
                " | nome: " + nome + '\'' +
                " | genero: " + genero
                ;
    }
}
