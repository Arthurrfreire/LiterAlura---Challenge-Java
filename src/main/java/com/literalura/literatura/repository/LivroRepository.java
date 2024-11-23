package com.literalura.literatura.repository;

import com.literalura.literatura.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Long> {
    List<Livro> findByIdioma(String idioma);

    Optional<Livro> findByTituloIgnoreCase(String titulo);
}
