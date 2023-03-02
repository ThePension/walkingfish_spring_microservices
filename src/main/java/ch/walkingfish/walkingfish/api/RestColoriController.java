package ch.walkingfish.walkingfish.api;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ch.walkingfish.walkingfish.model.Article;
import ch.walkingfish.walkingfish.model.Colori;
import ch.walkingfish.walkingfish.service.ColoriService;

@RestController
@RequestMapping("/api/colori")
public class RestColoriController {
    @Autowired
    ColoriService coloriService;

    @GetMapping(value = { "/", "" })
    public List<Colori> getAllColoris() {
        return coloriService.getAllColori();
    }

    @GetMapping("/{colori_id}/articles")
    public List<Article> getAllArticlesBasedOnColori(@PathVariable int colori_id) {
        return coloriService.getColoriById(colori_id) //
                .getArticles()//
                .stream()//
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public Colori getColoriById(@PathVariable int id)
    {
        return coloriService.getColoriById(id);
    }
}