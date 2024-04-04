package ru.ov4innikov.social.network.user.repository;

import io.micrometer.observation.annotation.Observed;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.ov4innikov.social.network.user.model.User;

import java.sql.PreparedStatement;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Log4j2
@Observed
@RequiredArgsConstructor
@Repository
public class DbUserRepository implements UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public static final RowMapper<User> USER_ROW_MAPPER = (resultSet, rowNum) ->
            User.builder()
                    .id(resultSet.getString("id"))
                    .firstName(resultSet.getString("first_name"))
                    .middleName(resultSet.getString("middle_name"))
                    .secondName(resultSet.getString("second_name"))
                    .birthdate(resultSet.getObject("birthdate", OffsetDateTime.class))
                    .biography(resultSet.getString("biography"))
                    .city(resultSet.getString("city"))
                    .interests(resultSet.getString("interests"))
                    .isMale(resultSet.getObject("is_male", Boolean.class))
                    .password(resultSet.getString("password"))
                    .role("ROLE_USER")
                    .build();

    @Override
    public String save(User user) {
        String sqlQuery = """
                INSERT INTO sc.user(
                    first_name,
                    middle_name,
                    second_name,
                    birthdate,
                    biography,
                    city,
                    interests,
                    is_male,
                    password)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sqlQuery, new String[]{"id"});
            stmt.setString(1, user.getFirstName());
            stmt.setString(2, user.getMiddleName());
            stmt.setString(3, user.getSecondName());
            stmt.setObject(4, user.getBirthdate());
            stmt.setString(5, user.getBiography());
            stmt.setString(6, user.getCity());
            stmt.setString(7, user.getInterests());
            stmt.setObject(8, user.getIsMale());
            stmt.setString(9, user.getPassword());
            return stmt;
        }, keyHolder);
        return keyHolder.getKeys().get("id").toString();
    }

    @Cacheable("id")
    @Override
    public User getById(String id) {
        return jdbcTemplate.queryForObject(
                "SELECT * FROM sc.user WHERE id = ?",
                USER_ROW_MAPPER,
                UUID.fromString(id));
    }

    @Override
    public List<User> findUsersByFirstNameAndSecondName(String firstName, String secondName) {
            return jdbcTemplate.query("SELECT * FROM sc.user WHERE first_name LIKE ? AND second_name LIKE ? ORDER BY id", USER_ROW_MAPPER, firstName + "%", secondName + "%");
    }

    @Override
    public long clean() {
        return jdbcTemplate.update("TRUNCATE TABLE sc.user");
    }
}