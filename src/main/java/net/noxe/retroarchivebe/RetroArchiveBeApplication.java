package net.noxe.retroarchivebe;

import com.github.javafaker.Faker;
import net.noxe.retroarchivebe.dtos.AppUserDto;
import net.noxe.retroarchivebe.dtos.ArticleDto;
import net.noxe.retroarchivebe.dtos.ArticleRequest;
import net.noxe.retroarchivebe.entities.AppUser;
import net.noxe.retroarchivebe.enums.AppUserRole;
import net.noxe.retroarchivebe.repositories.AppUserRepository;
import net.noxe.retroarchivebe.services.ArticleService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class RetroArchiveBeApplication {

    public static void main(String[] args) {
        SpringApplication.run(RetroArchiveBeApplication.class, args);
    }

    @Bean
    @Order(1)
    CommandLineRunner start(AppUserRepository appUserRepository,
                            PasswordEncoder passwordEncoder) {
        return args -> {

            if (appUserRepository.findAppUserByEmailIgnoreCase("hamza.ouggadi@gmail.com").isEmpty()) {
                AppUser admin = AppUser.builder()
                        .email("hamza.ouggadi@gmail.com")
                        .appUserRole(AppUserRole.ADMIN)
                        .password(passwordEncoder.encode("password"))
                        .username("Noxe")
                        .registrationDate(LocalDateTime.now())
                        .build();

                appUserRepository.saveAndFlush(admin);
            }

        };
    }

    @Bean
    @Order(2)
    @Profile("dev")
    CommandLineRunner startArticles(ArticleService articleService) {
        return args -> {
            Faker faker = new Faker();

            AppUserDto appUserDto = new AppUserDto(
                    "hamza.ouggadi@gmail.com",
                    null,
                    "Noxe",
                    AppUserRole.ADMIN,
                    LocalDateTime.now()
            );

            ArticleDto articleDto = new ArticleDto(
                    faker.book().title(),
                    faker.lorem().paragraph(10),
                    List.of("file/image1.jpg", "file/image2.jpg"),
                    LocalDateTime.now(),
                    LocalDateTime.now(),
                    "Noxe"
            );

            ArticleRequest articleRequest = new ArticleRequest(appUserDto, articleDto);

            articleService.saveArticle(articleRequest);
        };
    }

}
