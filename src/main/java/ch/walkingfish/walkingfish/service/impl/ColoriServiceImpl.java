package ch.walkingfish.walkingfish.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.walkingfish.walkingfish.repository.ColoriRepository;
import ch.walkingfish.walkingfish.service.ColoriService;
import ch.walkingfish.walkingfish.model.Colori;

@Service
public class ColoriServiceImpl implements ColoriService {
    @Autowired
    private ColoriRepository coloriRepository;

    /**
     * Return all the colori
     * 
     * @return the list of colori
     */
    public List<Colori> getAllColori() {
        List<Colori> result = new ArrayList<Colori>();
        coloriRepository.findAll().forEach(result::add);

        return result;
    }

    /**
     * Return the colori by the id
     * 
     * @param id the id of the colori
     * @return the colori
     */
    public Colori getColoriById(long id) {
        return coloriRepository.findById(id);
    }

    /**
     * Add a new colori
     * 
     * @param colori the colori to add
     */
    public Colori addColori(Colori colori) {
        return coloriRepository.saveAndFlush(colori);
    }

    /**
     * Update the colori
     * 
     * @param colori the colori to update
     */
    public Colori updateColori(Colori colori) {
        return coloriRepository.save(colori);
    }

    /**
     * Delete the colori by the id
     * @param id the id of the colori
     */
    public void deleteColori(long id) {
        coloriRepository.deleteById(id);
    }

    /**
     * Return the colori by the name
     * 
     * @param name the name of the colori
     * @return the colori
     */
    public Colori getColoriByName(String name) {
        return coloriRepository.findByName(name);
    }

    /**
     * Return the colori by the hexa code
     * 
     * @param hexa the hexa code
     * @return the colori
     */
    public Colori getColoriByHexa(String hexa) {
        return coloriRepository.findByHexa(hexa);
    }

    /**
     * Delete all the colori
     */
    public void deleteAllColori() {
        coloriRepository.deleteAll();
    }
}
