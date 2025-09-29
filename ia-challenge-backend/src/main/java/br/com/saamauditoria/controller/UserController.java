package br.com.saamauditoria.controller;

import br.com.saamauditoria.model.User;
import br.com.saamauditoria.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserRepository users;
    private final PasswordEncoder enc;

    public UserController(UserRepository users, PasswordEncoder enc) {
        this.users = users;
        this.enc = enc;
    }


    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody User user) {
        if (users.findByEmail(user.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Email already exists");
        }

        user.setPassword(enc.encode(user.getPassword()));
        if (user.getRoles() == null || user.getRoles().isBlank()) {
            user.setRoles("ROLE_USER");
        }

        users.save(user);
        return ResponseEntity.ok("User created successfully");
    }
}
