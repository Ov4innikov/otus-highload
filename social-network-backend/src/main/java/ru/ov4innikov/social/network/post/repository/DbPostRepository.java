package ru.ov4innikov.social.network.post.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.ov4innikov.social.network.model.Post;

import java.math.BigDecimal;
import java.sql.Types;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Log4j2
@RequiredArgsConstructor
@Repository
public class DbPostRepository implements PostRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public static final RowMapper<Post> POST_ROW_MAPPER = (resultSet, rowNum) ->
            new Post()
                    .id(resultSet.getString("id"))
                    .text(resultSet.getString("text"))
                    .authorUserId(resultSet.getString("author_user_id"));

    @Override
    public String create(String authorUserId, String text) {
        String sqlQueryInsert = """
                INSERT INTO sc.post(
                    author_user_id,
                    text)
                VALUES (:author_user_id, :text)
                """;
        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource paramSource = new MapSqlParameterSource();
        paramSource.addValue("author_user_id", authorUserId, Types.VARCHAR);
        paramSource.addValue("text", text, Types.VARCHAR);
        jdbcTemplate.update(sqlQueryInsert, paramSource, keyHolder);
        return keyHolder.getKeys().get("id").toString();
    }

    @Override
    public boolean deleteById(String id) {
        String sqlQueryDelete = """
                DELETE FROM sc.post
                WHERE id = :id
                """;
        MapSqlParameterSource paramSource = new MapSqlParameterSource();
        paramSource.addValue("id", UUID.fromString(id));
        int updateCount = jdbcTemplate.update(sqlQueryDelete, paramSource);
        return updateCount > 0;
    }

    @Override
    public List<Post> getFeed(Long offset, Long limit) {
        String sqlQueryGetFeed = """
                SELECT *
                FROM sc.post
                LIMIT :limit
                OFFSET :offset
                """;
        Map<String, Long> mapOfParameters = Map.of("offset", offset, "limit", limit);
        SqlParameterSource namedParameters = new MapSqlParameterSource(mapOfParameters);
        return jdbcTemplate.query(sqlQueryGetFeed, namedParameters, POST_ROW_MAPPER);
    }

    @Override
    public Post getById(String id) {
        String sqlQueryGetById = """
                SELECT *
                FROM sc.post
                WHERE id = :id
                """;
        SqlParameterSource namedParameters = new MapSqlParameterSource("id", UUID.fromString(id));
        return jdbcTemplate.queryForObject(sqlQueryGetById, namedParameters, POST_ROW_MAPPER);
    }

    @Override
    public boolean update(String id, String text) {
        String sqlQueryUpdate = """
                UPDATE sc.post
                SET text = :text
                WHERE id = :id
                """;
        Map<String, Object> mapOfParameters = Map.of("text", text, "id", UUID.fromString(id));
        SqlParameterSource namedParameters = new MapSqlParameterSource(mapOfParameters);
        int update = jdbcTemplate.update(sqlQueryUpdate, namedParameters);
        return update > 0;
    }

    @Override
    public long clean() {
        SqlParameterSource namedParameters = new MapSqlParameterSource();
        return jdbcTemplate.update("TRUNCATE TABLE sc.post", namedParameters);
    }
}
