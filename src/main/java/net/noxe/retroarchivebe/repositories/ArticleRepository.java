package net.noxe.retroarchivebe.repositories;

import net.noxe.retroarchivebe.entities.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

    Optional<Article> findArticleByTitle(String title);
    List<Article> findArticlesByAppUserUsername(String username);
    List<Article> findArticlesByAppUserEmail(String email);

    Optional<Article> findArticleByAppUserEmailAndTitle(String email, String title);

    @Modifying
    int deleteArticleByTitle(String title);

    @Modifying
    int deleteArticleById(Long id);
}
