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

        Map response = restTemplate.getForObject(url, Map.class);
        List<Map<String, Object>> results = (List<Map<String, Object>>) response.get("results");

        if (results.isEmpty()) {
            throw new RuntimeException("Livro não encontrado na API.");
        }

        Map<String, Object> livroData = results.get(0);
        Livro livro = new Livro();
        livro.setTitulo((String) livroData.get("title"));
        livro.setIdioma(((List<String>) livroData.get("languages")).get(0));
        livro.setDownloads((Integer) livroData.get("download_count"));

        Map<String, Object> autorData = ((List<Map<String, Object>>) livroData.get("authors")).get(0);
        Autor autor = new Autor();
        autor.setNome((String) autorData.get("name"));
        livro.setAutor(autor);

        return livro;
    }
}
