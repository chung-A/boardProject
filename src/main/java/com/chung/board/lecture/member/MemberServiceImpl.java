package com.chung.board.lecture.member;

public class MemberServiceImpl implements MemberService {

    // 마무리 자동완성: 컨트롤 시프트 엔터
    private final MemberRepository memberRepository;

    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public void join(Member member) {
        memberRepository.save(member);
    }

    @Override
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
