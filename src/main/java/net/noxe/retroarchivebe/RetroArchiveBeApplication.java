package net.noxe.retroarchivebe;

import net.noxe.retroarchivebe.entities.AppUser;
import net.noxe.retroarchivebe.enums.AppUserRole;
import net.noxe.retroarchivebe.repositories.AppUserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;

@SpringBootApplication
public class RetroArchiveBeApplication {

    public static void main(String[] args) {
        SpringApplication.run(RetroArchiveBeApplication.class, args);
    }

    @Bean
    CommandLineRunner start(AppUserRepository appUserRepository) {
        return args -> {

            AppUser admin = AppUser.builder()
                    .email("hamza@email.com")
                    .appUserRole(AppUserRole.ADMIN)
                    .password("password")
                    .username("hamza")
                    .registrationDate(LocalDateTime.now())
                    .build();

            appUserRepository.saveAndFlush(admin);
        };
    }

}
