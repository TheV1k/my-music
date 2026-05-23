package br.com.alura.exercicios.my_musics.Service;

import com.google.genai.Client;
import com.google.genai.errors.ClientException;
import com.google.genai.types.GenerateContentResponse;
import org.springframework.stereotype.Service;
import java.net.SocketTimeoutException;

@Service
public class ConsultaGemini {

    private final Client client = Client.builder()
            .apiKey(System.getenv("GEMINI_APIKEY"))
            .build();

    public String buscarInformacoes(String pesquisa) {
        try{
            GenerateContentResponse response =
                    client.models.generateContent(
                            "gemini-3.1-flash-lite",
                            """
                                    
                                    Traga um resumo em um parágrafo sobre o sobre o artista, dupla ou banda, 
                                    não faça nenhuma pergunta apenas retorne as informações essenciais sobre: %s
                                    
                                    """.formatted(pesquisa),
                            null
                    );

            return response.text();

        } catch (ClientException e){
        System.out.println("Erro de cliente Gemini: " + e.getMessage());

        if (e.getMessage().contains("quota")) {
            return "A cota da API Gemini foi excedida.";
        }

        if (e.getMessage().contains("API key")) {
            return "API Key do Gemini inválida.";
        }

        return "Erro na requisição para a API Gemini.";

    } catch (RuntimeException e) {

        Throwable causa = e.getCause();

        if (causa instanceof SocketTimeoutException) {

            System.out.println("Timeout Gemini: " + e.getMessage());

            return "A requisição demorou muito para responder.";
        }

        return "Erro inesperado ao consultar o Gemini.";

    } catch (Exception e) {

        System.out.println("Erro geral: " + e.getMessage());

        return "Erro ao buscar informações.";
    }

}
}