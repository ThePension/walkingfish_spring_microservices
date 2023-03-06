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
import ch.walkingfish.walkingfish.service.FileStorageService;
import ch.walkingfish.walkingfish.service.PictureService;

@RestController
@RequestMapping("/api/picture")
public class RestPictureController {
    @Autowired
    private PictureService pictureService;

    @Autowired
    private FileStorageService fileStorageService;

    @GetMapping(value = {"/", ""})
    public List<Picture> getAllPictures()
    {
        return pictureService.getAllPictures();
    }

    @GetMapping("/{id}")
    public Picture getPictureByArticleId(@PathVariable int id) {
        try {
            return pictureService.getPictureById((long) id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("/{picture_id}/articles")
    public Article getArticleFromPicture(@PathVariable int picture_id)
    {
        try {
            return pictureService.getPictureById((long) picture_id).getArticle();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @DeleteMapping("/{id}")
    public void deletePictureById(@PathVariable int id)
    {
        Picture picture;
        // Get the picture from the database
        try {
            picture = pictureService.getPictureById((long) id);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        // Delete the picture from the database
        try {
            pictureService.deletePictureInDB((long) id);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        // Delete the picture from the server
        try {
            fileStorageService.delete(picture.getName());
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
    }
}
