package br.com.alura.exercicios.my_musics.Service;

import br.com.alura.exercicios.my_musics.DTO.AlbumDTO;
import br.com.alura.exercicios.my_musics.Models.Album;
import br.com.alura.exercicios.my_musics.Models.DadosAlbum;
import br.com.alura.exercicios.my_musics.Models.DadosBusca;
import br.com.alura.exercicios.my_musics.Repository.AlbumRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlbumService extends BaseService {

    public AlbumService(ConsumoAPI consumo,
                          ConverteDados conversor) {

        super(consumo, conversor);
    }


    //Faz a requisição e retorna lista de álbuns do artista pesquisado
    public List<DadosAlbum> buscarAlbums(Long idArtista){

        var endereco =ENDERECO + "lookup?id=" +
                idArtista
                + "&entity=album";

        System.out.println("URL album: " + endereco);
        var json = consumo.obterDados(endereco);

        DadosBusca<DadosAlbum> dadosAlbum = conversor.obterDados(json, new TypeReference<DadosBusca<DadosAlbum>>() {
        });


        return dadosAlbum.results().stream()
                .filter(album -> album.idAlbum() != null)
                .toList();

    }

    public List<Album> processarAlbums(List<DadosAlbum> dadosAlbuns){

        List<Album> albums = dadosAlbuns.stream()
                .filter(a -> a != null)
                .filter(a -> a.anoLancamento() != null)
                .distinct()
                .map(Album::new)
                .toList();

        dadosAlbuns.stream()
                .filter(album -> album.idAlbum() != null)
                .forEach(album -> {

                    Integer ano = null;

                    if(album.anoLancamento() != null){
                        ano = Integer.valueOf(
                                album.anoLancamento().substring(0,4)
                        );
                    }

                    Double preco = album.preco();
                });

        return albums;
    }
    public List<AlbumDTO> converterParaDTO(List<Album> albums){

        return albums.stream()
                .map(album -> new AlbumDTO(
                        album.getNome(),
                        album.getCapa(),
                        album.getPreco(),
                        album.getAnoLancamento()
                ))
                .toList();
    }
    @Autowired
    private AlbumRepository repository;

    public List<Album> salvar(List<AlbumDTO> dtos) {

        List<Album> albums = dtos.stream()
                .map(Album::new)
                .toList();

        return repository.saveAll(albums);
    }
}
