package br.com.alura.exercicios.my_musics.Controller;

import br.com.alura.exercicios.my_musics.Service.ConsultaGemini;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ia")
public class GeminiController {

    private final ConsultaGemini geminiService;

    // Injeção de dependência via construtor (Boa prática!)
    public GeminiController(ConsultaGemini geminiService) {
        this.geminiService = geminiService;
    }

    @GetMapping("/biografia")
    public ResponseEntity<String> obterBiografiaArtista(@RequestParam String artista) {
        // Validação simples antes de chamar a IA
        if (artista == null || artista.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("O nome do artista não pode estar vazio.");
        }

        // Chama o serviço que se comunica com o Spring AI / Gemini
        String biografia = geminiService.buscarInformacoes(artista);

        return ResponseEntity.ok(biografia);
    }
}
