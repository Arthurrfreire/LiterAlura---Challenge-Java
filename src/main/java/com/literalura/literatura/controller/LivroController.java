import com.literalura.literatura.model.Livro;
import com.literalura.literatura.service.LivroService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/livros")
public class LivroController {

    private final LivroService livroService;

    public LivroController(LivroService livroService) {
        this.livroService = livroService;
    }

    @GetMapping("/idioma/{idioma}")
    public List<Livro> listarLivrosPorIdioma(@PathVariable String idioma) {
        return livroService.listarLivrosPorIdioma(idioma);
    }
}
