package com.test.startspring;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HelloController {

	@RequestMapping(value="/hello.action", method= {RequestMethod.GET})  
	public String hello(HttpServletRequest req) {
		
		// DB �۾��� ����
		
		req.setAttribute("name", "ȫ�浿");
		req.setAttribute("age", 25);
		req.setAttribute("addr", "����� ���α� �λ絿");
		
		return "hello";
		//  /WEB-INF/views/hello.jsp �������� ������� �Ѵ�.
	}// end of 
	
}
