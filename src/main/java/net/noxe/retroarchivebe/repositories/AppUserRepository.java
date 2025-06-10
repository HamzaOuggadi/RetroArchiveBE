package net.noxe.retroarchivebe.repositories;

import net.noxe.retroarchivebe.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {

    Optional<AppUser> findAppUserByEmailIgnoreCase(String email);
}
