package net.noxe.retroarchivebe.services.impl;

import lombok.RequiredArgsConstructor;
import net.noxe.retroarchivebe.exceptions.AppUserLoggedException;
import net.noxe.retroarchivebe.repositories.AppUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);

    private final AppUserRepository appUserRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return appUserRepository.findAppUserByEmailIgnoreCase(email).orElseThrow( () ->
                new AppUserLoggedException("User [{}] Not Found", HttpStatus.NOT_FOUND, logger, Level.ERROR, email));
    }
}
