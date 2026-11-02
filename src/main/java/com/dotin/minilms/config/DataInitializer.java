package com.dotin.minilms.config;

import com.dotin.minilms.entity.Role;
import com.dotin.minilms.entity.User;
import com.dotin.minilms.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initData(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {

            // ۱. کاربر ادمین اصلی
            createUserIfNotFound(userRepository, passwordEncoder, "admin", "admin123", Role.ADMIN);

            // ۲. ادمین دوم (برای تست مدیریت توسط شخص دیگر)
            createUserIfNotFound(userRepository, passwordEncoder, "manager", "manager123", Role.ADMIN);

            // ۳. دانشجوهای مختلف برای تست ثبت‌نام‌های متفاوت
            createUserIfNotFound(userRepository, passwordEncoder, "student", "student123", Role.STUDENT);
            createUserIfNotFound(userRepository, passwordEncoder, "ali", "ali123", Role.STUDENT);
            createUserIfNotFound(userRepository, passwordEncoder, "sara", "sara123", Role.STUDENT);
            createUserIfNotFound(userRepository, passwordEncoder, "reza", "reza123", Role.STUDENT);
        };
    }

    // یک متد کمکی کوچک برای اینکه کد بالا شلوغ نشود و تکرار کد کمتر باشد
    private void createUserIfNotFound(UserRepository repo, PasswordEncoder encoder, String username, String password, Role role) {
        if (repo.findByUsername(username).isEmpty()) {
            User user = new User();
            user.setUsername(username);
            user.setPassword(encoder.encode(password));
            user.setRole(role);
            repo.save(user);
            System.out.println("User created: " + username + " with role: " + role);
        }
    }
}
