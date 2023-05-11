package ch.walkingfish.walkingfish.api;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import ch.walkingfish.walkingfish.model.Article;
import ch.walkingfish.walkingfish.model.Colori;
import ch.walkingfish.walkingfish.model.Picture;
import ch.walkingfish.walkingfish.model.log.LogType;
import ch.walkingfish.walkingfish.model.log.SimpleLog;
import ch.walkingfish.walkingfish.service.CatalogService;
import ch.walkingfish.walkingfish.service.FileStorageService;
import ch.walkingfish.walkingfish.service.PictureService;
import ch.walkingfish.walkingfish.service.ProducerService;

@RestController
@RequestMapping("/api/article")
public class RestArticleController {
    @Autowired
    private ProducerService producerService;
    
    @Autowired
    private CatalogService catalogService;

    @Autowired
    private PictureService pictureService;

    @Autowired
    private FileStorageService fileStorageService;

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

        SimpleLog log = new SimpleLog(LogType.INFO, "GET /api/article", "Récupération de tous les articles du catalogue");

        producerService.send(log);

        return articles;
    }

    @GetMapping("/{id}")
    public Article getArticleById(@PathVariable int id)
    {
        Article article = null;
        try {
            article = catalogService.getArticleById((long) id);

            SimpleLog log = new SimpleLog(LogType.INFO, "GET /api/article/" + id, "Récupération de l'article " + article.getName());

            producerService.send(log);
            
        } catch (Exception e) {
            e.printStackTrace();

            SimpleLog log = new SimpleLog(LogType.ERROR, "GET /api/article/" + id, e.getMessage());

            producerService.send(log);

            return null;
        }
        
        producerService.send(article);
        return article;
    }

    @GetMapping("/{id}/pictures")
    public List<Picture> getPicturesFromArticle(@PathVariable int id)
    {
        try {
            List<Picture> pictures = catalogService.getArticleById((long)id).getPictures();

            SimpleLog log = new SimpleLog(LogType.INFO, "GET /api/article/" + id + "/pictures", "Récupération des images de l'article " + id);

            producerService.send(log);

            return pictures;
        } catch (Exception e) {
            e.printStackTrace();

            SimpleLog log = new SimpleLog(LogType.ERROR, "GET /api/article/" + id + "/pictures", e.getMessage());

            producerService.send(log);

            return null;
        }
    }

    @GetMapping("/{id}/coloris")
    public List<Colori> getColorisFromArticle(@PathVariable int id)
    {
        try {
            List<Colori> coloris = catalogService.getArticleById((long)id).getColoris();

            SimpleLog log = new SimpleLog(LogType.INFO, "GET /api/article/" + id + "/coloris", "Récupération des coloris de l'article " + id);

            producerService.send(log);

            return coloris;
        } catch (Exception e) {
            e.printStackTrace();

            SimpleLog log = new SimpleLog(LogType.ERROR, "GET /api/article/" + id + "/coloris", e.getMessage());

            producerService.send(log);

            return null;
        }
    }

    @PostMapping(value = { "/", "" })
    public Article createArticle(@RequestBody Article article)
    {
        try {
            Article added_article = catalogService.addArticleToCatalog(article);

            SimpleLog log = new SimpleLog(LogType.INFO, "POST /api/article", "Création de l'article " + article.getName());

            producerService.send(log);

            return added_article;
        } catch (Exception e) {
            e.printStackTrace();

            SimpleLog log = new SimpleLog(LogType.ERROR, "POST /api/article", e.getMessage());

            producerService.send(log);

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

            SimpleLog log = new SimpleLog(LogType.ERROR, "PUT /api/article/" + id, e.getMessage());

            producerService.send(log);

            return null;
        }

        // Update the article
        try {
            Article updated_article =  catalogService.updateArticleInDB(article);

            SimpleLog log = new SimpleLog(LogType.INFO, "PUT /api/article/" + id, "Mise à jour de l'article " + article.getName());

            producerService.send(log);

            return updated_article;

        } catch (Exception e) {
            e.printStackTrace();

            SimpleLog log = new SimpleLog(LogType.ERROR, "PUT /api/article/" + id, e.getMessage());

            producerService.send(log);

            return null;
        }
    }

    @DeleteMapping(value = { "/{id}", "/{id}/" })
    public void deleteArticle(@PathVariable int id)
    {
        try {
            catalogService.deleteArticleInDB((long)id);

            SimpleLog log = new SimpleLog(LogType.INFO, "DELETE /api/article/" + id, "Suppression de l'article " + id);

            producerService.send(log);

        } catch (Exception e) {
            e.printStackTrace();

            SimpleLog log = new SimpleLog(LogType.ERROR, "DELETE /api/article/" + id, e.getMessage());

            producerService.send(log);
        }
    }

    @PostMapping(value = {"/{id}/pictures", "/{id}/pictures/"})
    public Picture addPicture(@PathVariable int id, @RequestBody MultipartFile image)
    {
        // Get the article from the database
        Article article;

        try {
            article = catalogService.getArticleById((long) id);

            SimpleLog log = new SimpleLog(LogType.INFO, "POST /api/article/" + id + "/pictures", "Ajout d'une image à l'article " + id);

            producerService.send(log);
        } catch (Exception e) {
            e.printStackTrace();

            SimpleLog log = new SimpleLog(LogType.ERROR, "POST /api/article/" + id + "/pictures", e.getMessage());

            producerService.send(log);

            return null;
        }

        try {
            String imageName = System.currentTimeMillis() + "_" + image.getOriginalFilename();

            fileStorageService.save(image, imageName);

            // Save the picture to the database
            Picture picture = new Picture("/articlesImages/" + imageName, imageName, article);

            SimpleLog log = new SimpleLog(LogType.INFO, "POST /api/article/" + id + "/pictures", "Ajout d'une image à l'article " + id);

            producerService.send(log);

            return pictureService.savePicture(picture);
        } catch (IOException e) {
            e.printStackTrace();

            SimpleLog log = new SimpleLog(LogType.ERROR, "POST /api/article/" + id + "/pictures", e.getMessage());

            producerService.send(log);
            
            return null;
        }
    }
}
