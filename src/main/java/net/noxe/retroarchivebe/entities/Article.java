package net.noxe.retroarchivebe.entities;

import jakarta.persistence.*;
import lombok.*;
import net.noxe.retroarchivebe.dtos.ArticleDto;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String title;
    @Column(columnDefinition = "TEXT")
    private String content;
    private List<String> images;
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
    @Column(nullable = true)
    private List<ArchiveFile> archiveFiles;
    @Column(nullable = false)
    private LocalDateTime publishedAt;
    private LocalDateTime updatedAt;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private AppUser appUser;

    public ArticleDto toDto() {
        return new ArticleDto(
                title,
                content,
                images,
                publishedAt,
                updatedAt,
                appUser.getUsername()
        );
    }
}
