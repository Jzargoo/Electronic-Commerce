package database;

import Annotations.IT;
import com.jzargo.Application;
import com.jzargo.entity.User;
import com.jzargo.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.FactoryBasedNavigableListAssert.assertThat;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.jzargo.entity.User;
import com.jzargo.repository.UserRepository;
@Slf4j
@IT
@ContextConfiguration(classes = Application.class)
public class UserAndUserRepositoryIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testSaveUser() {
        User user = User.builder()
                .username("test_user")
                .password("securepassword")
                .phone("+77773662100")
                .ProfileImage("profile.jpg")
                .createdTime(LocalDate.now())
                .build();

        User savedUser;
        userRepository.save(user);
        savedUser = userRepository.findByPhone("+77773662100").orElseThrow();
        assertNotNull(savedUser.getId());
        assertThat(savedUser.getUsername()).isEqualTo("test_user");
        assertThat(savedUser.getPhone()).isEqualTo("+77773662100");
        assertThat(savedUser.getProfileImage()).isEqualTo("profile.jpg");
        assertThat(savedUser.getCreatedTime()).isNotNull();
    }

    @Test
    public void testFindById() {
        User user = User.builder()
                .username("another_user")
                .password("anotherpassword")
                .phone("+77771234567")
                .ProfileImage("avatar.jpg")
                .createdTime(LocalDate.now())
                .build();

        User savedUser = userRepository.save(user);
        Long userId = savedUser.getId();

        // Проверяем поиск по ID
        var foundUser = userRepository.findById(userId);
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getUsername()).isEqualTo("another_user");
    }

    @Test
    public void testFindAllUsers() {
        // Создаем нескольких пользователей
        User user1 = User.builder()
                .username("user1")
                .password("password1")
                .phone("+77771111111")
                .build();

        User user2 = User.builder()
                .username("user2")
                .password("password2")
                .phone("+77772222222")
                .build();

        userRepository.save(user1);
        userRepository.save(user2);

        // Проверяем, что в базе есть два пользователя
        var users = userRepository.findAll();
        assertThat(users).hasSize(2);
    }
}
