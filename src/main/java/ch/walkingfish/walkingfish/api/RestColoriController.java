package ch.walkingfish.walkingfish.api;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @PostMapping(value = {"/", ""}, consumes = { "multipart/form-data", "application/json" })
    public Colori createColori(@RequestBody Colori colori)
    {
        return coloriService.addColori(colori);
    }

    @PutMapping(value = {"/{id}", ""}, consumes = { "multipart/form-data", "application/json" })
    public Colori updateColori(@PathVariable int id, @RequestBody Colori colori)
    {
        System.out.println("id: " + id);
        // Check if the id is the same as the id in the object
        if (id != colori.getId()) {
            // TODO : Return custom error
            return null;
        }

        // Check if the colori exists
        if (coloriService.getColoriById(id) == null) {
            // TODO : Return custom error
            return null;
        }

        return coloriService.updateColori(colori);
    }

    @DeleteMapping("/{id}")
    public void deleteColori(@PathVariable int id)
    {
        coloriService.deleteColori(id);
    }
}