package br.com.alura.exercicios.my_musics.Service;

import br.com.alura.exercicios.my_musics.DTO.AlbumDTO;
import br.com.alura.exercicios.my_musics.DTO.MusicaDTO;
import br.com.alura.exercicios.my_musics.DTO.ResumoAlbumDTO;
import br.com.alura.exercicios.my_musics.DTO.ResumoMusicaDTO;
import br.com.alura.exercicios.my_musics.Models.*;
import br.com.alura.exercicios.my_musics.Repository.MusicaRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MusicaService extends BaseService {

    private final MusicaRepository repository;


    //Paginação
    public Page<Musica> listarMusicasPaginados(int pagina, int tamanho){
        PageRequest pageable = PageRequest.of(pagina,tamanho);

        return repository.findAll(pageable);
    }


    public MusicaService(ConsumoAPI consumo,
                         ConverteDados conversor,
                         MusicaRepository repository) {

        super(consumo, conversor);
        this.repository = repository;
    }

    // Faz a requisição das músicas do álbum
    public List<DadosMusica> buscarMusicas(Long idAlbum){

        var endereco = ENDERECO + "lookup?id=" +
                idAlbum +
                "&entity=song";

        System.out.println("URL: " + endereco);

        var json = consumo.obterDados(endereco);

        DadosBusca<DadosMusica> dadosMusica =
                conversor.obterDados(
                        json,
                        new TypeReference<DadosBusca<DadosMusica>>() {}
                );

        return dadosMusica.results();
    }


    //Processa a lista de músicas vindas da API
    public List<Musica> processarMusicas(Long idAlbum,Album album, Artista artista){

        List<DadosMusica> dadosMusicas =
                buscarMusicas(idAlbum);

        return dadosMusicas.stream()
                .filter(m -> m.titulo() != null)
                .map(dto -> new Musica(dto,album,artista))
                .toList();
    }

    //Converte dados para DTO

    public List<MusicaDTO> converterParaDTO(List<Musica> musicas){

        return musicas.stream()
                .map(musica -> new MusicaDTO(
                        musica.getFaixa(),
                        musica.getTitulo(),
                        musica.getLinkMusica()
                ))
                .toList();
    }

    //Salvar músicas
    public List<ResumoMusicaDTO> salvar(List<MusicaDTO> dtos, Album album, Artista artista) {

        List<Musica> musicasSalvas = dtos.stream()
                .map(dto -> new Musica(
                        dto,
                        album,
                        artista))
                .toList();

        repository.saveAll(musicasSalvas);

        return musicasSalvas.stream()
                .map(musica -> new ResumoMusicaDTO(
                        musica.getFaixa(),
                        musica.getTitulo(),
                        musica.getAlbum().getNome(),
                        musica.getAlbum().getArtista(),
                        musica.getLinkMusica()
                ))
                .toList();
    }

    //Busca músicas por titulo
    public List<Musica> buscarMusicaPorTitulo(String tituloMusica) {
            List<Musica> musicas = repository.findByTituloEqualsIgnoreCase(tituloMusica)
                    .stream()
                    .toList();

            if (musicas.isEmpty()){
                throw new RuntimeException("Nenhuma música encontrada!");
            }
            return musicas;

    }

    //Lista as músicas de um album
    public List<Musica> musicasPorAlbum(String album){

        List<Musica> musicaPorAlbum = repository.findByAlbumNomeEqualsIgnoreCase(album);

        return  musicaPorAlbum;

    }

    //Lista as músicas de um album buscando apenas parte do título
    public List<Musica> musicasPorAlbumParteTitulo(String album){

        List<Musica> musicaPorAlbum = repository.findByAlbumNomeContainingIgnoreCase(album);

        return  musicaPorAlbum;

    }
}