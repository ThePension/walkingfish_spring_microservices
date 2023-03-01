package ch.walkingfish.walkingfish.api;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ch.walkingfish.walkingfish.model.Article;
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
}
