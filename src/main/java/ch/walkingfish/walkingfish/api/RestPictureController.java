package ch.walkingfish.walkingfish.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ch.walkingfish.walkingfish.model.Colori;
import ch.walkingfish.walkingfish.model.Picture;
import ch.walkingfish.walkingfish.service.CatalogService;
import ch.walkingfish.walkingfish.service.ColoriService;
import ch.walkingfish.walkingfish.service.PictureService;

@RestController
@RequestMapping("/api/picture")
public class RestPictureController {
    @Autowired
    PictureService pictureService;
    
    @GetMapping("/{article_id}")
    public Picture getColoriByArticleId(@PathVariable int article_id) {
        try {
            return pictureService.getPictureById((long) article_id);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }
}
