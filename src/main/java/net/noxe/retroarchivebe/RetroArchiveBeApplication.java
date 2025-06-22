package net.noxe.retroarchivebe;

import net.noxe.retroarchivebe.entities.AppUser;
import net.noxe.retroarchivebe.enums.AppUserRole;
import net.noxe.retroarchivebe.repositories.AppUserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

@SpringBootApplication
public class RetroArchiveBeApplication {

    public static void main(String[] args) {
        SpringApplication.run(RetroArchiveBeApplication.class, args);
    }

    @Bean
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

}
