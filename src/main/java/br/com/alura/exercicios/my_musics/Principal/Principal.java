package br.com.alura.exercicios.my_musics.Principal;

import br.com.alura.exercicios.my_musics.DTO.AlbumDTO;
import br.com.alura.exercicios.my_musics.DTO.ArtistaDTO;
import br.com.alura.exercicios.my_musics.DTO.MusicaDTO;
import br.com.alura.exercicios.my_musics.Models.*;
import br.com.alura.exercicios.my_musics.Repository.AlbumRepository;
import br.com.alura.exercicios.my_musics.Repository.ArtistaRepository;
import br.com.alura.exercicios.my_musics.Repository.MusicaRepository;
import br.com.alura.exercicios.my_musics.Service.*;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;


@Component
public class Principal {

    private ArtistaRepository repositorioArtista;
    private AlbumRepository repositorioAlbum;
    private MusicaRepository repositorioMusica;

    private Scanner sc = new Scanner(System.in);
    private ConsumoAPI consumo = new ConsumoAPI();
    private ConverteDados conversor = new ConverteDados();
    private final String ENDERECO = "https://itunes.apple.com/";

    private final ArtistaService artistaService;
    private final AlbumService albumService;
    private final MusicaService musicaService;
    private ConsultaGemini consultaGemini;


    public Principal(ArtistaRepository repositorioArtista, AlbumRepository repositorioAlbum, MusicaRepository repositorioMusica, ArtistaService artistaService, AlbumService albumService, MusicaService musicaService, ConsultaGemini consultaGemini) {
        this.repositorioArtista = repositorioArtista;
        this.repositorioAlbum = repositorioAlbum;
        this.repositorioMusica = repositorioMusica;
        this.artistaService = artistaService;
        this.albumService = albumService;
        this.musicaService = musicaService;
        this.consultaGemini = consultaGemini;
    }

    public void exibeMenu(){
        var menu = """
                   1 - Buscar Artista
                   2 - Pesquisar sobre o artista
                   0- Sair
                   """;
        System.out.println(menu);

        var opcao = sc.nextInt();
        sc.nextLine();

        switch (opcao){
            case 1:
                buscarArtistaWeb();
                break;
            case 2:
                pesquisarArtista();
                break;
            case 0:
                System.out.println("Saindo...");
                break;
        }
    }

    //Busca as informações dos artistas na web
    private void buscarArtistaWeb(){
        DadosArtista dados = getDadosArtista();
        ArtistaDTO artistaDTO = new ArtistaDTO(dados.nome(), Genero.fromString(dados.genero()));
        Artista artista = new Artista(artistaDTO);
        repositorioArtista.save(artista);
        //Busca os albums do artista pesquisado na api do iTunes e processa os dados
        List<DadosAlbum> dadosAlbums = buscarAlbums(dados.idArtista());
        List<Album> albums = albumService.processarAlbums(dadosAlbums);
        //Converte a lista de albums do artista para DTO e salva no banco de dados
        List<AlbumDTO> albunsDTO = albumService.converterParaDTO(albums);
        albumService.salvar(albunsDTO);
        //Busca as músicas do artista pesquisado na api do iTunes
        dadosAlbums.forEach(album -> {
            List<DadosMusica> dadosMusicas =  musicaService.buscarMusicas(album.idAlbum());
            List<Musica> musicas =  musicaService.processarMusicas(album.idAlbum());
          //Converte os dados das músicas para DTO
            List<MusicaDTO> musicaDTO = musicaService.converterParaDTO(musicas);
            musicaService.salvar(musicaDTO);
        });

        System.out.println("Artista: " + dados.nome() + " | " + "Gênero: " + dados.genero() + " salvos no My Music");
    }

    //Faz a requisição para buscar as informações do artista (Nome e gênero na API do iTunes)
    private DadosArtista getDadosArtista() {
        System.out.println("Informe o artista desejado: ");
        var nomeArtista = sc.nextLine();
        return artistaService.getDadosArtista(nomeArtista);
    }

    //Faz a requisição e retorna lista de álbuns do artista pesquisado
    public List<DadosAlbum> buscarAlbums(Long idArtista){
        return albumService.buscarAlbums(idArtista);

    }


    //Busca informações dos artistas no Chat GPT
    private void pesquisarArtista(){

        System.out.println("Digite o artista, dupla ou banda que deseja pesquisar: ");
        var pesquisa = sc.nextLine();
        String resposta = consultaGemini.buscarInformacoes(pesquisa);
        System.out.println(resposta);

    }
}

