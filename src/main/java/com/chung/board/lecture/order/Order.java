package com.chung.board.lecture.order;

import com.chung.board.lecture.member.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class Order {
    private Long orderId;
    private String itemName;
    private int itemPrice;
    private int discountPrice;

    private Member member;  //주문자 정보.

    public Order(Long memberId, String itemName, int itemPrice, int discountPrice) {
    }

    public int calculatePrice(){
        return itemPrice-discountPrice;
    }
}
