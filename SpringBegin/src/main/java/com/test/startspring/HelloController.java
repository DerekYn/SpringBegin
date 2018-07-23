package com.test.startspring;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HelloController {

	@RequestMapping(value="/hello.action", method= {RequestMethod.GET})  
	public String hello(HttpServletRequest req) {
		
		// DB 작업은 생략
		
		req.setAttribute("name", "홍길동");
		req.setAttribute("age", 25);
		req.setAttribute("addr", "서울시 종로구 인사동");
		
		return "hello";
		//  /WEB-INF/views/hello.jsp 페이지를 뷰단으로 한다.
	}// end of 
	
}
