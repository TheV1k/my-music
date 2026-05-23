package br.com.alura.exercicios.my_musics.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.stereotype.Service;

@Service
public class ConverteDados implements IConverteDados {
    private ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());

    //Converte os dados para Json
    @Override
    public <T> T obterDados(String json, TypeReference<T> typeReference) {
        try {
            return mapper.readValue(json, typeReference);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}




