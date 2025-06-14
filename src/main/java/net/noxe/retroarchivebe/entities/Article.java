package net.noxe.retroarchivebe.entities;

import jakarta.persistence.*;
import lombok.*;

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
    private String content;
    private List<String> images;
    @Column(nullable = false)
    private LocalDateTime publishedAt;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private AppUser appUser;
}
