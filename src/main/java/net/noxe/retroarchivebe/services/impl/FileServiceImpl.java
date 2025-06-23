package net.noxe.retroarchivebe.services.impl;

import lombok.RequiredArgsConstructor;
import net.noxe.retroarchivebe.entities.AppUser;
import net.noxe.retroarchivebe.entities.ArchiveFile;
import net.noxe.retroarchivebe.enums.Category;
import net.noxe.retroarchivebe.exceptions.AppUserLoggedException;
import net.noxe.retroarchivebe.exceptions.StorageLoggedException;
import net.noxe.retroarchivebe.repositories.AppUserRepository;
import net.noxe.retroarchivebe.repositories.FileRepository;
import net.noxe.retroarchivebe.services.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final FileRepository fileRepository;
    private final AppUserRepository appUserRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(FileServiceImpl.class);

    @Value("${file.storage.path}")
    private String storagePath;

    @Override
    public void uploadFile(MultipartFile file, String userEmail, Category category) throws IOException {
        if (file.isEmpty()) {
            throw new StorageLoggedException("File is empty", HttpStatus.BAD_REQUEST ,LOGGER, Level.ERROR);
        }

        Path targetDir = Paths.get(storagePath);
        if (!Files.exists(targetDir)) {
            Files.createDirectories(targetDir);
        }

        Path targetFile = targetDir.resolve(Path.of(Objects.requireNonNull(file.getOriginalFilename()))).normalize();

        Files.copy(file.getInputStream(), targetFile, StandardCopyOption.REPLACE_EXISTING);

        persistFileData(file, userEmail, category);
    }

    @Override
    public Resource getFile(String fileName) throws MalformedURLException {
        ArchiveFile fileData = getFileData(fileName);

        Path filePath = Paths.get(storagePath).resolve(fileData.getFilename()).normalize();

        if (!Files.exists(filePath) || !Files.isRegularFile(filePath)) {
            throw new StorageLoggedException("File with name {} not found.", HttpStatus.NOT_FOUND, LOGGER, Level.ERROR, fileName);
        }

        fileData.setDownloads(fileData.getDownloads() + 1);

        fileRepository.saveAndFlush(fileData);

        return new UrlResource(filePath.toUri());
    }

    @Override
    public void deleteFile(String fileName) {

    }

    @Override
    public void persistFileData(MultipartFile file, String userEmail, Category category) {
        if (file.isEmpty()) {
            throw new StorageLoggedException("File is empty", LOGGER, Level.ERROR);
        }

        Optional<AppUser> optionalAppUser = appUserRepository.findAppUserByEmailIgnoreCase(userEmail);

        if (optionalAppUser.isEmpty()) {
            throw new AppUserLoggedException("User {} not found.", HttpStatus.NOT_FOUND, LOGGER, Level.ERROR, userEmail);
        }

        ArchiveFile archiveFile = ArchiveFile.builder()
                .filename(file.getOriginalFilename())
                .location(storagePath + file.getOriginalFilename())
                .category(category)
                .uploadDate(LocalDateTime.now())
                .appUser(optionalAppUser.get())
                .build();

        fileRepository.saveAndFlush(archiveFile);
    }

    @Override
    public ArchiveFile getFileData(String fileName) {
        Optional<ArchiveFile> optionalArchiveFile = fileRepository.findArchiveFileByFilenameIgnoreCase(fileName);

        if (optionalArchiveFile.isPresent()) {
            return optionalArchiveFile.get();
        } else {
            throw new StorageLoggedException("File with name {} not found in the Database.", HttpStatus.NOT_FOUND, LOGGER, Level.ERROR, fileName);
        }
    }

}
