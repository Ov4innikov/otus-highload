package ru.ov4innikov.social.network.user.repository;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.ov4innikov.social.network.common.config.TestContainersConfig;
import ru.ov4innikov.social.network.user.model.User;

import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.*;

@Log4j2
@DirtiesContext
@SpringBootTest
class DbUserRepositoryTest extends TestContainersConfig {

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository.clean();
    }

    @AfterEach
    void tearDown() {
        userRepository.clean();
    }

    @Test
    public void test_saveAndGet_positive() {
        User user = User.builder()
                .firstName("Василий")
                .middleName("Васильевич")
                .secondName("Тестиров")
                .birthdate(OffsetDateTime.parse("2012-03-03T23:00:01Z"))
                .biography("Тест")
                .city("Тестинг")
                .password("Пароль")
                .build();
        String id = userRepository.save(user);
        User userByIdFromDb = userRepository.getById(id);
        log.info("User from db = {}", userByIdFromDb);
        assertNotNull(userByIdFromDb);
    }
}