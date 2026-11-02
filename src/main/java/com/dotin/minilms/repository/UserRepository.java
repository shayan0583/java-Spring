package com.dotin.minilms.repository;

import com.dotin.minilms.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // متدی برای پیدا کردن کاربر بر اساس نام کاربری
    // Spring Data JPA به طور خودکار این متد را پیاده‌سازی می‌کند!
    Optional<User> findByUsername(String username);

}
