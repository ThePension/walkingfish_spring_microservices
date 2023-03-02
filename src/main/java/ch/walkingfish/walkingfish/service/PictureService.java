package ch.walkingfish.walkingfish.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.walkingfish.walkingfish.model.Article;
import ch.walkingfish.walkingfish.model.Picture;
import ch.walkingfish.walkingfish.repository.PictureRepository;

@Service
public class PictureService {
    @Autowired
    private PictureRepository pictureRepository;
    /**
	 * Delete the picture in the database
	 * 
	 * @param id the id of the picture to delete
	 * @throws Exception if the picture does not exist
	 */
	public void deletePictureInDB(Long id) throws Exception {
		Optional<Picture> optPicture = pictureRepository.findById(id);

		if (optPicture.isPresent()) {
			Picture picture = optPicture.get();

			// Remove the picture from the article
			Article article = picture.getArticle();
			article.getPictures().remove(picture);

			// Delete the picture
			pictureRepository.delete(picture);
		} else {
			throw new Exception("This picture does not exist");
		}
	}

	/**
	 * Save the picture in the database
	 * 
	 * @param picture the picture to save
	 */
	public Picture savePicture(Picture picture) {
		return pictureRepository.save(picture);
	}

	/**
	 * Return the picture with the given id
	 * 
	 * @param id the id of the picture
	 * @return the picture
	 * @throws Exception if the picture does not exist
	 */
	public Picture getPictureById(Long id) throws Exception {
		Optional<Picture> optPicture = pictureRepository.findById(id);

		if (optPicture.isPresent()) {
			return optPicture.get();
		} else {
			throw new Exception("This picture does not exist");
		}
	}

	/**
	 * Delete all the pictures in the database
	 */
	public void deleteAllPictures() {
		pictureRepository.findAll().forEach(p -> {
			try {
				deletePictureInDB(p.getId());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}

    public List<Picture> getAllPictures()
    {
        List<Picture> result = new ArrayList<Picture>();
		pictureRepository.findAll().forEach(result::add);

        return result;
    }
}
