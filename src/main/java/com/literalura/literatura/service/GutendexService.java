package com.literalura.literatura.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

@Service
public class GutendexService {

    private final HttpClient client = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public Map<String, Object> buscarLivroPorTitulo(String titulo) {
        try {
            String url = "https://gutendex.com/books?search=" + titulo;
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            JsonNode jsonNode = objectMapper.readTree(response.body());

            // Converter JsonNode para Map
            return objectMapper.convertValue(jsonNode, Map.class);

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Erro ao buscar livro por título: " + e.getMessage(), e);
        }
    }
}
