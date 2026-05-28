package br.com.alura.exercicios.my_musics.Service;

import br.com.alura.exercicios.my_musics.DTO.AlbumDTO;
import br.com.alura.exercicios.my_musics.DTO.ResumoAlbumDTO;
import br.com.alura.exercicios.my_musics.Models.Album;
import br.com.alura.exercicios.my_musics.Models.Artista;
import br.com.alura.exercicios.my_musics.Models.DadosAlbum;
import br.com.alura.exercicios.my_musics.Models.DadosBusca;
import br.com.alura.exercicios.my_musics.Repository.AlbumRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlbumService extends BaseService {

    public AlbumService(ConsumoAPI consumo,
                          ConverteDados conversor) {

        super(consumo, conversor);
    }

    //Paginação
    public Page<Album> listarAlbumsPaginados(int pagina, int tamanho) {
        PageRequest pageable = PageRequest.of(pagina, tamanho);

        return repository.findAll(pageable);
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

    public List<Album> processarAlbums(List<DadosAlbum> dadosAlbuns, Artista artista){

        List<Album> albums = dadosAlbuns.stream()
                .filter(a -> a != null)
                .filter(a -> a.anoLancamento() != null)
                .distinct()
                .map(dados -> new Album(dados, artista))
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
                        album.getAnoLancamento(),
                        album.getIdItunes()
                ))
                .toList();
    }
    @Autowired
    private AlbumRepository repository;

    public List<ResumoAlbumDTO> salvar(List<AlbumDTO> dtos, Artista artista) {

        List<Album> albums = dtos.stream()
                .map(dto -> new Album(dto, artista))
                .toList();

        repository.saveAll(albums);

        return albums.stream()
                        .map(a -> new ResumoAlbumDTO(
                                a.getNome(),
                                a.getArtista(),
                                a.getPreco(),
                                a.getAnoLancamento(),
                                a.getCapa()))
                .toList();
    }

    public List<Album> cincoMaisCaros() {

        return repository.cincoAlbumsMaisCaros();
    }

    public List<ResumoAlbumDTO> listarResumo() {


        return repository.listarResumoAlbum();
    }
}
