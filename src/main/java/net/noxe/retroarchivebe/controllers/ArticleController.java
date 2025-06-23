package net.noxe.retroarchivebe.controllers;

import lombok.RequiredArgsConstructor;
import net.noxe.retroarchivebe.dtos.ArticleDto;
import net.noxe.retroarchivebe.dtos.ArticleRequest;
import net.noxe.retroarchivebe.services.ArticleService;
import net.noxe.retroarchivebe.utils.GenericMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/articles")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    @GetMapping("/by-title/{title}")
    public ResponseEntity<ArticleDto> getArticleByTitle(@PathVariable String title) {
        return ResponseEntity.ok(articleService.getArticleByTitle(title));
    }

    @GetMapping("/by-id/{id}")
    public ResponseEntity<ArticleDto> getArticleById(@PathVariable String id) {
        return ResponseEntity.ok(articleService.getArticleById(id));
    }

    @GetMapping("/by-username/{username}")
    public ResponseEntity<List<ArticleDto>> getArticlesByUsername(@PathVariable String username) {
        return ResponseEntity.ok(articleService.getArticlesByUsername(username));
    }

    @GetMapping("/by-email/{userEmail}")
    public ResponseEntity<List<ArticleDto>> getArticlesByUserEmail(@PathVariable String userEmail) {
        return ResponseEntity.ok(articleService.getArticlesByUserEmail(userEmail));
    }

    @PostMapping("/save")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ArticleDto> saveArticle(@RequestBody ArticleRequest articleRequest) {
        return ResponseEntity.ok(articleService.saveArticle(articleRequest));
    }

    @PutMapping("/update")
    public ResponseEntity<ArticleDto> updateArticle(@RequestBody ArticleRequest articleRequest) {
        return ResponseEntity.ok(articleService.updateArticle(articleRequest));
    }

    @DeleteMapping("/delete-by-title/{title}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<GenericMessage> deleteArticleByTitle(@PathVariable String title) {
        int deletedArticles = articleService.deleteArticleByTitle(title);
        return ResponseEntity.ok(new GenericMessage("Deleted " + deletedArticles + " article(s) with title: " + title,
                HttpStatus.OK));
    }

    @DeleteMapping("/delete-by-id/{id}")
    public ResponseEntity<GenericMessage> deleteArticleById(@PathVariable String id) {
        int deletedArticles = articleService.deleteArticleById(id);
        return ResponseEntity.ok(new GenericMessage("Deleted " + deletedArticles + " article with ID: " + id,
                HttpStatus.OK));
    }
}
