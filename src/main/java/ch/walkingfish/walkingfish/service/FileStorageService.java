package ch.walkingfish.walkingfish.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {

    /**
     * Save the file to the server
     * @param file the file to save
     * @param fileName the name of the file
     * @throws IOException if the file can't be saved
     */
    public void save(MultipartFile file, String fileName) throws IOException;

    /**
     * Delete the file from the server
     * @param filename the name of the file to delete
     * @return true if the file was deleted, false if it didn't exist
     * @throws IOException if the file can't be deletedF
     */
    public boolean delete(String filename) throws IOException;
}
