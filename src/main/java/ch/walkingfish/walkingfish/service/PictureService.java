package ch.walkingfish.walkingfish.service;

import java.util.List;

import ch.walkingfish.walkingfish.model.Picture;

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

	/**
	 * Return all the pictures in the database
	 * @return the list of pictures
	 */
    public List<Picture> getAllPictures();
}
