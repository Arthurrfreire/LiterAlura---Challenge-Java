package com.literalura.literatura.service;

import com.literalura.literatura.model.Autor;
import com.literalura.literatura.repository.AutorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AutorService {

    private final AutorRepository autorRepository;

    public AutorService(AutorRepository autorRepository) {
        this.autorRepository = autorRepository;
    }

    public Autor criarAutor(Autor autor) {
        return autorRepository.save(autor);
    }

    public List<Autor> listarAutoresRegistrados() {
        return autorRepository.findAll();
    }
}
