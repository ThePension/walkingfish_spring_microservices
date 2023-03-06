package ch.walkingfish.walkingfish.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.walkingfish.walkingfish.model.Article;
import ch.walkingfish.walkingfish.model.Picture;
import ch.walkingfish.walkingfish.repository.PictureRepository;

public interface PictureService {

    /**
	 * Delete the picture in the database
	 * 
	 * @param id the id of the picture to delete
	 * @throws Exception if the picture does not exist
	 */
	public void deletePictureInDB(Long id) throws Exception;

	/**
	 * Save the picture in the database
	 * 
	 * @param picture the picture to save
	 */
	public Picture savePicture(Picture picture);

	/**
	 * Return the picture with the given id
	 * 
	 * @param id the id of the picture
	 * @return the picture
	 * @throws Exception if the picture does not exist
	 */
	public Picture getPictureById(Long id) throws Exception;

	/**
	 * Delete all the pictures in the database
	 */
	public void deleteAllPictures();

    public List<Picture> getAllPictures();
}
