package net.noxe.retroarchivebe.services;

import net.noxe.retroarchivebe.dtos.AppUserDto;
import net.noxe.retroarchivebe.dtos.ArticleDto;
import net.noxe.retroarchivebe.entities.Article;

import java.util.List;

public interface ArticleService {

    Article getArticleByTitle(String title);
    Article getArticleById(String id);
    List<Article> getArticlesByUsername(String username);
    List<Article> getAllArticlesByUserEmail(String email);

    Article saveArticle(ArticleDto articleDto, AppUserDto appUserDto);

    void deleteArticleByTitle(String title);
    void deleteArticleById(String id);
}
