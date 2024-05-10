package ru.ov4innikov.social.network.common.config;

import com.redis.testcontainers.RedisContainer;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.support.TestPropertySourceUtils;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@Log4j2
@Testcontainers
//@ContextConfiguration(initializers = TestContainersConfig.TestContainersConfigInitializer.class)
public class TestContainersConfig {
//
//    @Container
//    @ServiceConnection
//    public static final PostgreSQLContainer<?> POSTGRESQL_CONTAINER = new PostgreSQLContainer<>("postgres:latest")
//            .withReuse(true)
//            .withDatabaseName("postgres");
//
//    @Container
//    @ServiceConnection
//    public static final RedisContainer REDIS_CONTAINER = new RedisContainer(DockerImageName.parse("redis:latest")).withExposedPorts(6380);
//
//    public static class TestContainersConfigInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
//
//        @Override
//        public void initialize(ConfigurableApplicationContext applicationContext) {
//            log.info("Url = {}", POSTGRESQL_CONTAINER::getJdbcUrl);
//            log.info("Password = {}", POSTGRESQL_CONTAINER::getPassword);
//            log.info("Username = {}", POSTGRESQL_CONTAINER::getUsername);
//            log.info("Redis url = {}", REDIS_CONTAINER.getRedisURI());
//            TestPropertySourceUtils.addInlinedPropertiesToEnvironment(
//                    applicationContext,
//                    "spring.datasource.url=" + POSTGRESQL_CONTAINER.getJdbcUrl(),
//                    "spring.datasource.password=" + POSTGRESQL_CONTAINER.getJdbcUrl(),
//                    "spring.datasource.username=" + POSTGRESQL_CONTAINER.getUsername(),
//                    "spring.data.redis.url=" + REDIS_CONTAINER.getRedisURI()
//            );
//        }
//    }
//
//    @DynamicPropertySource
//    static void datasourceProperties(DynamicPropertyRegistry registry) {
//        registry.add("spring.datasource.url", POSTGRESQL_CONTAINER::getJdbcUrl);
//        registry.add("spring.datasource.password", POSTGRESQL_CONTAINER::getPassword);
//        registry.add("spring.datasource.username", POSTGRESQL_CONTAINER::getUsername);
//        registry.add("spring.data.redis.url", () -> REDIS_CONTAINER.getHost() + ":" + REDIS_CONTAINER.getMappedPort(6379).toString());
//    }
}
