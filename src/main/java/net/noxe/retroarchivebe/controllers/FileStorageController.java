package net.noxe.retroarchivebe.controllers;

import lombok.RequiredArgsConstructor;
import net.noxe.retroarchivebe.dtos.ArchiveFileDto;
import net.noxe.retroarchivebe.entities.ArchiveFile;
import net.noxe.retroarchivebe.enums.Category;
import net.noxe.retroarchivebe.services.FileService;
import net.noxe.retroarchivebe.utils.GenericMessage;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

@RestController
@RequestMapping("api/v1/files")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class FileStorageController {

    private final FileService fileService;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<GenericMessage> uploadFile(@RequestPart("file") MultipartFile file,
                                                     @RequestParam("userEmail") String userEmail,
                                                     @RequestParam("category") Category category) throws IOException {
        fileService.uploadFile(file, userEmail, category);
        return ResponseEntity.ok(new GenericMessage("File uploaded successfully", HttpStatus.OK));
    }

    @GetMapping("/download/{fileName:.+}")
    public ResponseEntity<Resource> getFile(@PathVariable String fileName) throws MalformedURLException {
        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .body(fileService.getFile(fileName));
    }

    @GetMapping("/data")
    public ResponseEntity<List<ArchiveFileDto>> getAllFilesData() {
        return ResponseEntity.ok(fileService.getAllFilesData());
    }

    @GetMapping("/data/{fileName:.+}")
    public ResponseEntity<ArchiveFileDto> getFileData(@PathVariable String fileName) {
        ArchiveFile archiveFile = fileService.getFileData(fileName);
        return ResponseEntity.ok(archiveFile.toDto());
    }

}
