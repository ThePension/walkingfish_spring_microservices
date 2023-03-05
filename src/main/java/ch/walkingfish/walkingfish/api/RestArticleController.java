package ch.walkingfish.walkingfish.api;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ch.walkingfish.walkingfish.model.Article;
import ch.walkingfish.walkingfish.model.Colori;
import ch.walkingfish.walkingfish.model.Picture;
import ch.walkingfish.walkingfish.service.CatalogService;

@RestController
@RequestMapping("/api/article")
public class RestArticleController {
    @Autowired
    CatalogService catalogService;

    @GetMapping(value = { "/", "" })
    public List<Article> showCatalogue(@RequestParam("search") Optional<String> opt_search) {
        List<Article> articles = null;

        if (opt_search.isPresent()) {
            String search = opt_search.get();
            articles = catalogService.getAllArticlesFromCatalog()//
                    .stream()//
                    .filter(article -> article.getDescription().contains(search) || article.getName().contains(search))//
                    .collect(Collectors.toList());
        } else {
            articles = catalogService.getAllArticlesFromCatalog();
        }

        return articles;
    }

    @GetMapping("/{id}")
    public Article getArticleById(@PathVariable int id)
    {
        try {
            return catalogService.getArticleById((long) id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("/{id}/pictures")
    public List<Picture> getPicturesFromArticle(@PathVariable int id)
    {
        try {
            return catalogService.getArticleById((long)id).getPictures();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("/{id}/coloris")
    public List<Colori> getColorisFromArticle(@PathVariable int id)
    {
        try {
            return catalogService.getArticleById((long)id).getColoris();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @PostMapping(value = { "/", "" })
    public Article createArticle(@RequestBody Article article)
    {
        try {
            return catalogService.addArticleToCatalog(article);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @PutMapping(value = { "/{id}", "/{id}/" })
    public Article updateArticle(@PathVariable int id, @RequestBody Article article)
    {
        // Check if the article exists
        try {
            catalogService.getArticleById((long) id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        // Update the article
        try {
            return catalogService.updateArticleInDB(article);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @DeleteMapping(value = { "/{id}", "/{id}/" })
    public void deleteArticle(@PathVariable int id)
    {
        try {
            catalogService.deleteArticleInDB((long)id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
