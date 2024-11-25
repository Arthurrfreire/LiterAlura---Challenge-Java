package com.literalura.literatura;

import com.literalura.literatura.service.AutorService;
import com.literalura.literatura.service.LivroService;
import com.literalura.literatura.service.GutendexService;
import com.literalura.literatura.model.Livro;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class LiterAluraApplication implements CommandLineRunner {

	@Autowired
	private LivroService livroService;

	@Autowired
	private AutorService autorService;

	@Autowired
	private GutendexService gutendexService;

	public static void main(String[] args) {
		SpringApplication.run(LiterAluraApplication.class, args);
	}

	@Override
	public void run(String... args) {
		Scanner scanner = new Scanner(System.in);

		while (true) {
			System.out.println("Escolha uma opção:");
			System.out.println("1- Buscar e salvar livro");
			System.out.println("2- Listar livros registrados");
			System.out.println("3- Listar autores registrados");
			System.out.println("0- Sair");

			int opcao = scanner.nextInt();
			scanner.nextLine(); // Consumir a quebra de linha

			switch (opcao) {
				case 1:
					System.out.println("Digite o título do livro:");
					String titulo = scanner.nextLine();
					Livro livro = gutendexService.buscarLivroPorTitulo(titulo);
					livroService.salvarLivro(livro);
					System.out.println("Livro salvo com sucesso!");
					break;

				case 2:
					System.out.println("Lista de livros registrados:");
					livroService.listarLivros().forEach(livroRegistrado -> {
						System.out.println("Título: " + livroRegistrado.getTitulo());
						System.out.println("Autor: " + livroRegistrado.getAutor().getNome());
						System.out.println("Idioma: " + livroRegistrado.getIdioma());
						System.out.println("Downloads: " + livroRegistrado.getDownloads());
						System.out.println("---");
					});
					break;

				case 3:
					System.out.println("Lista de autores registrados:");
					autorService.listarAutoresRegistrados().forEach(autor -> {
						System.out.println("Nome: " + autor.getNome());
						System.out.println("Ano de nascimento: " + autor.getAnoNascimento());
						System.out.println("Ano de falecimento: " + (autor.getAnoFalecimento() != null ? autor.getAnoFalecimento() : "Vivo"));
						System.out.println("---");
					});
					break;

				case 0:
					System.out.println("Saindo...");
					return;

				default:
					System.out.println("Opção inválida!");
			}
		}
	}
}
