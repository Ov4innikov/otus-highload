package ru.ov4innikov.social.network.friend.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.annotation.Caching;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Log4j2
@RequiredArgsConstructor
@Repository
public class DbFriendRepository implements FriendRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public List<String> getFriendIds(String currentUserId) {
        if (currentUserId == null) return null;
        String sqlQueryGetFriendIds = """
                SELECT *
                FROM sc.friend
                WHERE user_id = :currentUserId
                """;
        Map<String, Object> mapOfParameters = Map.of("currentUserId", UUID.fromString(currentUserId));
        SqlParameterSource namedParameters = new MapSqlParameterSource(mapOfParameters);
        return jdbcTemplate.query(sqlQueryGetFriendIds, namedParameters, (resultSet, rowNum) -> resultSet.getString("friend_user_id"));
    }

    @Override
    public void addFriend(String currentUserId, String friendUserId) {
        String sqlQueryInsert = """
                INSERT INTO sc.friend(
                        user_id,
                        friend_user_id
                    )
                VALUES (:currentUserId, :friendUserId)
                """;
        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource paramSource = new MapSqlParameterSource();
        paramSource.addValue("currentUserId", UUID.fromString(currentUserId));
        paramSource.addValue("friendUserId", UUID.fromString(friendUserId));
        jdbcTemplate.update(sqlQueryInsert, paramSource, keyHolder);
    }

    @Override
    public void deleteFriend(String currentUserId, String friendUserId) {
        String sqlQueryDelete = """
                DELETE FROM sc.friend
                WHERE user_id = :currentUserId AND friend_user_id = :friendUserId
                """;
        MapSqlParameterSource paramSource = new MapSqlParameterSource();
        paramSource.addValue("currentUserId", UUID.fromString(currentUserId));
        paramSource.addValue("friendUserId", UUID.fromString(friendUserId));
        int updateCount = jdbcTemplate.update(sqlQueryDelete, paramSource);
    }

    @Override
    public long clean() {
        SqlParameterSource namedParameters = new MapSqlParameterSource();
        return jdbcTemplate.update("TRUNCATE TABLE sc.friend", namedParameters);
    }
}
