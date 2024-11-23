package com.literalura.literatura.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Service
public class LivroService {

    private final HttpClient client = HttpClient.newBuilder()
            .followRedirects(HttpClient.Redirect.ALWAYS)
            .build();
    private final ObjectMapper objectMapper = new ObjectMapper();

    private static final String BASE_URL = "https://gutendex.com/books";

    public void buscarLivroPorTitulo(String titulo) {
        try {
            String url = BASE_URL + "?search=" + titulo.replace(" ", "%20");
            System.out.println("Buscando livro pelo título na API: " + titulo);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                JsonNode root = objectMapper.readTree(response.body());
                if (root.get("count").asInt() > 0) {
                    JsonNode book = root.get("results").get(0);
                    exibirLivro(book);
                } else {
                    System.out.println("Livro não encontrado na API.");
                }
            } else {
                System.out.println("Erro ao consultar a API. Código de status: " + response.statusCode());
            }
        } catch (Exception e) {
            System.out.println("Erro ao buscar livro: " + e.getMessage());
        }
    }

    public void listarLivrosRegistrados() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                JsonNode root = objectMapper.readTree(response.body());
                System.out.println("Livros registrados na API:");
                root.get("results").forEach(this::exibirLivro);
            } else {
                System.out.println("Erro ao consultar a API. Código de status: " + response.statusCode());
            }
        } catch (Exception e) {
            System.out.println("Erro ao listar livros: " + e.getMessage());
        }
    }

    public void listarAutoresRegistrados() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                JsonNode root = objectMapper.readTree(response.body());
                List<String> authors = new ArrayList<>();
                root.get("results").forEach(book -> {
                    book.get("authors").forEach(author -> {
                        if (!authors.contains(author.get("name").asText())) {
                            authors.add(author.get("name").asText());
                        }
                    });
                });
                System.out.println("Autores registrados:");
                authors.forEach(author -> System.out.println("- " + author));
            } else {
                System.out.println("Erro ao consultar a API. Código de status: " + response.statusCode());
            }
        } catch (Exception e) {
            System.out.println("Erro ao listar autores: " + e.getMessage());
        }
    }

    public void listarAutoresVivosPorAno(int ano) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                JsonNode root = objectMapper.readTree(response.body());
                System.out.println("Autores vivos no ano " + ano + ":");
                root.get("results").forEach(book -> {
                    book.get("authors").forEach(author -> {
                        int birthYear = author.get("birth_year").asInt();
                        int deathYear = author.get("death_year").asInt(-1); // -1 if not present
                        if (birthYear <= ano && (deathYear == -1 || deathYear >= ano)) {
                            System.out.println("- " + author.get("name").asText());
                        }
                    });
                });
            } else {
                System.out.println("Erro ao consultar a API. Código de status: " + response.statusCode());
            }
        } catch (Exception e) {
            System.out.println("Erro ao listar autores vivos: " + e.getMessage());
        }
    }

    public void listarLivrosPorIdioma(String idioma) {
        try {
            String url = BASE_URL + "?languages=" + idioma;

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                JsonNode root = objectMapper.readTree(response.body());
                System.out.println("Livros no idioma '" + idioma + "':");
                root.get("results").forEach(this::exibirLivro);
            } else {
                System.out.println("Erro ao consultar a API. Código de status: " + response.statusCode());
            }
        } catch (Exception e) {
            System.out.println("Erro ao listar livros por idioma: " + e.getMessage());
        }
    }

    private void exibirLivro(JsonNode book) {
        System.out.println("----- LIVRO -----");
        System.out.println("Título: " + book.get("title").asText());
        System.out.println("Autor: " + book.get("authors").get(0).get("name").asText());
        System.out.println("Idioma: " + book.get("languages").get(0).asText());
        System.out.println("Número de downloads: " + book.get("download_count").asInt());
        System.out.println("-----------------");
    }
}
