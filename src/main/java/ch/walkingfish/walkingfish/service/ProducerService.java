package ch.walkingfish.walkingfish.service;

import java.util.List;

import org.springframework.stereotype.Service;

import ch.walkingfish.walkingfish.model.Article;
import ch.walkingfish.walkingfish.model.Colori;
import ch.walkingfish.walkingfish.model.Picture;

public interface ProducerService {
    void send(Object message);
}
