package br.com.alura.exercicios.my_musics.Principal;

import br.com.alura.exercicios.my_musics.DTO.AlbumDTO;
import br.com.alura.exercicios.my_musics.DTO.ArtistaDTO;
import br.com.alura.exercicios.my_musics.DTO.MusicaDTO;
import br.com.alura.exercicios.my_musics.Models.*;
import br.com.alura.exercicios.my_musics.Repository.AlbumRepository;
import br.com.alura.exercicios.my_musics.Repository.ArtistaRepository;
import br.com.alura.exercicios.my_musics.Repository.MusicaRepository;
import br.com.alura.exercicios.my_musics.Service.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
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
        var opcao = -1;
        while (opcao != 0){
        var menu = """
                   1 - Cadastrar Artista
                   2 - Pesquisar sobre o artista
                   3 - Buscar Artista por parte do nome
                   4 - Pesquisar Música pelo titulo
                   5 - Listar cinco Albums Mais caros
                   6 - Lista as músicas de um album (Busca pelo título)
                   7 - Buscar Músicas de um album usando parte do nome
                   8 - Buscar albums de um artista
                   0- Sair
                   """;
        System.out.println(menu);

        opcao = sc.nextInt();
        sc.nextLine();
        switch (opcao){
            case 1:
                buscarArtistaWeb();
                break;
            case 2:
                pesquisarArtista();
                break;
            case 3:
                buscaArtistaParteDoNome();
                break;
            case 4:
                buscarMusicaPorTitulo();
                break;
            case 5:
                listaCincoAlbumsMaiscaros();
                break;
            case 6:
                listarMusicasDeUmAlbum();
                break;
            case 0:
                System.out.println("Saindo...");
                break;
            case 7:
                listarMusicasParteNomeAlbum();
                break;

            case 8:
                ListarAlbumsDeUmArtista();
                break;
            default:
                System.out.println("Opção inválida");
            }
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
        List<Album> albums = albumService.processarAlbums(dadosAlbums, artista);
        //Converte a lista de albums do artista para DTO e salva no banco de dados
        List<AlbumDTO> albunsDTO = albumService.converterParaDTO(albums);

        List<Album> albumsSalvos =
                albumService.salvar(
                        albumService.converterParaDTO(albums),
                        artista
                );
        //Busca as músicas do artista pesquisado na api do iTunes
        albumsSalvos.forEach(album -> {
            List<Musica> musicas =  musicaService.processarMusicas(album.getIdItunes(), album, artista);
          //Converte os dados das músicas para DTO
            List<MusicaDTO> musicaDTO = musicaService.converterParaDTO(musicas);
            musicaService.salvar(musicas);
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

    //Busca artista utilizando parte do nome
    private void buscaArtistaParteDoNome(){

        System.out.println("Digite o artista a ser pesquisado");
        var nomeArtista = sc.nextLine();
        var pesquisa = artistaService.buscaArtistaParteDoNome(nomeArtista);
        System.out.println(pesquisa);

    }

    //Busca músicas pelo título
    private void buscarMusicaPorTitulo(){
        System.out.println("Digite o titulo da música: ");
        var tituloMusica = sc.nextLine();
        List<Musica> pesquisa = musicaService.buscarMusicaPorTitulo(tituloMusica);
        pesquisa.stream().forEach(System.out::println);

    }

    //Lista os cinco albums mais caros
    private void listaCincoAlbumsMaiscaros(){

        List<Album> albumsMaisCaros = albumService.cincoMaisCaros()
                .stream()
                .toList();
        albumsMaisCaros.forEach(System.out::println);

    }

    //Lista as músicas de um album
    private void listarMusicasDeUmAlbum(){

        System.out.println("Digite o album desejado: ");
        var album = sc.nextLine();
        List<Musica> albumPesquisado = musicaService.musicasPorAlbum(album)
                .stream()
                .toList();

        albumPesquisado.forEach(musica -> System.out.println("Album: " + musica.getAlbum().getNome() +
                " | Faixa:  " + musica.getFaixa() +  "|Titulo: " + musica.getTitulo() + " | Link: " + musica.getLinkMusica()));
    }

    private void listarMusicasParteNomeAlbum(){


        System.out.println("Digite o album desejado: ");
        var album = sc.nextLine();
        List<Musica> albumPesquisado = musicaService.musicasPorAlbumParteTitulo(album).stream().toList();

        albumPesquisado.forEach(musica -> System.out.println("Album: " + musica.getAlbum().getNome() +
                " | Faixa:  " + musica.getFaixa() +  "|Titulo: " + musica.getTitulo() + " | Link: " + musica.getLinkMusica()));


    }

    private void ListarAlbumsDeUmArtista(){

        System.out.println("Digite o artista desejado: ");
        var pesquisa = sc.nextLine();
        List<Album> albums = artistaService.listarAlbumsDoArtista(pesquisa);
        albums.forEach(album -> System.out.println("Artista: " + album.getArtista()+ " | Album: " + album.getNome() + "Ano: " + album.getAnoLancamento() + "Capa: " +album.getCapa()));

    }
}

