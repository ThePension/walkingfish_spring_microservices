package ch.walkingfish.walkingfish.service;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import ch.walkingfish.walkingfish.model.*;

public interface CatalogService {


	/**
	 * Return all the articles in the catalog
	 * 
	 * @return the list of articles
	 */
	public List<Article> getAllArticlesFromCatalog();

	/**
	 * 
	 * @param pageable the page to display
	 * @return the article
	 */
	public Page<Article> findPaginated(Pageable pageable);

	/**
	 * 
	 * @param pageable
	 * @param search
	 * @return
	 */
	public Page<Article> findPaginatedAndFiltered(Pageable pageable, String search);

	/**
	 * Add a new article to the catalog
	 * 
	 * @param article the article to add
	 * @return the article added
	 */
	public Article addArticleToCatalog(Article article);

	/**
	 * Return the article with the given id
	 * 
	 * @param id the id of the article
	 * @return the article
	 * @throws Exception if the article does not exist
	 */
	public Article getArticleById(Long id) throws Exception;

	/**
	 * Update the article in the database
	 * 
	 * @param article the article to update
	 * @return the article updated
	 */
	public Article updateArticleInDB(Article article);

	/**
	 * Delete the article in the database
	 * 
	 * @param article the article to delete
	 */
	public void deleteArticleInDB(Article article);

	/**
	 * Delete the article in the database
	 * 
	 * @param id the id of the article to delete
	 */
	@Transactional
	public void deleteArticleInDB(Long id);

	/**
	 * Delete all the articles in the database
	 */
	public void deleteAllArticles();
}
