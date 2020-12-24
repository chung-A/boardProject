package com.chung.board.lecture;

import com.chung.board.lecture.member.Grade;
import com.chung.board.lecture.member.Member;
import com.chung.board.lecture.member.MemberService;
import com.chung.board.lecture.member.MemberServiceImpl;
import com.chung.board.lecture.order.Order;
import com.chung.board.lecture.order.OrderService;
import com.chung.board.lecture.order.OrderServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class OrderApp {
    public static void main(String[] args) {

//        AppConfig appConfig=new AppConfig();
//        MemberService memberService=appConfig.memberService();
//        OrderService orderService=appConfig.orderService();

        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
        MemberService memberService = applicationContext.getBean("memberService", MemberService.class);
        OrderService orderService = applicationContext.getBean("orderService", OrderService.class);

        Long memberId = 1L;
        Member member = new Member(memberId, "memberA", Grade.VIP);
        memberService.join(member);

        Order itemA = orderService.createOrder(memberId, "itemA", 10000);
        System.out.println("itemA = " + itemA);
        System.out.println("itemA = " + itemA.calculatePrice());
    }
}
