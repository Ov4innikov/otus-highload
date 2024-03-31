package ru.ov4innikov.social.network.user.repository;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.ov4innikov.social.network.user.model.User;

import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@Log4j2
@Testcontainers
@SpringBootTest
class DbUserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Container
    @ServiceConnection
    public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:latest")
            .withReuse(true)
            .withDatabaseName("postgres");

    @DynamicPropertySource
    static void datasourceProperties(DynamicPropertyRegistry registry) {
        log.info("Url = {}", postgreSQLContainer::getJdbcUrl);
        log.info("Password = {}", postgreSQLContainer::getPassword);
        log.info("Username = {}", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
    }

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
        long id = userRepository.save(user);
        User userByIdFromDb = userRepository.getById(id);
        log.info("User from db = {}", userByIdFromDb);
        assertNotNull(userByIdFromDb);
    }
}