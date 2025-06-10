package net.noxe.retroarchivebe.controllers;

import lombok.RequiredArgsConstructor;
import net.noxe.retroarchivebe.services.FileService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/files")
@RequiredArgsConstructor
public class FileStorageController {

    private final FileService fileService;


}
