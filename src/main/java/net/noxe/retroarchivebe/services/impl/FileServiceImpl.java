package net.noxe.retroarchivebe.services.impl;

import lombok.RequiredArgsConstructor;
import net.noxe.retroarchivebe.repositories.FileRepository;
import net.noxe.retroarchivebe.services.FileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final FileRepository fileRepository;

    @Value("file.storage.path")
    private String storagePath;

    @Override
    public void uploadFile(MultipartFile file) throws IOException {
        Path targetDir =  Paths.get(storagePath);
        if (Files.exists(targetDir)) {
            Files.createDirectories(targetDir);
        }

        Path targetFile = targetDir.resolve(Path.of(Objects.requireNonNull(file.getOriginalFilename()))).normalize();
        Files.copy(file.getInputStream(), targetFile, StandardCopyOption.REPLACE_EXISTING);
    }

    @Override
    public MultipartFile getFile(String fileName) {

        return null;
    }

    @Override
    public void deleteFile(String fileName) {

    }
}
