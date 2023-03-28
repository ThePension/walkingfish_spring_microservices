package ch.walkingfish.walkingfish.service;

import java.util.List;

import org.springframework.stereotype.Service;

import ch.walkingfish.walkingfish.model.Article;
import ch.walkingfish.walkingfish.model.Colori;
import ch.walkingfish.walkingfish.model.Picture;

public interface ProducerService {
    void send(String message);
    void send(Article article);
    void send(Picture picture);
    // void send(List<Picture> pictures);
    void send(List<Article> articles);
    void send(Colori colori);
    // void send(List<Colori> coloris);
}
