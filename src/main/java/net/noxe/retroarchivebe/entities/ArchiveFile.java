package net.noxe.retroarchivebe.entities;

import jakarta.persistence.*;
import lombok.*;
import net.noxe.retroarchivebe.enums.Category;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ArchiveFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String filename;
    @Column(nullable = false)
    private String location;
    private Category category;
    @Column(nullable = false)
    private LocalDateTime uploadDate;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private AppUser appUser;
    private long downloads;
}
