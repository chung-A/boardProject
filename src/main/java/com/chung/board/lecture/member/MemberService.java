package com.chung.board.lecture.member;

public interface MemberService {

    void join(Member member);

    Member findMember(Long memberId);
}
