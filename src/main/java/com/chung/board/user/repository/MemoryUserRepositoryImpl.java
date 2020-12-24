package com.chung.board.user.repository;

import com.chung.board.user.UserVO;
import org.springframework.stereotype.Repository;

import java.util.*;

public class MemoryUserRepositoryImpl implements UserRepository {

    Map<Long, UserVO> userMap = new HashMap<>();
    private static long sequence=0L;

    @Override
    public UserVO save(UserVO userVO) {
        userVO.setId(++sequence);
        userMap.put(userVO.getId(), userVO);
        return userVO;
    }

    @Override
    public Optional<UserVO> findById(Long id) {
        return Optional.ofNullable(userMap.get(id));
    }

    @Override
    public Optional<UserVO> findByName(String name) {
        return userMap.values().stream().filter(
                u->u.getName().equals(name)
        ).findAny();
    }

    @Override
    public List<UserVO> findAll() {
        return new ArrayList<>(userMap.values());
    }

    @Override
    public void clearData() {
        userMap.clear();
    }
}
