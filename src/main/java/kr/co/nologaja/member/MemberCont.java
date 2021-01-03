package kr.co.nologaja.member;

import javax.inject.Inject;
import javax.management.BadBinaryOpValueExpException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import jdk.jfr.MetadataDefinition;


@Controller
public class MemberCont {
	
	@Inject
	BuyerDAO bdao;
	
	@Inject
	SellerDAO sdao;
	
	public MemberCont() {
		System.out.println("==MemberCont()==");
	}
	
	//회원가입 페이지(배너 상의 회원가입 버튼)
	@RequestMapping(value = "/memberform.do")
	   public ModelAndView form() {
	      ModelAndView mav = new ModelAndView();
	      mav.setViewName("member/memberform");
	      return mav;
	   }
	
	//회원가입 구매자 dto
	@RequestMapping("/buyerjoin.do")
	public String insert(@ModelAttribute BuyerDTO dto) {
		bdao.insert(dto);
		return "member/login";
	}//insert() end
	

	//회원가입 판매자 dto
	@RequestMapping("/sellerjoin.do")
	public String insert(@ModelAttribute SellerDTO dto) {
		sdao.insert(dto);
		return "member/login";
	}//insert() end

//---------------------------------------------------------------------------------------------------------------------------------------------
	//로그인페이지로 이동
	@RequestMapping("/login.do")
	   public ModelAndView login() {
	      ModelAndView mav = new ModelAndView();
	      mav.setViewName("member/login");
	      return mav;
   }
	

	//구매자 로그인처리
	@RequestMapping("/blogin.do")
	public ModelAndView blogin(String uid, String upw, HttpServletRequest request) {
		boolean result = bdao.blogin(uid, upw);
		ModelAndView mav = new ModelAndView();
		if(result==true) {
			String ugrd=bdao.read_bgrd(uid, upw);
			mav.setViewName("index");
			HttpSession session=request.getSession();
			session.setAttribute("uid", uid);
			session.setAttribute("ugrd", ugrd);
			session.setMaxInactiveInterval(20*60*24);
		}else {
			mav.setViewName("member/login");
		}
		return mav;
	}//blogin() end
	
	
	//판매자 로그인처리
	@RequestMapping("/slogin.do")
	public ModelAndView slogin(String suid, String supw, HttpServletRequest request) {
		boolean result = sdao.slogin(suid, supw);
		ModelAndView mav = new ModelAndView();
		if(result==true) {
			String ugrd=sdao.read_sgrd(suid, supw);
			mav.setViewName("index");
			HttpSession session=request.getSession();
			session.setAttribute("suid", suid);
			session.setAttribute("ugrd", ugrd);
			session.setMaxInactiveInterval(20*60*24);
		}else {
			mav.setViewName("member/login");
		}
		return mav;
	}//slogin() end
	
	//로그아웃
	@RequestMapping("/logout.do")
    public ModelAndView logout(HttpServletRequest request) {
		HttpSession session=request.getSession();
		session.removeAttribute("suid");
		session.removeAttribute("uid");
		ModelAndView mav = new ModelAndView();
	    mav.setViewName("member/login");
	    return mav;
	}
	
//------------------------------------------------------------------------------------------------------------------------------

}//class end
