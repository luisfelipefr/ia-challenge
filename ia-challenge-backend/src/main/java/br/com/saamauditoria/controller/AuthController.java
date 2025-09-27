package br.com.saamauditoria.controller;

import br.com.saamauditoria.repository.UserRepository;
import br.com.saamauditoria.security.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

record LoginReq(String email, String password) {}
record LoginRes(String token) {}

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = {"http://localhost:5173"})
public class AuthController {

    private final UserRepository users;
    private final PasswordEncoder enc;
    private final JwtService jwt;

    public AuthController(UserRepository users, PasswordEncoder enc, JwtService jwt) {
        this.users = users; this.enc = enc; this.jwt = jwt;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginReq req) {
        var user = users.findByEmail(req.email()).orElse(null);
        if (user == null || !enc.matches(req.password(), user.getPassword())) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
        String token = jwt.generate(user.getUsername(), 1000L * 60 * 60 * 8); // 8h
        return ResponseEntity.ok(new LoginRes(token));
    }
}
