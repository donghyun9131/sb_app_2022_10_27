package com.sbs.exam.sb_app_2022_10_27.controller;


import com.sbs.exam.sb_app_2022_10_27.service.MemberService;
import com.sbs.exam.sb_app_2022_10_27.vo.Member;
import com.sbs.exam.sb_app_2022_10_27.util.Ut;
import com.sbs.exam.sb_app_2022_10_27.vo.ResultData;
import com.sbs.exam.sb_app_2022_10_27.vo.Rq;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class UsrMemberController {
  private MemberService memberService;
  private Rq rq;

  public UsrMemberController(MemberService memberService, Rq rq) {
    this.memberService = memberService;
    this.rq = rq;
  }

  @RequestMapping("/usr/member/join")
  public String showJoin() {
    return "usr/member/join";
  }

  @RequestMapping("usr/member/doJoin")
  @ResponseBody
  public String doJoin(String loginId, String loginPw, String name, String nickname, String cellphoneNo, String email) {

    if ( Ut.empty(loginId) ) {
      return rq.jsHistoryBack("loginId(을)를 입력 해주세요.");
    }

    if( Ut.empty(loginPw) ) {
      return rq.jsHistoryBack("loginPw(을)를 입력 해주세요.");
    }

    if( Ut.empty(name) ) {
      return rq.jsHistoryBack("name(을)를 입력 해주세요.");
    }

    if( Ut.empty(nickname) ) {
      return rq.jsHistoryBack("nickname(을)를 입력 해주세요.");
    }

    if( Ut.empty(cellphoneNo) ) {
      return rq.jsHistoryBack("cellphoneNo(을)를 입력 해주세요.");
    }

    if( Ut.empty(email) ) {
      return rq.jsHistoryBack("email(을)를 입력 해주세요.");
    }

    ResultData<Integer> joinRd = memberService.join(loginId, loginPw, name, nickname, cellphoneNo, email);

    // 회원가입 실패 확인
    if ( joinRd.isFail() ) {
      return rq.jsHistoryBack(Ut.f("%s", joinRd.getMsg()));
    }

    Member member = memberService.getMemberById(joinRd.getData1());

    // 로그인 페이지로 이동
    return rq.jsReplace(Ut.f("%s", joinRd.getMsg()), "/usr/member/login");
  }

  @RequestMapping("/usr/member/login")
  public String showLogin(HttpSession httpSession) {
    return "usr/member/login";
  }

  @RequestMapping("/usr/member/doLogin")
  @ResponseBody
  public String doLogin(String loginId, String loginPw) {
    if ( rq.isLogined() ) {
      return rq.jsHistoryBack("이미 로그인 되었습니다.");
    }

    if( Ut.empty(loginId) ) {
      return rq.jsHistoryBack("loginId(을)를 입력 해주세요.");
    }

    if( Ut.empty(loginPw) ) {
      return rq.jsHistoryBack("loginPw(을)를 입력 해주세요.");
    }
    // DB에서 입력된 loginID와 같은 회원정보를 가져온다.
    Member member = memberService.getMemberByLoginId(loginId);

    // 해당 회원 정보가 없으면 로그인창으로 메세지와 함께 이동.
    if ( member == null ) {
      return rq.jsHistoryBack("존재하지 않는 로그인아이디 입니다.");
    }

    // 멤버변수에서 비밀번호를 얻어와 입력된 비밀번호와 같은지 확인.
    if(member.getLoginPw().equals(loginPw) == false) {
      return rq.jsHistoryBack("비밀번호가 일치하지 않습니다.");
    }

    // rq에 로그인한 멤버에 해당하는 값을 set
    rq.login(member);

    return rq.jsReplace(Ut.f("%s님 환영합니다.", member.getNickname()), "/");
  }

  @RequestMapping("/usr/member/doLogout")
  @ResponseBody
  public String doLogout() {
    if ( !rq.isLogined() ) {
      return rq.jsHistoryBack("이미 로그아웃 상태입니다.");
    }

    rq.logout();

    return rq.jsReplace(Ut.f("로그아웃 되었습니다."), "/");
  }
}
