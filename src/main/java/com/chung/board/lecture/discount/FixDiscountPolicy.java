package com.chung.board.lecture.discount;

import com.chung.board.lecture.member.Grade;
import com.chung.board.lecture.member.Member;

public class FixDiscountPolicy implements DiscountPolicy {
    @Override
    public int discount(Member member, int price) {
        if(member.getGrade()== Grade.VIP){
            return 1000;
        }
        return 0;
    }
}
