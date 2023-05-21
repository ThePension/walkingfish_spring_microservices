package ch.walkingfish.walkingfish.api;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ch.walkingfish.walkingfish.model.Article;
import ch.walkingfish.walkingfish.model.Picture;
import ch.walkingfish.walkingfish.model.log.LogType;
import ch.walkingfish.walkingfish.model.log.SimpleLog;
import ch.walkingfish.walkingfish.service.FileStorageService;
import ch.walkingfish.walkingfish.service.PictureService;
import ch.walkingfish.walkingfish.service.ProducerService;

@RestController
@RequestMapping("/api/picture")
public class RestPictureController {
    @Autowired
    private PictureService pictureService;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private ProducerService producerService;

    /**
     * Get all pictures from the database
     * @return a list of pictures
     */
    @GetMapping(value = {"/", ""})
    public List<Picture> getAllPictures()
    {
        SimpleLog log = new SimpleLog(LogType.INFO, "GET /api/picture", "Récupération de toutes les images");

        producerService.send(log); 

        return pictureService.getAllPictures();
    }

    /**
     * Get a picture by its id
     * @param id the id of the picture
     * @return the picture
     */
    @GetMapping("/{id}")
    public Picture getPictureByArticleId(@PathVariable int id) {
        try {
            Picture picture = pictureService.getPictureById((long) id);

            SimpleLog log = new SimpleLog(LogType.INFO, "GET /api/picture/" + id, "Récupération de l'image " + id);

            producerService.send(log);

            return picture;
        } catch (Exception e) {
            e.printStackTrace();

            SimpleLog log = new SimpleLog(LogType.ERROR, "GET /api/picture/" + id, "Erreur lors de la récupération de l'image " + id);

            producerService.send(log);

            return null;
        }
    }

    /**
     * Get the article from a picture
     * @param picture_id the id of the picture
     * @return the article
     */
    @GetMapping("/{picture_id}/articles")
    public Article getArticleFromPicture(@PathVariable int picture_id)
    {
        try {
            Article article = pictureService.getPictureById((long) picture_id).getArticle();

            SimpleLog log = new SimpleLog(LogType.INFO, "GET /api/picture/" + picture_id + "/articles", "Récupération de l'article de l'image " + picture_id);

            producerService.send(log);

            return article;

        } catch (Exception e) {
            e.printStackTrace();

            SimpleLog log = new SimpleLog(LogType.ERROR, "GET /api/picture/" + picture_id + "/articles", "Erreur lors de la récupération de l'article de l'image " + picture_id);

            producerService.send(log);

            return null;
        }
    }

    /**
     * Delete a picture by its id
     * @param id the id of the picture
     */
    @DeleteMapping("/{id}")
    public void deletePictureById(@PathVariable int id)
    {
        Picture picture;
        // Get the picture from the database
        try {
            picture = pictureService.getPictureById((long) id);

            SimpleLog log = new SimpleLog(LogType.INFO, "DELETE /api/picture/" + id, "Suppression de l'image " + id);

            producerService.send(log);

        } catch (Exception e) {
            e.printStackTrace();

            SimpleLog log = new SimpleLog(LogType.ERROR, "DELETE /api/picture/" + id, "Erreur lors de la suppression de l'image " + id);

            producerService.send(log);

            return;
        }

        // Delete the picture from the database
        try {
            pictureService.deletePictureInDB((long) id);

            SimpleLog log = new SimpleLog(LogType.INFO, "DELETE /api/picture/" + id, "Suppression de l'image " + id + " dans la base de données");

            producerService.send(log);
        } catch (Exception e) {
            e.printStackTrace();

            SimpleLog log = new SimpleLog(LogType.ERROR, "DELETE /api/picture/" + id, "Erreur lors de la suppression de l'image " + id + " dans la base de données");

            producerService.send(log);

            return;
        }

        // Delete the picture from the server
        try {
            fileStorageService.delete(picture.getName());

            SimpleLog log = new SimpleLog(LogType.INFO, "DELETE /api/picture/" + id, "Suppression de l'image " + id + " sur le serveur");

            producerService.send(log);
        } catch (IOException e) {
            e.printStackTrace();

            SimpleLog log = new SimpleLog(LogType.ERROR, "DELETE /api/picture/" + id, "Erreur lors de la suppression de l'image " + id + " sur le serveur");

            producerService.send(log);

            return;
        }
    }
}
