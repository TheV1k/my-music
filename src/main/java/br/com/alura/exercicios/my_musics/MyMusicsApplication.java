package br.com.alura.exercicios.my_musics;

import br.com.alura.exercicios.my_musics.Principal.Principal;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MyMusicsApplication implements CommandLineRunner {

	private final Principal principal;

	public MyMusicsApplication(Principal principal) {
		this.principal = principal;
	}

	public static void main(String[] args) {
		SpringApplication.run(MyMusicsApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		principal.exibeMenu();

	}
}
