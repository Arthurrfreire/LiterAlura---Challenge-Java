package com.literalura.literatura.controller;

import com.literalura.literatura.service.GutendexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class LivroController {

    @Autowired
    private GutendexService gutendexService;

    @GetMapping("/livros/buscar")
    public Map<String, Object> buscarLivro(@RequestParam String titulo) {
        return gutendexService.buscarLivroPorTitulo(titulo);
    }
}
