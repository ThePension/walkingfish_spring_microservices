package ch.walkingfish.walkingfish.api;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ch.walkingfish.walkingfish.model.Colori;
import ch.walkingfish.walkingfish.service.ColoriService;

@RestController
@RequestMapping("/api/colori")
public class RestColoriController {
    @Autowired
    ColoriService coloriService;
    
    @GetMapping(value = { "/", "" })
    public List<Colori> getAllColoris() {
        return coloriService.getAllColori();
    }
}