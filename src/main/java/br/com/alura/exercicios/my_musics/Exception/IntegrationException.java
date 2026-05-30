package br.com.alura.exercicios.my_musics.Exception;

public class IntegrationException extends RuntimeException {
    public IntegrationException(String message) {

        super(message);
    }

    //Passa a real causa da exception
    public IntegrationException(String message, Throwable cause) {
        super(message, cause);
    }
}
