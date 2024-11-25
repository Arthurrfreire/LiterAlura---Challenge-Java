package com.literalura.literatura;

import com.literalura.literatura.model.Livro;
import com.literalura.literatura.service.AutorService;
import com.literalura.literatura.service.GutendexService;
import com.literalura.literatura.service.LivroService;
import io.github.cdimascio.dotenv.Dotenv;
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
		Dotenv dotenv = Dotenv.load();

		String dbUrl = dotenv.get("DB_URL");
		String dbUsername = dotenv.get("DB_USERNAME");
		String dbPassword = dotenv.get("DB_PASSWORD");

		System.out.println("URL do Banco: " + dbUrl);
		System.out.println("Usuário: " + dbUsername);

		SpringApplication.run(LiterAluraApplication.class, args);
	}

	@Override
	public void run(String... args) {
		Scanner scanner = new Scanner(System.in);
		int opcao;

		do {
			exibirMenu();
			opcao = Integer.parseInt(scanner.nextLine());

			switch (opcao) {
				case 1:
					buscarLivroPeloTitulo(scanner);
					break;
				case 2:
					listarLivrosRegistrados();
					break;
				case 3:
					listarAutoresRegistrados();
					break;
				case 4:
					listarAutoresVivosPorAno(scanner);
					break;
				case 5:
					listarLivrosPorIdioma(scanner);
					break;
				case 0:
					System.out.println("Saindo do sistema...");
					break;
				default:
					System.out.println("Opção inválida. Tente novamente.");
			}

		} while (opcao != 0);

		scanner.close();
	}

	private void exibirMenu() {
		System.out.println("\nEscolha o número de sua opção:");
		System.out.println("1- Buscar livro pelo título");
		System.out.println("2- Listar livros registrados");
		System.out.println("3- Listar autores registrados");
		System.out.println("4- Listar autores vivos em um determinado ano");
		System.out.println("5- Listar livros em um determinado idioma");
		System.out.println("0- Sair");
	}

	private void buscarLivroPeloTitulo(Scanner scanner) {
		System.out.print("Digite o título do livro: ");
		String titulo = scanner.nextLine();
		try {
			Livro livro = gutendexService.buscarLivroPorTitulo(titulo);

			livroService.salvarLivro(livro);

			System.out.println("\n----- LIVRO ENCONTRADO -----");
			System.out.println("Título: " + livro.getTitulo());
			System.out.println("Autor: " + livro.getAutor().getNome());
			System.out.println("Idioma: " + livro.getIdioma());
			System.out.println("Número de downloads: " + livro.getDownloads());
			System.out.println("-----------------------------\n");
		} catch (RuntimeException e) {
			System.out.println("Erro: " + e.getMessage());
		}
	}

	private void listarLivrosRegistrados() {
		System.out.println("\n----- LIVROS REGISTRADOS -----");
		livroService.listarLivros().forEach(livro -> {
			System.out.println("Título: " + livro.getTitulo());
			System.out.println("Autor: " + livro.getAutor().getNome());
			System.out.println("Idioma: " + livro.getIdioma());
			System.out.println("Número de downloads: " + livro.getDownloads());
			System.out.println("-----------------------------");
		});
	}

	private void listarAutoresRegistrados() {
		System.out.println("\n----- AUTORES REGISTRADOS -----");
		autorService.listarAutoresRegistrados().forEach(autor -> {
			System.out.println("Nome: " + autor.getNome());
			System.out.println("Ano de nascimento: " + autor.getAnoNascimento());
			System.out.println("Ano de falecimento: " + autor.getAnoFalecimento());
			System.out.println("-------------------------------");
		});
	}

	private void listarAutoresVivosPorAno(Scanner scanner) {
		System.out.print("Digite o ano: ");
		int ano = Integer.parseInt(scanner.nextLine());

		System.out.println("\n----- AUTORES VIVOS NO ANO " + ano + " -----");
		autorService.listarAutoresRegistrados().stream()
				.filter(autor -> autor.getAnoNascimento() <= ano &&
						(autor.getAnoFalecimento() == null || autor.getAnoFalecimento() > ano))
				.forEach(autor -> {
					System.out.println("Nome: " + autor.getNome());
					System.out.println("Ano de nascimento: " + autor.getAnoNascimento());
					System.out.println("-------------------------------");
				});
	}

	private void listarLivrosPorIdioma(Scanner scanner) {
		System.out.print("Digite o idioma (ex.: 'en', 'pt'): ");
		String idioma = scanner.nextLine();

		System.out.println("\n----- LIVROS EM " + idioma + " -----");
		livroService.listarLivrosPorIdioma(idioma).forEach(livro -> {
			System.out.println("Título: " + livro.getTitulo());
			System.out.println("Autor: " + livro.getAutor().getNome());
			System.out.println("-------------------------------");
		});
	}
}
