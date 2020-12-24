package com.chung.board.lecture.discount;

import com.chung.board.lecture.member.Member;

public interface DiscountPolicy {

    /**
     * @return 할인 대상금액
     */
    int discount(Member member,int price);
}
