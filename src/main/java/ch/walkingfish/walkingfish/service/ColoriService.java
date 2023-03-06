package ch.walkingfish.walkingfish.service;

import java.util.List;

import ch.walkingfish.walkingfish.model.Colori;


public interface ColoriService {

    /**
     * Return all the colori
     * 
     * @return the list of colori
     */
    public List<Colori> getAllColori();

    /**
     * Return the colori by the id
     * 
     * @param id the id of the colori
     * @return the colori
     */
    public Colori getColoriById(long id);

    /**
     * Add a new colori
     * 
     * @param colori the colori to add
     */
    public Colori addColori(Colori colori);

    /**
     * Update the colori
     * 
     * @param colori the colori to update
     */
    public Colori updateColori(Colori colori);

    /**
     * Delete the colori by the id
     * @param id the id of the colori
     */
    public void deleteColori(long id);

    /**
     * Return the colori by the name
     * 
     * @param name the name of the colori
     * @return the colori
     */
    public Colori getColoriByName(String name);

    /**
     * Return the colori by the hexa code
     * 
     * @param hexa the hexa code
     * @return the colori
     */
    public Colori getColoriByHexa(String hexa);

    /**
     * Delete all the colori
     */
    public void deleteAllColori();
}
