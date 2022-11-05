package com.sbs.exam.sb_app_2022_10_27.service;

import com.sbs.exam.sb_app_2022_10_27.repository.MemberRepository;
import com.sbs.exam.sb_app_2022_10_27.util.Ut;
import com.sbs.exam.sb_app_2022_10_27.vo.Member;
import com.sbs.exam.sb_app_2022_10_27.vo.ResultData;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

  private MemberRepository memberRepository;

  public MemberService(MemberRepository memberRepository) {
    this.memberRepository = memberRepository;
  }

  public ResultData<Integer> join(String loginId, String loginPw, String name, String nickname, String cellphoneNo, String email) {

    // 로그인 아이디 중복 체크
    Member oldMember = getMemberByLoginId(loginId);

    if ( oldMember != null) {
      return ResultData.from("F-7", Ut.f("해당 로그인아이디(%s)는 이미 사용중입니다.", loginId));
    }

    // 이름 + 이메일 중복 체크
    oldMember = getMemberByNameAndEmail(name, email);

    if ( oldMember != null ) {
      return ResultData.from("F-8", Ut.f("해당 이름(%s)과 이메일(%s)은 이미 사용중입니다.", name, email));
    }


    memberRepository.join(loginId, loginPw, name, nickname, cellphoneNo, email);
    int id = memberRepository.getLastInsertId();

    return ResultData.from("S-1", "회원 가입이 완료 되었습니다.", "id", id);
  }

  public Member getMemberByNameAndEmail(String name, String email) {
    return memberRepository.getMemberByNameAndEmail(name, email);
  }

  public Member getMemberByLoginId(String loginId) {
    return memberRepository.getMemberByloginId(loginId);
  }

  public Member getMemberById(int id) {
    return memberRepository.getMemberById(id);
  }
}
