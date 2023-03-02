package ch.walkingfish.walkingfish.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ch.walkingfish.walkingfish.model.Article;
import ch.walkingfish.walkingfish.model.Picture;
import ch.walkingfish.walkingfish.service.PictureService;

@RestController
@RequestMapping("/api/picture")
public class RestPictureController {
    @Autowired
    PictureService pictureService;

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
}
