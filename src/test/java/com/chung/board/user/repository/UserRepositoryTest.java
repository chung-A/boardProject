package com.chung.board.user.repository;

import com.chung.board.user.UserVO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class UserRepositoryTest {

    UserRepository userRepository=new MemoryUserRepositoryImpl();

    @AfterEach
    public void afterEach() {
        userRepository.clearData();
    }

    @Test
    void save() {
        UserVO user = new UserVO();
        user.setName("test");

        userRepository.save(user);

        UserVO result = userRepository.findById(user.getId()).get();
        assertThat(user).isEqualTo(result);
    }

    @Test
    public void findByName() {
        //given
        UserVO userVO1 = new UserVO();
        userVO1.setName("spring1");
        userRepository.save(userVO1);
        
        UserVO userVO2 = new UserVO();
        userVO2.setName("spring2");
        userRepository.save(userVO2);
        //when
        UserVO result = userRepository.findByName("spring1").get();
        
        //then
        assertThat(result).isEqualTo(userVO1);
    }
    @Test
    public void findAll() {
        //given
        UserVO userVO1 = new UserVO();
        userVO1.setName("spring1");
        userRepository.save(userVO1);

        UserVO userVO2 = new UserVO();
        userVO2.setName("spring2");
        userRepository.save(userVO2);
        
        //when
        List<UserVO> result = userRepository.findAll();
        //then
        assertThat(result.size()).isEqualTo(2);
    }
}