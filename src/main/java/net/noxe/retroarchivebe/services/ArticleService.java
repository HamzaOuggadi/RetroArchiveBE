package net.noxe.retroarchivebe.services;

import net.noxe.retroarchivebe.dtos.ArticleDto;
import net.noxe.retroarchivebe.dtos.ArticleRequest;

import java.util.List;

public interface ArticleService {

    ArticleDto getArticleByTitle(String title);
    ArticleDto getArticleById(String id);
    List<ArticleDto> getArticlesByUsername(String username);
    List<ArticleDto> getArticlesByUserEmail(String email);

    ArticleDto saveArticle(ArticleRequest articleRequest);
    ArticleDto updateArticle(ArticleRequest articleRequest);

    int deleteArticleByTitle(String title);
    int deleteArticleById(String id);
}
