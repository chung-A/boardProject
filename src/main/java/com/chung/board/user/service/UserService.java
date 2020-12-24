package com.chung.board.user.service;

import com.chung.board.cmmn.MyLogger;
import com.chung.board.user.repository.UserRepository;
import com.chung.board.user.UserVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * 회원가입.
     */
    public Long join(UserVO userVO) {
        //이름 중복검사.
        ValidateUserInfo(userVO);

        //저장.
        UserVO savedUser = userRepository.save(userVO);
        return savedUser.getId();
    }

    private void ValidateUserInfo(UserVO userVO) {
        userRepository.findByName(userVO.getName()).ifPresent(
                u-> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }

    /**
     * 전체회원 조회
     */
    public List<UserVO> findAllUsers() {
        return userRepository.findAll();
    }

    /**
     * 유저 id로 조회
     */
    public Optional<UserVO> findOne(Long userId) {
        return userRepository.findById(userId);
    }

    /*
    private final MyLogger myLogger;
    private final UserRepository userRepository;

    public void logic(String msg) {
        myLogger.log(msg);
    }

    public void joinUser(UserVO vo){
        long start = System.currentTimeMillis();

        userRepository.save(vo);

        long end = System.currentTimeMillis();
        System.out.println("걸린시간 = " + (end-start));
    }

    public UserVO loginUser(String id,String pw){
        long start = System.currentTimeMillis();

        long end = System.currentTimeMillis();
        System.out.println("걸린시간 = " + (end-start));
        return null;
    }
    */
}
