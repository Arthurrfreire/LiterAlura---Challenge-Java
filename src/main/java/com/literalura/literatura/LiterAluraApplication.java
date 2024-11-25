package com.literalura.literatura;

import com.literalura.literatura.service.AutorService;
import com.literalura.literatura.service.GutendexService;
import com.literalura.literatura.service.LivroService;
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
			System.out.println("\nEscolha o número de sua opção:");
			System.out.println("1- Buscar livro pelo título");
			System.out.println("2- Listar livros registrados");
			System.out.println("3- Listar autores registrados");
			System.out.println("4- Listar autores vivos em um determinado ano");
			System.out.println("5- Listar livros em um determinado idioma");
			System.out.println("0- Sair");

			int opcao = scanner.nextInt();
			scanner.nextLine(); // Consumir quebra de linha

			switch (opcao) {
				case 1:
					buscarLivro(scanner);
					break;

				case 2:
					listarLivros();
					break;

				case 3:
					listarAutores();
					break;

				case 4:
					listarAutoresVivos(scanner);
					break;

				case 5:
					listarLivrosPorIdioma(scanner);
					break;

				case 0:
					System.out.println("Saindo...");
					return;

				default:
					System.out.println("Opção inválida!");
			}
		}
	}

	private void buscarLivro(Scanner scanner) {
		System.out.println("Digite o título do livro:");
		String titulo = scanner.nextLine();
		try {
			Livro livro = gutendexService.buscarLivroPorTitulo(titulo);
			livroService.salvarLivro(livro);
			System.out.println("\n----- LIVRO -----");
			System.out.println("Título: " + livro.getTitulo());
			System.out.println("Autor: " + livro.getAutor().getNome());
			System.out.println("Idioma: " + livro.getIdioma());
			System.out.println("Número de downloads: " + livro.getDownloads());
			System.out.println("------------------");
		} catch (RuntimeException e) {
			System.out.println("Erro: " + e.getMessage());
		}
	}

	private void listarLivros() {
		System.out.println("\n--- LIVROS REGISTRADOS ---");
		livroService.listarLivros().forEach(livro -> {
			System.out.println("----- LIVRO -----");
			System.out.println("Título: " + livro.getTitulo());
			System.out.println("Autor: " + livro.getAutor().getNome());
			System.out.println("Idioma: " + livro.getIdioma());
			System.out.println("Número de downloads: " + livro.getDownloads());
			System.out.println("------------------");
		});
	}

	private void listarAutores() {
		System.out.println("\n--- AUTORES REGISTRADOS ---");
		autorService.listarAutoresRegistrados().forEach(autor -> {
			System.out.println("Nome: " + autor.getNome());
			System.out.println("Ano de nascimento: " + autor.getAnoNascimento());
			System.out.println("Ano de falecimento: " +
					(autor.getAnoFalecimento() != null ? autor.getAnoFalecimento() : "Vivo"));
			System.out.println("-----");
		});
	}

	private void listarAutoresVivos(Scanner scanner) {
		System.out.println("Digite o ano desejado:");
		int ano = scanner.nextInt();
		scanner.nextLine(); // Consumir quebra de linha
		System.out.println("\n--- AUTORES VIVOS EM " + ano + " ---");
		autorService.listarAutoresRegistrados().stream()
				.filter(autor -> autor.getAnoNascimento() <= ano &&
						(autor.getAnoFalecimento() == null || autor.getAnoFalecimento() >= ano))
				.forEach(autor -> {
					System.out.println("Nome: " + autor.getNome());
					System.out.println("Ano de nascimento: " + autor.getAnoNascimento());
					System.out.println("Ano de falecimento: " +
							(autor.getAnoFalecimento() != null ? autor.getAnoFalecimento() : "Vivo"));
					System.out.println("-----");
				});
	}

	private void listarLivrosPorIdioma(Scanner scanner) {
		System.out.println("Digite o idioma (ex.: pt, en, fr):");
		String idioma = scanner.nextLine();
		System.out.println("\n--- LIVROS NO IDIOMA: " + idioma.toUpperCase() + " ---");
		var livrosPorIdioma = livroService.listarLivrosPorIdioma(idioma);
		if (livrosPorIdioma.isEmpty()) {
			System.out.println("Nenhum livro encontrado para o idioma especificado.");
		} else {
			livrosPorIdioma.forEach(l -> {
				System.out.println("Título: " + l.getTitulo());
				System.out.println("Autor: " + l.getAutor().getNome());
				System.out.println("Idioma: " + l.getIdioma());
				System.out.println("Downloads: " + l.getDownloads());
				System.out.println("-----");
			});
		}
	}
}
