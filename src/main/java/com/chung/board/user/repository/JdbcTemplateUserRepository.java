package com.chung.board.user.repository;

import com.chung.board.user.UserVO;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class JdbcTemplateUserRepository implements UserRepository{

    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateUserRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public UserVO save(UserVO userVO) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("user").usingGeneratedKeyColumns("id");
        //파라미터 바인딩
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", userVO.getName());
        // 실행 & db 에서 생성된 key 받아오기
        Number key = jdbcInsert.executeAndReturnKey(new
                MapSqlParameterSource(parameters));
        userVO.setId(key.longValue());
        return userVO;
    }

    @Override
    public Optional<UserVO> findById(Long id) {
        List<UserVO> result = jdbcTemplate.query("select * from user where id = ?", userRowMapper(), id);
        return result.stream().findAny();
    }

    @Override
    public Optional<UserVO> findByName(String name) {
        List<UserVO> result = jdbcTemplate.query("select * from user where id= ?", userRowMapper(),name);
        return result.stream().findAny();
    }

    @Override
    public List<UserVO> findAll() {
        return jdbcTemplate.query("select * from user", userRowMapper());
    }

    private RowMapper<UserVO> userRowMapper() {
        return (rs, rowNum) -> {
            UserVO userVO = new UserVO();
            userVO.setId(rs.getLong("id"));
            userVO.setName(rs.getString("name"));
            return userVO;
        };
    }

    @Override
    public void clearData() {

    }
}
