package net.noxe.retroarchivebe.controllers;

import net.noxe.retroarchivebe.dtos.AuthRequest;
import net.noxe.retroarchivebe.dtos.AuthResponse;
import net.noxe.retroarchivebe.entities.AppUser;
import net.noxe.retroarchivebe.security.JwtUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    public AuthController(AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );

        AppUser user = (AppUser) auth.getPrincipal();
        String token = jwtUtils.generateToken(user.getEmail(), user.getAppUserRole());

        return ResponseEntity.ok(new AuthResponse(token));
    }
}
