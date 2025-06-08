package net.noxe.retroarchivebe.entities;

import jakarta.persistence.*;
import lombok.*;
import net.noxe.retroarchivebe.enums.AppUserRole;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String password;
    private String username;
    private AppUserRole appUserRole;
    private LocalDateTime registrationDate;
    @OneToMany(mappedBy = "appUser", cascade = CascadeType.ALL)
    private List<ArchiveFile> archiveFiles;
    @OneToMany(mappedBy = "appUser", cascade = CascadeType.ALL)
    private List<Article> articles;
}
