package br.com.alura.exercicios.my_musics.Service;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;
import org.springframework.stereotype.Service;

@Service
public class ConsultaGemini {

    private final Client client = Client.builder()
            .apiKey(System.getenv("GEMINI_APIKEY"))
            .build();

    public String buscarInformacoes(String pesquisa) {

        GenerateContentResponse response =
                client.models.generateContent(
                        "gemini-3.1-flash-lite",
                        "Traga um resumo em um parágrafo sobre o sobre o artista, dupla ou banda: " + pesquisa,
                        null
                );

        return response.text();
    }
}