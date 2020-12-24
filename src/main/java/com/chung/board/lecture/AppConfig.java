package com.chung.board.lecture;

import com.chung.board.lecture.discount.DiscountPolicy;
import com.chung.board.lecture.discount.RateDiscountPolicy;
import com.chung.board.lecture.member.MemberRepository;
import com.chung.board.lecture.member.MemberService;
import com.chung.board.lecture.member.MemberServiceImpl;
import com.chung.board.lecture.member.MemoryMemberRepository;
import com.chung.board.lecture.order.OrderService;
import com.chung.board.lecture.order.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    //생성자 주입.
    //컨트롤 알트 M= extract method.
    @Bean
    public MemberService memberService(){
        return new MemberServiceImpl(getMemberRepository());
    }

    @Bean
    public OrderService orderService() {
        return new OrderServiceImpl(getMemberRepository(), getDiscountPolicy());
    }

    @Bean
    public MemberRepository getMemberRepository() {
        return new MemoryMemberRepository();
    }

    @Bean
    public DiscountPolicy getDiscountPolicy() {
        return new RateDiscountPolicy();
    }
}
