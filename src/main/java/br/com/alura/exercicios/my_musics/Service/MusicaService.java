package br.com.alura.exercicios.my_musics.Service;

import br.com.alura.exercicios.my_musics.DTO.AlbumDTO;
import br.com.alura.exercicios.my_musics.DTO.MusicaDTO;
import br.com.alura.exercicios.my_musics.Models.Album;
import br.com.alura.exercicios.my_musics.Models.DadosBusca;
import br.com.alura.exercicios.my_musics.Models.DadosMusica;
import br.com.alura.exercicios.my_musics.Models.Musica;
import br.com.alura.exercicios.my_musics.Repository.MusicaRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MusicaService extends BaseService {

    private final MusicaRepository repository;

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

    public List<Musica> processarMusicas(Long idAlbum){

        List<DadosMusica> dadosMusicas =
                buscarMusicas(idAlbum);

        return dadosMusicas.stream()
                .filter(m -> m.titulo() != null)
                .map(Musica::new)
                .toList();
    }

    public List<MusicaDTO> converterParaDTO(List<Musica> musicas){

        return musicas.stream()
                .map(musica -> new MusicaDTO(
                        musica.getFaixa(),
                        musica.getTitulo(),
                        musica.getLinkMusica()
                ))
                .toList();
    }

    public List<Musica> salvar(List<MusicaDTO> dtos) {

        List<Musica> musica = dtos.stream()
                .map(Musica::new)
                .toList();

        return repository.saveAll(musica);
    }
}