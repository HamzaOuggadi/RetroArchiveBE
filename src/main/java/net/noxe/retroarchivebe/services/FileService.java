package net.noxe.retroarchivebe.services;

import net.noxe.retroarchivebe.entities.ArchiveFile;
import net.noxe.retroarchivebe.enums.Category;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;

public interface FileService {

    void uploadFile(MultipartFile file, String userEmail, Category category) throws IOException;
    Resource getFile(String fileName) throws MalformedURLException;
    void deleteFile(String fileName);

    void persistFileData(MultipartFile file, String userEmail, Category category);
    ArchiveFile getFileData(String fileName);

}
