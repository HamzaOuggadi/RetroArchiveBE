package net.noxe.retroarchivebe.services.impl;

import lombok.RequiredArgsConstructor;
import net.noxe.retroarchivebe.dtos.ArticleDto;
import net.noxe.retroarchivebe.dtos.ArticleRequest;
import net.noxe.retroarchivebe.entities.AppUser;
import net.noxe.retroarchivebe.entities.Article;
import net.noxe.retroarchivebe.exceptions.AppUserLoggedException;
import net.noxe.retroarchivebe.exceptions.ArticleLoggedException;
import net.noxe.retroarchivebe.repositories.AppUserRepository;
import net.noxe.retroarchivebe.repositories.ArticleRepository;
import net.noxe.retroarchivebe.services.ArticleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {

    private static final Logger logger = LoggerFactory.getLogger(ArticleServiceImpl.class);

    private final ArticleRepository articleRepository;
    private final AppUserRepository appUserRepository;

    @Override
    public ArticleDto getArticleByTitle(String title) {
        Optional<Article> optionalArticle = articleRepository.findArticleByTitle(title);
        if (optionalArticle.isPresent()) {
            return optionalArticle.get().toDto();
        } else {
            throw new ArticleLoggedException("Article with title {} not found",
                    HttpStatus.NOT_FOUND,
                    logger,
                    Level.ERROR,
                    title);
        }
    }

    @Override
    public ArticleDto getArticleById(String id) {
        Optional<Article> optionalArticle = articleRepository.findById(Long.parseLong(id));
        if (optionalArticle.isPresent()) {
            return optionalArticle.get().toDto();
        } else {
            throw new ArticleLoggedException("Article with ID {} not found",
                    HttpStatus.NOT_FOUND,
                    logger,
                    Level.ERROR,
                    id);
        }
    }

    @Override
    public List<ArticleDto> getArticlesByUsername(String username) {
        List<Article> articles = articleRepository.findArticlesByAppUserUsername(username);
        if (!articles.isEmpty()) {
            return articles.stream().map(Article::toDto).toList();
        } else {
            throw new ArticleLoggedException("No articles found for user {}",
                    HttpStatus.NOT_FOUND,
                    logger,
                    Level.ERROR,
                    username);
        }
    }

    @Override
    public List<ArticleDto> getArticlesByUserEmail(String email) {
        List<Article> articles = articleRepository.findArticlesByAppUserEmail(email);
        if (!articles.isEmpty()) {
            return articles.stream().map(Article::toDto).toList();
        } else {
            throw new ArticleLoggedException("No articles found for user with email {}",
                    HttpStatus.NOT_FOUND,
                    logger,
                    Level.ERROR,
                    email);
        }
    }

    @Override
    public ArticleDto saveArticle(ArticleRequest articleRequest) {
        Optional<AppUser> optionalAppUser = appUserRepository.findAppUserByEmailIgnoreCase(articleRequest.appUserDto().email());
        Optional<Article> optionalArticle = articleRepository.findArticleByTitle(articleRequest.articleDto().title());
        if (optionalAppUser.isPresent() && optionalArticle.isEmpty()) {
            AppUser appUser = optionalAppUser.get();
            Article article = articleRequest.articleDto().toArticle();
            article.setAppUser(appUser);
            articleRepository.saveAndFlush(article);
            return article.toDto();
        } else if(optionalAppUser.isEmpty()) {
            throw new AppUserLoggedException("User with email {} not found",
                    HttpStatus.NOT_FOUND,
                    logger,
                    Level.ERROR,
                    articleRequest.appUserDto().email());
        } else {
            throw new ArticleLoggedException("Article with title {} already exists",
                    HttpStatus.CONFLICT,
                    logger,
                    Level.ERROR,
                    articleRequest.articleDto().title());
        }
    }

    @Override
    public ArticleDto updateArticle(ArticleRequest articleRequest) {
        Optional<Article> optionalArticle = articleRepository.findArticleByAppUserEmailAndTitle(articleRequest.appUserDto().email(),
                articleRequest.articleDto().title());
        Optional<AppUser> optionalAppUser = appUserRepository.findAppUserByEmailIgnoreCase(articleRequest.appUserDto().email());
        if (optionalArticle.isPresent() && optionalAppUser.isPresent()) {
            Article article = optionalArticle.get();
            AppUser appUser = optionalAppUser.get();
            article.setTitle(articleRequest.articleDto().title());
            article.setContent(articleRequest.articleDto().content());
            article.setImages(articleRequest.articleDto().images());
            article.setPublishedAt(articleRequest.articleDto().publishedAt());
            article.setUpdatedAt(LocalDateTime.now());
            article.setAppUser(appUser);
            articleRepository.saveAndFlush(article);
            return article.toDto();
        } else {
            if (optionalArticle.isEmpty()) {
                throw new ArticleLoggedException("Article with title {} not found",
                        HttpStatus.NOT_FOUND,
                        logger,
                        Level.ERROR,
                        articleRequest.articleDto().title());
            } else {
                throw new AppUserLoggedException("User with email {} not found",
                        HttpStatus.NOT_FOUND,
                        logger,
                        Level.ERROR,
                        articleRequest.appUserDto().email());
            }
        }
    }

    @Override
    public int deleteArticleByTitle(String title) {
        if (articleRepository.findArticleByTitle(title).isPresent()) {
            return articleRepository.deleteArticleByTitle(title);
        } else {
            throw new ArticleLoggedException("Article with title [{}] not found",
                    HttpStatus.NOT_FOUND,
                    logger,
                    Level.ERROR,
                    title);
        }
    }

    @Override
    public int deleteArticleById(String id) {
        if (articleRepository.findById(Long.parseLong(id)).isPresent()) {
            return articleRepository.deleteArticleById(Long.parseLong(id));
        } else {
            throw new ArticleLoggedException("Article with ID [{}] not found",
                    HttpStatus.NOT_FOUND,
                    logger,
                    Level.ERROR,
                    id);
        }
    }
}
