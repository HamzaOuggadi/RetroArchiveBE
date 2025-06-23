package net.noxe.retroarchivebe.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import net.noxe.retroarchivebe.entities.Article;

import java.time.LocalDateTime;
import java.util.List;

public record ArticleDto(
        String title,
        String content,
        List<String> images,
        LocalDateTime publishedAt,
        LocalDateTime updatedAt,
        @JsonProperty("author") String appUserUsername) {

    public Article toArticle() {
        return Article.builder()
                .title(title)
                .content(content)
                .images(images)
                .publishedAt(publishedAt)
                .updatedAt(updatedAt)
                .build();
    }
}
