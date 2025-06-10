package net.noxe.retroarchivebe.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {

    void uploadFile(MultipartFile file) throws IOException;
    MultipartFile getFile(String fileName);
    void deleteFile(String fileName);

}
