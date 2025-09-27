package br.com.saamauditoria;

import br.com.saamauditoria.model.User;
import br.com.saamauditoria.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SeedConfig {
    @Bean
    CommandLineRunner seedUsers(UserRepository users, PasswordEncoder enc) {
        return args -> {
            if (users.findByUsername("admin").isEmpty()) {
                User u = new User();
                u.setUsername("admin");
                u.setEmail("admin@local.com");
                u.setPassword(enc.encode("admin")); // bcrypt
                u.setRoles("ROLE_USER,ROLE_ADMIN");
                users.save(u);
            }
        };
    }
}
