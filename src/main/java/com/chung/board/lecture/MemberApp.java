package com.chung.board.lecture;

import com.chung.board.lecture.member.Grade;
import com.chung.board.lecture.member.Member;
import com.chung.board.lecture.member.MemberService;
import com.chung.board.lecture.member.MemberServiceImpl;

public class MemberApp {

    public static void main(String[] args) {
        AppConfig appConfig = new AppConfig();
        MemberService memberService=appConfig.memberService();

    }
}
