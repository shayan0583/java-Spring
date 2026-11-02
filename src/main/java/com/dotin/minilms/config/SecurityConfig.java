package com.dotin.minilms.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // صفحات عمومی و لاگین
                        .requestMatchers("/login.html", "/css/**", "/js/**", "/api/v1/auth/me").permitAll()

                        // رودمپ‌ها: مشاهده برای همه لاگین‌شده‌ها، ایجاد/ویرایش فقط ادمین
                        .requestMatchers(HttpMethod.GET, "/api/v1/roadmaps/**").authenticated()
                        .requestMatchers(HttpMethod.POST, "/api/v1/roadmaps/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/roadmaps/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/roadmaps/**").hasRole("ADMIN")

                        // ثبت‌نام: فقط دانشجو (یا هر کاربر authenticated با چک کردن نقش در سرویس)
                        .requestMatchers("/api/v1/enrollments/**").authenticated()

                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login.html")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/index.html", true)
                        .failureUrl("/login.html?error=true")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/login.html")
                        .permitAll()
                );

        return http.build();
    }
}
