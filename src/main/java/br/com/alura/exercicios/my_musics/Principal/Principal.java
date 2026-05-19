package br.com.alura.exercicios.my_musics.Principal;

import br.com.alura.exercicios.my_musics.Models.DadosAlbum;
import br.com.alura.exercicios.my_musics.Models.DadosArtista;
import br.com.alura.exercicios.my_musics.Models.DadosBusca;
import br.com.alura.exercicios.my_musics.Models.DadosMusica;
import br.com.alura.exercicios.my_musics.Service.ConsultaGemini;
import br.com.alura.exercicios.my_musics.Service.ConsumoAPI;
import br.com.alura.exercicios.my_musics.Service.ConverteDados;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.stereotype.Component;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Scanner;


@Component
public class Principal {

    private Scanner sc = new Scanner(System.in);
    private ConsumoAPI consumo = new ConsumoAPI();
    private ConverteDados conversor = new ConverteDados();
    private final String ENDERECO = "https://itunes.apple.com/";
    private ConsultaGemini consultaGemini;

    public Principal(ConsultaGemini consultaGemini) {
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
        System.out.println("Artista: " + dados.nome() + " | " + "Gênero: " + dados.genero());
        List<DadosAlbum> albuns = buscarAlbuns(dados.idArtista());
        albuns.stream()
                .filter(a -> a != null)
                .filter(a -> a.anoLancamento() != null)
                .distinct()
                .forEach(album -> {

                    Double preco = album.preco();
                    System.out.printf("Album: %s | Genero: %s | Ano de Lançamento: %d%n | Preço: %s| capa: %s\n",
                            album.nome(),
                            album.genero(),
                            album.anoLancamento().getYear(),
                            preco != null ? String.format("%.2f",preco): "0,00",
                            album.capa());
                    List<DadosMusica> musicas = buscarMusicas(album.idAlbum());

                    musicas.stream()
                            .filter(m -> m.titulo() != null)
                            .forEach(m ->
                        System.out.printf("Faixa : %s | Titulo: %s | Ouça: %s\n",
                                m.numeroFaixa(), m.titulo(), m.linkMusica()));


                });
    }


    //Faz a requisição para buscar as informações do artista (Nome e gênero na API do iTunes)
    private DadosArtista getDadosArtista(){
        System.out.println("Informe o artista desejado: ");
        var nomeArtista = sc.nextLine();
        var artista = URLEncoder.encode(nomeArtista, StandardCharsets.UTF_8);

        var json = consumo.obterDados(ENDERECO + "search?term=" +
                artista
                + "&entity=musicArtist");

        DadosBusca<DadosArtista> dadosArtista = conversor.obterDados(json, new TypeReference<DadosBusca<DadosArtista>>() {
        });

        return dadosArtista
                .results()
                .stream()
                .filter(a -> a.nome().equalsIgnoreCase(nomeArtista))
                .findFirst()
                .orElseThrow(()-> new RuntimeException("Artista ão encontrado"));

    }

    //Faz a requisição e retorna lista de álbuns do artista pesquisado
    public List<DadosAlbum> buscarAlbuns(Long idArtista){

        var json = consumo.obterDados(ENDERECO + "lookup?id=" +
                idArtista
                + "&entity=album");

        DadosBusca dadosAlbum = conversor.obterDados(json, new TypeReference<DadosBusca<DadosAlbum>>() {
        });

        return dadosAlbum.results();
    }

    //Faz a requisição das músicas do artista
    public List<DadosMusica> buscarMusicas(Long idAlbum){
        var json = consumo.obterDados(ENDERECO + "lookup?id=" +
                idAlbum
                + "&entity=song");

        DadosBusca dadosMusica = conversor.obterDados(json, new TypeReference<DadosBusca<DadosMusica>>() {
        });

        return dadosMusica.results();

    }

    //Busca informações dos artistas no Chat GPT
    private void pesquisarArtista(){

        System.out.println("Digite o artista, dupla ou banda que deseja pesquisar: ");
        var pesquisa = sc.nextLine();
        String resposta = consultaGemini.buscarInformacoes(pesquisa);
        System.out.println(resposta);


    }
}
