package com.literalura.literatura.service;

import com.literalura.literatura.model.Autor;
import com.literalura.literatura.model.Livro;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class GutendexService {
    private final RestTemplate restTemplate;

    public GutendexService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Livro buscarLivroPorTitulo(String titulo) {
        String url = "https://gutendex.com/books?search=" + titulo;

        // Fazendo a requisição para a API Gutendex
        Map<String, Object> response = restTemplate.getForObject(url, Map.class);

        if (response == null || !response.containsKey("results")) {
            throw new RuntimeException("Erro ao consultar a API Gutendex.");
        }

        List<Map<String, Object>> results = (List<Map<String, Object>>) response.get("results");
        if (results == null || results.isEmpty()) {
            throw new RuntimeException("Livro não encontrado na API.");
        }

        Map<String, Object> livroData = results.get(0);

        // Processa os dados para criar a entidade Livro
        Livro livro = new Livro();
        livro.setTitulo((String) livroData.get("title"));
        livro.setIdioma(((List<String>) livroData.get("languages")).get(0));
        livro.setDownloads(((Number) livroData.get("download_count")).intValue());

        // Processa o autor
        Map<String, Object> autorData = ((List<Map<String, Object>>) livroData.get("authors")).get(0);
        Autor autor = new Autor();
        autor.setNome((String) autorData.get("name"));
        autor.setAnoNascimento((Integer) autorData.get("birth_year"));
        autor.setAnoFalecimento((Integer) autorData.get("death_year"));

        livro.setAutor(autor);

        return livro;
    }
}
