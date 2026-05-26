package br.com.alura.exercicios.my_musics.Service;

import br.com.alura.exercicios.my_musics.Models.Album;
import br.com.alura.exercicios.my_musics.Models.Musica;

import java.util.List;

public abstract class BaseService {

    protected final ConsumoAPI consumo;
    protected final ConverteDados conversor;

    protected static final String ENDERECO =
            "https://itunes.apple.com/";

    protected BaseService(ConsumoAPI consumo,
                          ConverteDados conversor) {

        this.consumo = consumo;
        this.conversor = conversor;
    }



}
