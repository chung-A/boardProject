package com.chung.board.lecture.discount;

import com.chung.board.lecture.member.Grade;
import com.chung.board.lecture.member.Member;

public class RateDiscountPolicy implements DiscountPolicy{

    private int discount=10;

    @Override
    public int discount(Member member, int price) {
        if(member.getGrade()== Grade.VIP){
            return price*discount/100;
        }
        return 0;
    }
}
