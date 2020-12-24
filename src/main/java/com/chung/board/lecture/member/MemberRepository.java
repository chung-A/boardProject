package com.chung.board.lecture.member;

public interface MemberRepository {

    void save(Member member);

    Member findById(Long memberId);
}
