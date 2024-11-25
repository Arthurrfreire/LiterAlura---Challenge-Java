import com.literalura.literatura.model.Autor;
import com.literalura.literatura.service.AutorService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/autores")
public class AutorController {

    private final AutorService autorService;

    public AutorController(AutorService autorService) {
        this.autorService = autorService;
    }

    @PostMapping
    public Autor criarAutor(@RequestBody Autor autor) {
        return autorService.criarAutor(autor);
    }

    @GetMapping
    public List<Autor> listarAutoresRegistrados() {
        return autorService.listarAutoresRegistrados();
    }
}
