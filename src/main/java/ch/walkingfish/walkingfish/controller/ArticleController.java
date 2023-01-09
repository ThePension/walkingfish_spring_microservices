package ch.walkingfish.walkingfish.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ch.walkingfish.walkingfish.model.Article;
import ch.walkingfish.walkingfish.service.ArticleService;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/catalogue")
public class ArticleController {
    @Autowired
    ArticleService articleService;

    @GetMapping(value = {"/", ""})
	public String showCatalogue(Model model) {
        List<Article> articles = articleService.getAllArticlesFromCatalog();

        model.addAttribute("articles", articles);

	    return "catalogue";
	}

    @GetMapping(value = {"/create"})
    public String showNewArticle(Model model) {
        model.addAttribute("article", new Article());
          
        return "new-article";
    }

    @PostMapping(value = "/save")
    public String saveBeer(@ModelAttribute Article article, BindingResult errors, Model model) {
        articleService.addBeerToCatalog(article);

        return "redirect:/catalogue/";
    }
}