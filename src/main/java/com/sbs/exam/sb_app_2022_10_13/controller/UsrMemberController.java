package com.sbs.exam.sb_app_2022_10_13.controller;


import com.sbs.exam.sb_app_2022_10_13.service.MemberService;
import com.sbs.exam.sb_app_2022_10_13.vo.Member;
import com.sbs.exam.sb_app_2022_10_13.util.Ut;
import com.sbs.exam.sb_app_2022_10_13.vo.ResultData;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UsrMemberController {
  private MemberService memberService;

  public UsrMemberController(MemberService memberService) {
    this.memberService = memberService;
  }

  @RequestMapping("usr/member/doJoin")
  @ResponseBody
  public ResultData<Member> doJoin(String loginId, String loginPw, String name, String nickname, String cellphoneNo, String email) {

    if ( Ut.empty(loginId) ) {
      return ResultData.from("F-1", "loginId(을)를 입력 해주세요.");
    }

    if( Ut.empty(loginPw) ) {
      return ResultData.from("F-2", "loginPw(을)를 입력 해주세요.");
    }

    if( Ut.empty(name) ) {
      return ResultData.from("F-3", "name(을)를 입력 해주세요.");
    }

    if( Ut.empty(nickname) ) {
      return ResultData.from("F-4", "nickname(을)를 입력 해주세요.");
    }

    if( Ut.empty(cellphoneNo) ) {
      return ResultData.from("F-5", "cellphoneNo(을)를 입력 해주세요.");
    }

    if( Ut.empty(email) ) {
      return ResultData.from("F-6", "email(을)를 입력 해주세요.");
    }

    ResultData<Integer> joinRd = memberService.join(loginId, loginPw, name, nickname, cellphoneNo, email);
    // resultCode

    if ( joinRd.isFail() ) {
      return (ResultData) joinRd;
    }

    Member member = memberService.getMemberById(joinRd.getData1());
    return ResultData.newData(joinRd, member);
  }
}
