package ch.walkingfish.walkingfish.api;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
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
import ch.walkingfish.walkingfish.model.log.LogType;
import ch.walkingfish.walkingfish.model.log.SimpleLog;
import ch.walkingfish.walkingfish.service.ColoriService;
import ch.walkingfish.walkingfish.service.ProducerService;

@RestController
@RequestMapping("/api/colori")
public class RestColoriController {
    @Autowired
    ColoriService coloriService;

    @Autowired
    private ProducerService producerService;

    /**
     * Get all coloris
     * @return a list of coloris
     */
    @GetMapping(value = { "/", "" })
    public List<Colori> getAllColoris() {
        SimpleLog log = new SimpleLog(LogType.INFO, "GET /api/colori", "Récupération de tous les coloris");

        producerService.send(log);

        return coloriService.getAllColori();
    }

    /**
     * Get all coloris in XML
     * @return a list of coloris
     */
    @GetMapping(value = { ".xml/", ".xml" })
    public List<Colori> getAllColorisXml() {
        SimpleLog log = new SimpleLog(LogType.INFO, "GET /api/colori.xml", "Récupération de tous les coloris");

        producerService.send(log);

        return coloriService.getAllColori();
    }

    /**
     * Get all articles based on a colori
     * @param colori_id the id of the colori
     * @return a list of articles
     */
    @GetMapping("/{colori_id}/articles")
    public List<Article> getAllArticlesBasedOnColori(@PathVariable int colori_id) {
        SimpleLog log = new SimpleLog(LogType.INFO, "GET /api/colori/" + colori_id + "/articles",
                "Récupération de tous les articles basés sur le colori " + colori_id);

        producerService.send(log);

        return coloriService.getColoriById(colori_id) //
                .getArticles()//
                .stream()//
                .collect(Collectors.toList());
    }

    /**
     * Get a colori by id
     * @param id the id of the colori
     * @return the colori
     */
    @GetMapping("/{id}")
    public Colori getColoriById(@PathVariable int id)
    {
        SimpleLog log = new SimpleLog(LogType.INFO, "GET /api/colori/" + id, "Récupération du colori " + id);

        producerService.send(log);

        return coloriService.getColoriById(id);
    }

    /**
     * Create a colori
     * @param colori the colori to create
     * @return the created colori
     */
    @PostMapping(value = {"/", ""}, consumes = { "multipart/form-data", "application/json" })
    public Colori createColori(@RequestBody Colori colori)
    {
        SimpleLog log = new SimpleLog(LogType.INFO, "POST /api/colori", "Création d'un colori");

        producerService.send(log);

        return coloriService.addColori(colori);
    }

    /**
     * Update a colori
     * @param id the id of the colori
     * @param colori the colori to update
     * @return the updated colori
     */
    @PutMapping(value = {"/{id}", ""}, consumes = { "multipart/form-data", "application/json" })
    public Colori updateColori(@PathVariable int id, @RequestBody Colori colori)
    {
        System.out.println("id: " + id);
        // Check if the id is the same as the id in the object
        if (id != colori.getId()) {
            SimpleLog log = new SimpleLog(LogType.ERROR, "PUT /api/colori/" + id, "L'id du colori ne correspond pas à l'id dans le body");

            producerService.send(log);

            return null;
        }

        // Check if the colori exists
        if (coloriService.getColoriById(id) == null) {
            SimpleLog log = new SimpleLog(LogType.ERROR, "PUT /api/colori/" + id, "Le colori n'existe pas");

            producerService.send(log);

            return null;
        }

        SimpleLog log = new SimpleLog(LogType.INFO, "PUT /api/colori/" + id, "Modification du colori " + id);

        producerService.send(log);

        return coloriService.updateColori(colori);
    }

    /**
     * Delete a colori
     * @param id the id of the colori
     */
    @DeleteMapping("/{id}")
    public void deleteColori(@PathVariable int id)
    {
        SimpleLog log = new SimpleLog(LogType.INFO, "DELETE /api/colori/" + id, "Suppression du colori " + id);

        producerService.send(log);

        coloriService.deleteColori(id);
    }
}