package ru.ov4innikov.social.network.post.repository;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import ru.ov4innikov.social.network.common.config.TestContainersConfig;
import ru.ov4innikov.social.network.model.UserRegisterPostRequest;
import ru.ov4innikov.social.network.post.model.Post;
import ru.ov4innikov.social.network.user.repository.UserRepository;
import ru.ov4innikov.social.network.user.service.UserService;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@Log4j2
@DirtiesContext
@SpringBootTest
class DbPostRepositoryTest extends TestContainersConfig {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private PostRepository postRepository;

    @BeforeEach
    void setUp() {
        postRepository.clean();
    }

    @AfterEach
    void tearDown() {
        postRepository.clean();
    }

    @Test
    public void test_saveAndGet_positive() throws JsonProcessingException {
        //execute and checks
        UUID user1 = UUID.randomUUID();
        UUID user2 = UUID.randomUUID();
        final String postIdOfUser1 = postRepository.create(user1.toString(), "Hello");
        final String postIdOfUser2 = postRepository.create(user2.toString(), "Hi");
        List<Post> feed = postRepository.getFeed(0L, 100L);
        log.info("Feed = {}", objectMapper.writeValueAsString(feed));
        assertEquals(2, feed.size());
        final Post postByUser2 = postRepository.getById(postIdOfUser2);
        assertEquals(user2.toString(), postByUser2.getAuthorUserId());
        assertEquals("Hi", postByUser2.getText());
        boolean isDeleted = postRepository.deleteById(postIdOfUser2);
        assertTrue(isDeleted);
        assertThrows(EmptyResultDataAccessException.class, () -> postRepository.getById(postIdOfUser2));
        final Post postByUser1 = postRepository.getById(postIdOfUser1);
        assertEquals(user1.toString(), postByUser1.getAuthorUserId());
        assertEquals("Hello", postByUser1.getText());
        postRepository.update(postIdOfUser1, "Hello updated");
        final Post postByUser1Updated = postRepository.getById(postIdOfUser1);
        assertEquals(user1.toString(), postByUser1Updated.getAuthorUserId());
        assertEquals("Hello updated", postByUser1Updated.getText());
    }
}