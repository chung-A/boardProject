package com.chung.board.member;

import com.chung.board.lecture.AppConfig;
import com.chung.board.lecture.member.Grade;
import com.chung.board.lecture.member.Member;
import com.chung.board.lecture.member.MemberService;
import com.chung.board.lecture.member.MemberServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MemberServiceTest {

    MemberService memberService;

    @BeforeEach
    public void beforeEach(){
        AppConfig appConfig=new AppConfig();
        memberService=appConfig.memberService();
    }

    @Test
    void join(){
        //given --이런 환경에서
        Member memberA = new Member(1L, "memberA", Grade.VIP);

        //when - 이렇게 하면
        memberService.join(memberA);
        Member memberB = memberService.findMember(memberA.getId());

        //then --이렇게 된다
        Assertions.assertThat(memberA).isEqualTo(memberB);
    }
}
