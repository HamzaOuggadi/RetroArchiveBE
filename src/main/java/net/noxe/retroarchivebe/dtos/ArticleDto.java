package net.noxe.retroarchivebe.dtos;

import net.noxe.retroarchivebe.entities.Article;

import java.time.LocalDateTime;
import java.util.List;

public record ArticleDto(
        String title,
        String content,
        List<String> images,
        LocalDateTime publishedAt,
        String appUserUsername) {

    public Article toArticle() {
        return new Article(
                null,
                title,
                content,
                images,
                publishedAt,
                null
        );
    }
}
