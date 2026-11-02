package com.dotin.minilms.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

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

                        // تاپیک‌های هر رودمپ: مشاهده برای ادمین و دانشجو، ایجاد فقط برای ادمین
                        .requestMatchers(HttpMethod.GET, "/api/v1/roadmaps/{roadmapId}/topics")
                        .hasAnyRole("ADMIN", "STUDENT")
                        .requestMatchers(HttpMethod.POST, "/api/v1/roadmaps/{roadmapId}/topics")
                        .hasRole("ADMIN")

                        // ویرایش و حذف تاپیک: فقط ادمین
                        .requestMatchers(HttpMethod.PUT, "/api/v1/topics/{topicId}")
                        .hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/topics/{topicId}")
                        .hasRole("ADMIN")

                        // ثبت‌نام در دوره: اجازه برای دانشجو و ادمین (باید قبل از POST عمومی رودمپ‌ها قرار بگیرد)
                        .requestMatchers(HttpMethod.POST, "/api/v1/roadmaps/*/enroll")
                        .hasAnyRole("STUDENT", "ADMIN")

                        // رودمپ‌ها: مشاهده برای کاربران واردشده، تغییرات فقط برای ادمین
                        .requestMatchers(HttpMethod.GET, "/api/v1/roadmaps/**").authenticated()
                        .requestMatchers(HttpMethod.POST, "/api/v1/roadmaps/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/roadmaps/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/roadmaps/**").hasRole("ADMIN")

                        // ثبت‌نام‌ها: سایر مسیرهای enrollment
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
