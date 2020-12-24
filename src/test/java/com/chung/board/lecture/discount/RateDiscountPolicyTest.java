package com.chung.board.lecture.discount;

import com.chung.board.lecture.member.Grade;
import com.chung.board.lecture.member.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RateDiscountPolicyTest {

    RateDiscountPolicy rateDiscountPolicy=new RateDiscountPolicy();

    @Test
    @DisplayName("VIP는 10%할인이 되어야 한다.")
    void vipTest(){
        //given
        Member member=new Member(1L,"VIP", Grade.VIP);

        //when
        int discount = rateDiscountPolicy.discount(member, 10000);

        //then
        Assertions.assertThat(discount).isEqualTo(1000);
    }

    @Test
    @DisplayName("VIP아니면 할인되면 안됨.") //작동안하면 세팅-Gradle-intelij로 변경
    void vipTest2(){
        //given
        Member member=new Member(2L,"VIP", Grade.BASIC);

        //when
        int discount = rateDiscountPolicy.discount(member, 10000);

        //then
        Assertions.assertThat(discount).isEqualTo(0);
    }
}