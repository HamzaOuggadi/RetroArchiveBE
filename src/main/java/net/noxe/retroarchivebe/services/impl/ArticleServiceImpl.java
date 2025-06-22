package net.noxe.retroarchivebe.services.impl;

import lombok.RequiredArgsConstructor;
import net.noxe.retroarchivebe.dtos.AppUserDto;
import net.noxe.retroarchivebe.dtos.ArticleDto;
import net.noxe.retroarchivebe.entities.Article;
import net.noxe.retroarchivebe.services.ArticleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {

    @Override
    public Article getArticleByTitle(String title) {
        return null;
    }

    @Override
    public Article getArticleById(String id) {
        return null;
    }

    @Override
    public List<Article> getArticlesByUsername(String username) {
        return List.of();
    }

    @Override
    public List<Article> getAllArticlesByUserEmail(String email) {
        return List.of();
    }

    @Override
    public Article saveArticle(ArticleDto articleDto, AppUserDto appUserDto) {
        return null;
    }

    @Override
    public void deleteArticleByTitle(String title) {

    }

    @Override
    public void deleteArticleById(String id) {

    }
}
