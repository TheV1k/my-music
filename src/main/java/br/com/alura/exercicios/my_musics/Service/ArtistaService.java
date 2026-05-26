package br.com.alura.exercicios.my_musics.Service;

import br.com.alura.exercicios.my_musics.DTO.ArtistaDTO;
import br.com.alura.exercicios.my_musics.Models.Album;
import br.com.alura.exercicios.my_musics.Models.Artista;
import br.com.alura.exercicios.my_musics.Models.DadosArtista;
import br.com.alura.exercicios.my_musics.Models.DadosBusca;
import br.com.alura.exercicios.my_musics.Repository.ArtistaRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class ArtistaService extends BaseService{


    public ArtistaService(ConsumoAPI consumo,
                          ConverteDados conversor) {

        super(consumo, conversor);
    }

    @Autowired
    private ArtistaRepository repository;


    //Faz a requisição para buscar as informações do artista (Nome e gênero na API do iTunes)
    public DadosArtista getDadosArtista(String nomeArtista){
        var artista = URLEncoder.encode(nomeArtista, StandardCharsets.UTF_8);
        var json = consumo.obterDados(ENDERECO + "search?term=" +
                artista
                + "&entity=musicArtist");
        DadosBusca<DadosArtista> dadosArtista =
                conversor.obterDados(json, new TypeReference<DadosBusca<DadosArtista>>() {
        });

         return dadosArtista
                .results()
                .stream()
                .filter(a -> a.nome().equalsIgnoreCase(nomeArtista))
                .findFirst()
                .orElseThrow(()-> new RuntimeException("Artista não encontrado"));
    }



    public Artista salvar(ArtistaDTO dto) {

        Artista artista = new Artista(dto);

        return repository.save(artista);
    }

    //Pesquisa os artistas salvos no banco utilizando parte do nome
    public Artista buscaArtistaParteDoNome(String nomeArtista){

       return repository.findByNomeContainsIgnoreCase(nomeArtista)
               .orElseThrow(() -> new RuntimeException("Artista não encontrado"));


    }
    public List<Album> listarAlbumsDoArtista(String pesquisa){

        List<Album> albumsDoArtista = repository.listaAlbunsPorArtista(pesquisa);
        return albumsDoArtista;

    }
}
