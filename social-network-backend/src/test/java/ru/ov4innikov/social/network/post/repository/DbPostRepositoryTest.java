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
import ru.ov4innikov.social.network.model.Post;
import ru.ov4innikov.social.network.model.UserRegisterPostRequest;
import ru.ov4innikov.social.network.user.repository.UserRepository;
import ru.ov4innikov.social.network.user.service.UserService;

import java.util.List;

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
        final String postIdOfUser1 = postRepository.create("userId1", "Hello");
        final String postIdOfUser2 = postRepository.create("userId2", "Hi");
        List<Post> feed = postRepository.getFeed(0L, 100L);
        log.info("Feed = {}", objectMapper.writeValueAsString(feed));
        assertEquals(2, feed.size());
        final Post postByUser2 = postRepository.getById(postIdOfUser2);
        assertEquals("userId2", postByUser2.getAuthorUserId().orElse(null));
        assertEquals("Hi", postByUser2.getText().orElse(null));
        boolean isDeleted = postRepository.deleteById(postIdOfUser2);
        assertTrue(isDeleted);
        assertThrows(EmptyResultDataAccessException.class, () -> postRepository.getById(postIdOfUser2));
        final Post postByUser1 = postRepository.getById(postIdOfUser1);
        assertEquals("userId1", postByUser1.getAuthorUserId().orElse(null));
        assertEquals("Hello", postByUser1.getText().orElse(null));
        postRepository.update(postIdOfUser1, "Hello updated");
        final Post postByUser1Updated = postRepository.getById(postIdOfUser1);
        assertEquals("userId1", postByUser1Updated.getAuthorUserId().orElse(null));
        assertEquals("Hello updated", postByUser1Updated.getText().orElse(null));
    }
}