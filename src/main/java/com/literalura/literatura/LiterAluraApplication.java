package com.literalura.literatura;

import com.literalura.literatura.service.LivroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class LiterAluraApplication implements CommandLineRunner {

	@Autowired
	private LivroService livroService;

	public static void main(String[] args) {
		SpringApplication.run(LiterAluraApplication.class, args);
	}

	@Override
	public void run(String... args) {
		Scanner scanner = new Scanner(System.in);
		int opcao;

		do {
			System.out.println("Escolha o número de sua opção:");
			System.out.println("1- Buscar livro pelo título");
			System.out.println("2- Listar livros registrados");
			System.out.println("3- Listar autores registrados");
			System.out.println("4- Listar autores vivos em um determinado ano");
			System.out.println("5- Listar livros em um determinado idioma");
			System.out.println("0- Sair");

			opcao = scanner.nextInt();
			scanner.nextLine(); // Consumir a quebra de linha

			switch (opcao) {
				case 1 -> {
					System.out.print("Digite o título do livro: ");
					String titulo = scanner.nextLine();
					livroService.buscarLivroPorTitulo(titulo);
				}
				case 2 -> livroService.listarLivrosRegistrados();
				case 3 -> livroService.listarAutoresRegistrados();
				case 4 -> {
					System.out.print("Digite o ano: ");
					int ano = scanner.nextInt();
					scanner.nextLine(); // Consumir a quebra de linha
					livroService.listarAutoresVivosPorAno(ano);
				}
				case 5 -> {
					System.out.print("Digite o idioma (ex: pt, en): ");
					String idioma = scanner.nextLine();
					livroService.listarLivrosPorIdioma(idioma);
				}
				case 0 -> System.out.println("Saindo...");
				default -> System.out.println("Opção inválida.");
			}
		} while (opcao != 0);
	}
}
