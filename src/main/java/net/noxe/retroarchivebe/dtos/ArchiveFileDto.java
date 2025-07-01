package net.noxe.retroarchivebe.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import net.noxe.retroarchivebe.entities.ArchiveFile;
import net.noxe.retroarchivebe.enums.Category;

import java.time.LocalDateTime;

public record ArchiveFileDto(
        String filename,
        String location,
        Category category,
        LocalDateTime uploadDate,
        @JsonProperty("author") String appUser,
        String articleTitle,
        long downloads) {

    public ArchiveFile toArchiveFile() {
        return ArchiveFile.builder()
                .filename(filename)
                .location(location)
                .category(category)
                .uploadDate(uploadDate)
                .downloads(downloads)
                .build();
    }
}
