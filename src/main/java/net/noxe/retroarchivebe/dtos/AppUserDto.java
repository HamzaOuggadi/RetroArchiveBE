package net.noxe.retroarchivebe.dtos;

import net.noxe.retroarchivebe.entities.AppUser;
import net.noxe.retroarchivebe.enums.AppUserRole;

import java.time.LocalDateTime;

public record AppUserDto(
        String email,
        String password,
        String username,
        AppUserRole appUserRole,
        LocalDateTime registrationDate) {

    public AppUser toAppUser() {
        return AppUser.builder()
                .email(email)
                .password(password)
                .username(username)
                .appUserRole(appUserRole)
                .registrationDate(registrationDate)
                .build();
    }

}
