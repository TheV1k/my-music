package br.com.alura.exercicios.my_musics.Service;

import br.com.alura.exercicios.my_musics.DTO.MusicaDTO;
import br.com.alura.exercicios.my_musics.DTO.ResumoMusicaDTO;
import br.com.alura.exercicios.my_musics.Models.*;
import br.com.alura.exercicios.my_musics.Repository.MusicaRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

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
    public List<ResumoMusicaDTO> salvar(List<Musica> musicas) {

        List<Musica> musicasSalvas = repository.saveAll(musicas);

        return musicasSalvas.stream()
                .map(musica -> new ResumoMusicaDTO(
                        musica.getFaixa(),
                        musica.getTitulo(),
                        musica.getArtista().getNome(),
                        musica.getAlbum().getNome(),
                        musica.getLinkMusica()
                ))
                .toList();
    }

    private ResumoMusicaDTO converterParaResumoDTO(Musica musica) {
        return new ResumoMusicaDTO(
                musica.getFaixa(),            // Integer
                musica.getTitulo(),           // String
                musica.getLinkMusica(),       // String
                musica.getAlbum().getNome(),  // String (Ajuste se na sua classe Album for outro getter)
                musica.getArtista().getNome() // String (Ajuste se na sua classe Artista for outro getter)
        );
    }

    //Busca músicas por titulo
    public Page<ResumoMusicaDTO> buscarMusicaPorTitulo(String tituloMusica, Pageable pageable) {

        Page<Musica> paginasMusicas = repository.findByTituloEqualsIgnoreCase(tituloMusica, pageable);


            return paginasMusicas.map(this::converterParaResumoDTO);

    }

    //Lista as músicas de um album
    public List<MusicaDTO> musicasPorAlbum(String album){

      return converterParaDTO(repository.findByAlbumNomeEqualsIgnoreCase(album));

    }

    //Lista as músicas de um album buscando apenas parte do título
    public List<ResumoMusicaDTO> musicasPorAlbumParteTitulo(String album){

        List<Musica> musicaPorAlbum = repository.findByAlbumNomeContainingIgnoreCase(album);

        return  musicaPorAlbum.stream().map(this::converterParaResumoDTO).toList();


    }

    public Page<ResumoMusicaDTO> listarMusicasPaginados(Pageable pageable) {
        return repository.findAll(pageable).map(musica -> new ResumoMusicaDTO(
                musica.getFaixa(),
                musica.getTitulo(), 
                musica.getArtista().getNome(),
                musica.getAlbum().getNome(),
                musica.getLinkMusica()
        ));
    }
}