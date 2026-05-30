package br.com.alura.exercicios.my_musics.Exception;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;

public record ErrorResponse(

        // Formata a data no JSON para o padrão ISO (ex: "2026-05-28T23:30:00")
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime timestamp,

        // Código de status HTTP (ex: 404, 502)
        int status,

        // O título/nome do erro (ex: "Recurso não encontrado")
        String error,

        // A mensagem detalhada explicando o que aconteceu (ex: "Artista com ID 5 não encontrado")
        String message,

        // O endpoint/URI onde o erro aconteceu (ex: "/api/ia/biografia")
        String path
) {}
