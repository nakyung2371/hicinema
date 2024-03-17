package com.himedia.hicinema.member;

import java.security.Principal;
import java.util.Optional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import oracle.jdbc.proxy.annotation.Post;


@RequiredArgsConstructor              //  final로 선언된 필드에 대한 생성자를 자동으로 생성
@Controller
@RequestMapping("/member")
public class MemberController {

	// 생성자 주입
	private final MemberService  memberService;
	private final MemberRepository memRepository;
	// 회원가입 페이지 출력 요청
	// http://localhost:8010/member/register 
	@GetMapping("/register")
	public String register (MemberCreateForm memberCreateForm) {
		
		return "member/register";
	}
	
	// BindingResult : 유효성 검증결과를 받아올 때
	
	
	
	// 회원가입 =================
	
	@PostMapping("/register")
	public String register (@Valid MemberCreateForm memberCreateForm , BindingResult bindingResult) {
		
		System.out.println("======회원가입==========");
		System.out.println(memberCreateForm.getName());
		System.out.println(memberCreateForm.getEmail());
		System.out.println(memberCreateForm.getPassword1());
		System.out.println(memberCreateForm.getPassword2());
		System.out.println(memberCreateForm.getPhone());
		System.out.println("======회원가입==========");
		
		
		
		if(bindingResult.hasErrors()) {
			return "member/register";
		}
		
		// 2개의 비밀번호가 일치하지 않을 때 (필드명, 오류 코드, 오류 메시지)
		if(!memberCreateForm.getPassword1().equals(memberCreateForm.getPassword2())) {    
			bindingResult.rejectValue("password2", "passwordInCorrect", 
                    "비밀번호가 일치하지 않습니다.");
            return "member/register";
			
		}
		
		try {
				memberService.create(
									memberCreateForm.getName(),
									memberCreateForm.getMemberId(),
									memberCreateForm.getPassword1(), 
									memberCreateForm.getEmail(),
									memberCreateForm.getPhone()
									);
		} catch(DataIntegrityViolationException e) {
			
			e.printStackTrace();
			bindingResult.reject("signupFailed", "이미 등록된 이용자입니다.");
			return "member/register";
		} catch(Exception e) {
			e.printStackTrace();
			bindingResult.reject("signupFailed", e.getMessage());  // e.getMessage : 구체적인 오류메세지
			return "member/register";
		}
		
        return "redirect:/";
	}
	
//	@PostMapping("/register")
//	public String save(@ModelAttribute MemberDTO memberDTO) {
//		System.out.println ("성공" );
//		System.out.println("memberDTO = " + memberDTO);
//		
//		memberService.create(memberDTO);
//		
//		return "member/login";
//	}
//	
//	
	
	
	 // 로그인 ===================
	
	  @GetMapping("/login")
	    public String login() {
	        return "member/login";
	    }
	

	// 로그아웃 ===================
		
//	 @GetMapping("/logout")
//		public String logout() {
//		    return "redirect:/";
//	}
	 
	// 마이페이지 =================
	
	  @GetMapping("/mypage_main")
	  public String mypage () {
	  	return "member/mypage_main";
	  }
	  
	 
	 //마이페이지_ 정보조회
	  @GetMapping("/mypage/mypage_edit")
	  
	  public String mypagemodify(Principal principal, Model model) {
		  
		  //클라이언트에서 로그온한 ID를 불러옴 
		  String mem = principal.getName(); 
		  System.out.println("로그온한 계정 : " + mem);
		  
		  Optional<Member> _member = memRepository.findByMemberId(mem);
	       Member member = _member.get(); 
	       
//	       System.out.println(member.getEmail());
//	       System.out.println(member.getMemberId());
//	       System.out.println(member.getPhone());
	       
	       
	       model.addAttribute("member", member);
	        
		  
	  	return "member/mypage/mypage_edit";
	  }
	  
	// 마이페이지_ 정보수정
	  
     @PostMapping("/mypage/mypage_edit")
	  
     public String UpdateMember(@ModelAttribute Member memberupdate, @RequestParam String password, Model model , Principal principal) {
    	 String mem = principal.getName(); 
    	 System.out.println("회원 수정");
		  System.out.println("로그온한 계정 : " + mem);
		  System.out.println("로그온한 패스워드 : " + password);
		  
    	 Optional<Member> _member = memRepository.findByMemberId(mem);
	       Member member = _member.get(); 
    	 
    	 if (!password.equals(member.getPassword())) {
             model.addAttribute("error", "비밀번호가 맞지 않습니다.");
             return "member/mypage/mypage_edit";
         }
         
         // 비밀번호가 일치하면 회원 정보 업데이트
         member.setPhone(memberupdate.getPhone());
         member.setEmail(memberupdate.getEmail());
         
         memRepository.save(member); 
         
         
         return "member/mypage_main";
     }
		  
	  	
	  
	  
	  // 회원탈퇴
	  @PostMapping("/delete")
	  public String memberWithdrawal(@RequestParam String password, Model model, Authentication authentication) {
	      UserDetails userDetails = (UserDetails) authentication.getPrincipal();
	      boolean result = memberService.delete(userDetails.getUsername(), password);

	      if (result) {
	          return "redirect:/logout";
	      } else {
	          model.addAttribute("wrongPassword", "비밀번호가 맞지 않습니다.");
	          return "/members/withdrawal";
	      }
	  }
	
	  
	 
}
