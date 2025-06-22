package net.noxe.retroarchivebe.controllers;

import lombok.RequiredArgsConstructor;
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

@RestController
@RequestMapping("api/v1/files")
@RequiredArgsConstructor
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

}
