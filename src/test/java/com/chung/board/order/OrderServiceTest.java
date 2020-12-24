package com.chung.board.order;

import com.chung.board.lecture.AppConfig;
import com.chung.board.lecture.member.Grade;
import com.chung.board.lecture.member.Member;
import com.chung.board.lecture.member.MemberService;
import com.chung.board.lecture.member.MemberServiceImpl;
import com.chung.board.lecture.order.Order;
import com.chung.board.lecture.order.OrderService;
import com.chung.board.lecture.order.OrderServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OrderServiceTest {

    MemberService memberService;
    OrderService orderService;

    @BeforeEach
    public void beforeEach() {
        AppConfig appConfig = new AppConfig();
        memberService = appConfig.memberService();
        orderService = appConfig.orderService();
    }

    @Test
    void createOrder() {
        //given
        Long memberId = 1L;
        Member member = new Member(memberId, "memberA", Grade.VIP);

        //when
        memberService.join(member);
        Order order = orderService.createOrder(memberId, "itemA", 10000);

        //then
        Assertions.assertThat(order.getDiscountPrice()).isEqualTo(1000);
    }
}
