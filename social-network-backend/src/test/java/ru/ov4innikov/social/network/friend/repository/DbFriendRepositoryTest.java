package ru.ov4innikov.social.network.friend.repository;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.ov4innikov.social.network.common.config.TestContainersConfig;
import ru.ov4innikov.social.network.model.Gender;
import ru.ov4innikov.social.network.model.UserRegisterPostRequest;
import ru.ov4innikov.social.network.user.repository.UserRepository;
import ru.ov4innikov.social.network.user.service.UserService;

import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@Log4j2
@DirtiesContext
@SpringBootTest
class DbFriendRepositoryTest extends TestContainersConfig {

    @Autowired
    private FriendRepository friendRepository;

    @Autowired
    private UserService userService;

    private String userId;

    @BeforeEach
    void setUp() {
        friendRepository.clean();
        UserRegisterPostRequest userRegisterPostRequest = new UserRegisterPostRequest()
                .firstName("Василий")
                .middleName("Васильевич")
                .secondName("Василевский")
                .biography("Сын Ввасилия")
                .birthdate(OffsetDateTime.parse("2000-01-04T10:00:12.000Z"))
                .city("Краснодар")
                .gender(Gender.MALE)
                .password("password")
                .interests("Интерересы");
        userId = userService.registerUser(userRegisterPostRequest).getUserId().get();
    }

    @AfterEach
    void tearDown() {
        friendRepository.clean();
    }

    @Test
    public void test_addAndDeleteFriend_positive() {
        String userId1 = UUID.randomUUID().toString();
        String userId2 = UUID.randomUUID().toString();
        assertEquals(0, friendRepository.getFriendIds(userId).size());
        friendRepository.addFriend(userId, userId1);
        friendRepository.addFriend(userId, userId2);
        assertEquals(2, friendRepository.getFriendIds(userId).size());
        friendRepository.deleteFriend(userId, userId2);
        assertEquals(1, friendRepository.getFriendIds(userId).size());
    }
}