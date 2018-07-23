package com.test.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.reflection.SystemMetaObject;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.test.model.MemberVO;
import com.test.model.MemberVO2;
import com.test.model.MyBatisTestVO;
import com.test.service.MyBatisTestService;

/*
사용자 웹브라우저 요청  ==> DispatcherServlet ==> @Controller 클래스 <==>> Service단(핵심업무로직단, business logic단) <==>> Model단(DAO, DTO) <==>> myBatis <==>> DB(오라클)           
(http://...  *.action)                                  |
         ↑                                              |
         |                                              ↓
         |                                           View단(.jsp)
         -----------------------------------------------| 
                                                         
서비스 객체는 데이터베이스 작업만 전문으로 하는 객체가 아니라 
해당업무(예: 게시판)에서 필요로 하는 작업을 전부 총 관리하는 객체이다.
여기서 관리라는 것은 업무에 필요한 작업의 흐름만 기술하는 것으로(즉, 메소드 호출임)
실제 업무 프로그래밍은 Model 단에서 기술해둔다.(즉, Model 단에서 메소드를 작성한다.)
이 서비스 객체는 업무 로직단(비지니스 로직단)이라고 부르며, 
실제 코딩은 없고 @Controller 에서 요청받은 것을 Model 단으로 넘겨주는 역할을 한다. 
*/

@Controller
public class MyBatisTestController {

	// 의존객체주입(DI : Dependency Injection)
	@Autowired
	private MyBatisTestService service;
	
	
	@RequestMapping(value="/mybatistest/mybatisTest1.action", method={RequestMethod.GET})     
	public String mybatisTest1(HttpServletRequest req) {
		
		// DB에서 받아오는 리턴(반환)값도 없고,
		// DB에 넣어줄 파라미터(인자)값도 없는 SQL 구문 실행
		
		int n = service.mbtest1();
		
		String msg = "";
		
		if(n > 0) {
			msg = "데이터 입력성공!!";
		}
		else {
			msg = "데이터 입력실패!!";
		}
		
		req.setAttribute("msg", msg);
		
		return "mybatistTest1";
		
	}// end of mybatisTest1()--------------------
	
	
	
	@RequestMapping(value="/mybatistest/mybatisTest2.action", method={RequestMethod.GET})     
	public String mybatisTest2(HttpServletRequest req) {
		
		// 기본형 primitive type(int, double, String) 을 인자(파라미터)값으로 
		// 넘겨서 SQL 구문을 실행한다.
		
		String name = "엄정화";
		
		int n = service.mbtest2(name);
		
		String msg = "";
		
		if(n > 0) {
			msg = n + "개의 데이터 변경성공!!";
		}
		else {
			msg = "데이터 변경실패!!";
		}
		
		req.setAttribute("msg", msg);
		
		return "mybatisTest2";
		
	}// end of mybatisTest2()--------------------	

	
	
	// form 페이지 띄우기는 대부분 GET 방식이다.
	// 하지만, 파일을 추가하는 form 페이지 띄우기는 POST 방식이다. 
	@RequestMapping(value="/mybatistest/mybatisTest3.action", method={RequestMethod.GET})     
	public String mybatisTest3() {
		
		// 글쓰기 form 페이지를 띄우려고 한다.
		return "register/mybatisTest3AddForm";
	//   /WEB-INF/views/register/mybatisTest3AddForm.jsp
	}// end of mybatisTest3()------------------------
	
	
	@RequestMapping(value="/mybatistest/mybatisTest3End.action", method={RequestMethod.POST})     
	public String mybatisTest3End(HttpServletRequest req) {
		
		// 1. form 에서 넘어온 값 받기
		String name = req.getParameter("name");
		String email = req.getParameter("email");
		String tel = req.getParameter("tel");
		String addr = req.getParameter("addr");
		
		// 2. DTO(VO)에 넣어준다.
		MyBatisTestVO vo = new MyBatisTestVO();
		vo.setName(name);
		vo.setEmail(email);
		vo.setTel(tel);
		vo.setAddr(addr);
		
		// 3. Service 단으로 생성된 DTO(VO)를 넘긴다.
		int n = service.mbtest3(vo);
		
		String msg = "";
		
		if(n>0) {
			msg = "회원가입 성공!!";
		}
		else {
			msg = "회원가입 실패!!";
		}
		
		req.setAttribute("msg", msg);
		
		return "register/mybatisTest3AddEnd";
		
	}// end of mybatisTest3End()---------------------
	
	
	@RequestMapping(value="/mybatistest/mybatisTest4.action", method={RequestMethod.GET})     
	public String mybatisTest4() {
		
		// 글쓰기 form 페이지를 띄우려고 한다.
		return "register/mybatisTest4AddForm";
	//   /WEB-INF/views/register/mybatisTest4AddForm.jsp
	}// end of mybatisTest4()------------------------
	
	
	@RequestMapping(value="/mybatistest/mybatisTest4End.action", method={RequestMethod.POST})     
	public String mybatisTest4End(MemberVO mvo, HttpServletRequest req) {
		
		// **** form 에서 넘어오는 name 의 값과 
		//      DB 의 컬럼명과 DTO(VO)의 get과 set다음에 나오는 메소드명(첫글자는 대문자)이
		//      동일할 경우 위처럼 파라미터명에 MemberVO mvo 와 같이 넣어주기만 하면
		//      form에 입력된 값들이 자동적으로  MemberVO mvo 에 입력되어지므로  		
		
		// 1. form 에서 넘어온 값 받기위하여 
		//    사용하였던 req.getParameter("name"); 이러한 작업이 
		//    필요없다.
				
		// 2. DTO(VO)에 넣어주는 작업도 필요없다.
		
		
		// 3. Service 단으로 생성된 DTO(VO)를 넘긴다.
		int n = service.mbtest4(mvo);
		
		String msg = "";
		
		if(n>0) {
			msg = "회원가입 성공!!";
		}
		else {
			msg = "회원가입 실패!!";
		}
		
		req.setAttribute("msg", msg);
		
		return "register/mybatisTest4AddEnd";
		
	}// end of mybatisTest4End()---------------------	


	@RequestMapping(value="/mybatistest/mybatisTest5.action", method={RequestMethod.GET})     
	public String mybatisTest5() {
		
		// 글쓰기 form 페이지를 띄우려고 한다.
		return "register/mybatisTest5AddForm";
	//   /WEB-INF/views/register/mybatisTest5AddForm.jsp
	}// end of mybatisTest5()------------------------
	
	
	@RequestMapping(value="/mybatistest/mybatisTest5End.action", method={RequestMethod.POST})     
	public String mybatisTest5End(HttpServletRequest req) {
		
		// 1. form 에서 넘어온 값 받기
		String name = req.getParameter("name");
		String email = req.getParameter("email");
		String tel = req.getParameter("tel");
		String addr = req.getParameter("addr");
		
		// 2. HashMap에 넣어준다.
		HashMap<String,String> map = new HashMap<String,String>();
		map.put("name", name);
		map.put("email", email);
		map.put("tel", tel);
		map.put("addr", addr);
				
		// 3. Service 단으로 생성된 HashMap을 넘긴다.
		int n = service.mbtest5(map);
		
		String msg = "";
		
		if(n>0) {
			msg = "회원가입 성공!!";
		}
		else {
			msg = "회원가입 실패!!";
		}
		
		req.setAttribute("msg", msg);
		
		return "register/mybatisTest5AddEnd";
		
	}// end of mybatisTest5End()---------------------	
	

	@RequestMapping(value="/mybatistest/mybatisTest6.action", method={RequestMethod.GET})     
	public String mybatisTest6() {
		
		// 글쓰기 form 페이지를 띄우려고 한다.
		return "register/mybatisTest6AddForm";
	//   /WEB-INF/views/register/mybatisTest6AddForm.jsp
	}// end of mybatisTest6()------------------------
	
	
	@RequestMapping(value="/mybatistest/mybatisTest6End.action", method={RequestMethod.POST})     
	public String mybatisTest6End(MemberVO mvo, HttpServletRequest req) {
		
		// 1. HashMap에 넣어준다.
		HashMap<String, MemberVO> map = new HashMap<String, MemberVO>();
		map.put("mvo", mvo);
						
		// 2. Service 단으로 생성된 HashMap을 넘긴다.
		int n = service.mbtest6(map);
		
		String msg = "";
		
		if(n>0) {
			msg = "회원가입 성공!!";
		}
		else {
			msg = "회원가입 실패!!";
		}
		
		req.setAttribute("msg", msg);
		
		return "register/mybatisTest6AddEnd";
		
	}// end of mybatisTest6End()---------------------
	
	
	@RequestMapping(value="/mybatistest/mybatisTest7.action", method={RequestMethod.GET})     
	public String mybatisTest7() {
		
		// 검색조건에 맞는 데이터를 찾기위해 Search 하는 form 페이지를 띄운다.
		return "search/mybatisTest7SearchForm";
	//   /WEB-INF/views/search/mybatisTest7SearchForm.jsp
	}// end of mybatisTest7()------------------------
	
	
	@RequestMapping(value="/mybatistest/mybatisTest7End.action", method={RequestMethod.GET})     
	public String mybatisTest7End(HttpServletRequest req) {
	
		// 1. 검색어를 받아온다.
		String no = req.getParameter("no");
		
		// 2. Service 단으로 검색어를 넘긴다.
		String name = service.mbtest7(no);
		
		String result = null;
		
		if(name != null) {
			result = name;
		}
		else {
			result = "검색하시려는 " + no + "번에 일치하는 데이터가 없습니다."; 
		}
		
		req.setAttribute("result", result);
		
		return "search/mybatisTest7SearchEnd";
		
	}// end of mybatisTest7End()--------------------------
	
	
	@RequestMapping(value="/mybatistest/mybatisTest8.action", method={RequestMethod.GET})     
	public String mybatisTest8() {
		
		// 검색조건에 맞는 데이터를 찾기위해 Search 하는 form 페이지를 띄운다.
		return "search/mybatisTest8SearchForm";
	//   /WEB-INF/views/search/mybatisTest8SearchForm.jsp
	}// end of mybatisTest8()------------------------
	
	
	@RequestMapping(value="/mybatistest/mybatisTest8End.action", method={RequestMethod.GET})     
	public String mybatisTest8End(HttpServletRequest req) {
	
		// 1. 검색어를 받아온다.
		String no = req.getParameter("no");
		
		// 2. Service 단으로 검색어를 넘긴다.
		MemberVO mvo = service.mbtest8(no);
		
		req.setAttribute("mvo", mvo);
		req.setAttribute("no", no);
		
		return "search/mybatisTest8SearchEnd";
		
	}// end of mybatisTest8End()--------------------------
	
	
	@RequestMapping(value="/mybatistest/mybatisTest9.action", method={RequestMethod.GET})     
	public String mybatisTest9() {
		
		// 검색조건에 맞는 데이터를 찾기위해 Search 하는 form 페이지를 띄운다.
		return "search/mybatisTest9SearchForm";
	//   /WEB-INF/views/search/mybatisTest9SearchForm.jsp
	}// end of mybatisTest9()------------------------
	
	
	@RequestMapping(value="/mybatistest/mybatisTest9End.action", method={RequestMethod.GET})     
	public String mybatisTest9End(HttpServletRequest req) {
	
		// 1. 검색어를 받아온다.
		String addrSearch = req.getParameter("addrSearch");
		
		// 2. Service 단으로 검색어를 넘긴다.
		List<MemberVO> memberList = service.mbtest9(addrSearch);
		
		req.setAttribute("memberList", memberList);
		req.setAttribute("addrSearch", addrSearch);
		
		return "search/mybatisTest9SearchEnd";
		
	}// end of mybatisTest9End()--------------------------
	
	
	@RequestMapping(value="/mybatistest/mybatisTest10.action", method={RequestMethod.GET})      
	public String mybatisTest10() {
		
		/*
		    DB의 컬럼명과 자바빈 프로퍼티명(VO의 get메소드명)이 다를 경우
		    resultMap을 사용해서 해결한다. 
		 */
		
		// 검색조건에 맞는 데이터를 찾기위해 Search 하는 form 페이지를 띄운다.
		return "search/mybatisTest10SearchForm";
	//   /WEB-INF/views/search/mybatisTest10SearchForm.jsp
	}// end of mybatisTest10()------------------------	
		
	
	@RequestMapping(value="/mybatistest/mybatisTest10End.action", method={RequestMethod.GET})     
	public String mybatisTest10End(HttpServletRequest req) {
	
		/*
		    검색란에 이름을 입력하지 않고 "찾기" 버튼을 클릭하면
		    모든 회원들이 출력되어지고,
		    이름을 넣어주어서 검색시 해당이름이 존재하면 회원들이 나오고
		    해당이름이 없으면 존재하지 않습니다 라고 나오도록 한다.
		    그런데 VO를 사용할 것인데 VO의 프로퍼티명(VO의 get메소드명)과
		  DB의 Table 컬럼명이 다른 경우이다.
		*/
		
		// 1. 검색어를 받아온다.
		String searchName = req.getParameter("searchName");
		HashMap<String,String> map = new HashMap<String,String>();
		map.put("searchNameKey", searchName);
		
		// 2. Service 단으로 검색어를 넘긴다.
		List<MemberVO2> memberList = service.mbtest10(map);
		
		req.setAttribute("memberList", memberList);
		req.setAttribute("searchName", searchName);
		
		return "search/mybatisTest10SearchEnd";
		
	}// end of mybatisTest10End()--------------------------
	
	
	@RequestMapping(value="/mybatistest/mybatisTest11.action", method={RequestMethod.GET})      
	public String mybatisTest11() {
		
		/*
		    DB의 컬럼명과 자바빈 프로퍼티명(VO의 get메소드명)이 다를 경우
		    SQL구문에서 alias(별칭)를 사용하면 된다.
		 */
		
		// 검색조건에 맞는 데이터를 찾기위해 Search 하는 form 페이지를 띄운다.
		return "search/mybatisTest11SearchForm";
	//   /WEB-INF/views/search/mybatisTest11SearchForm.jsp
	}// end of mybatisTest11()------------------------	
		
	
	@RequestMapping(value="/mybatistest/mybatisTest11End.action", method={RequestMethod.GET})     
	public String mybatisTest11End(HttpServletRequest req) {
	
		/*
		    검색란에 이름을 입력하지 않고 "찾기" 버튼을 클릭하면
		    모든 회원들이 출력되어지고,
		    이름을 넣어주어서 검색시 해당이름이 존재하면 회원들이 나오고
		    해당이름이 없으면 존재하지 않습니다 라고 나오도록 한다.
		    그런데 VO를 사용할 것인데 VO의 프로퍼티명(VO의 get메소드명)과
		  DB의 Table 컬럼명이 다른 경우이다.
		*/
		
		// 1. 검색어를 받아온다.
		String searchName = req.getParameter("searchName");
		HashMap<String,String> map = new HashMap<String,String>();
		map.put("searchNameKey", searchName);
		
		// 2. Service 단으로 검색어를 넘긴다.
		List<MemberVO2> memberList = service.mbtest11(map);
		
		req.setAttribute("memberList", memberList);
		req.setAttribute("searchName", searchName);
		
		return "search/mybatisTest11SearchEnd";
		
	}// end of mybatisTest11End()--------------------------	
	

	@RequestMapping(value="/mybatistest/mybatisTest12.action", method={RequestMethod.GET})     
	public String mybatisTest12(HttpServletRequest req) {
	
		String colName = req.getParameter("colName");
		String searchWord = req.getParameter("searchWord");
		String startday = req.getParameter("startday");
		String endday = req.getParameter("endday");
		
		if(searchWord != null && !searchWord.trim().isEmpty()) {
			
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("colName", colName);
			map.put("searchWord", searchWord);
			map.put("startday", startday);
			map.put("endday", endday);
			
			List<MemberVO> memberList = service.mbtest12(map);
			
			req.setAttribute("memberList", memberList);
			req.setAttribute("colName", colName);
			req.setAttribute("searchWord", searchWord);
			req.setAttribute("startday", startday);
			req.setAttribute("endday", endday);
		}
		
		return "search/mybatisTest12Search";
		
	}// end of mybatisTest12End()--------------------------	

	

	// ===> SQL 문의 결과를 받아올때 VO를 사용하지 않고
	//      HashMap을 사용하는 경우이다. <====   
	@RequestMapping(value="/mybatistest/mybatisTest13.action", method={RequestMethod.GET})     
	public String mybatisTest13(HttpServletRequest req) {
	
		List<Integer> deptnoList = service.mbtest13_deptno();
		req.setAttribute("deptnoList", deptnoList);
		
		String department_id = req.getParameter("department_id");
		String gender = req.getParameter("gender");
		
		
		HashMap<String,String> map = new HashMap<String,String>();
		map.put("DEPARTMENT_ID", department_id);
		map.put("GENDER", gender);
		
		List<HashMap<String,String>> empList = service.mbtest13(map);
		req.setAttribute("empList", empList);
		req.setAttribute("department_id", department_id);
		req.setAttribute("gender", gender);
		
		return "search/mybatisTest13Search";
		
	}// end of mybatisTest13End(HttpServletRequest req)--------------------------
	
	
	
	//  ===> ///////  AJAX  ////// <====       
	@RequestMapping(value="/mybatistest/mybatisTest14.action", method={RequestMethod.GET})     
	public String mybatisTest14() {
		
		return "search/mybatisTest14SearchForm";
		
	}// end of mybatisTest14()--------------------------	
	
	
	@RequestMapping(value="/mybatistest/mybatisTest14JSON.action", method={RequestMethod.GET})     
	public String mybatisTest14JSON(HttpServletRequest req) {
		
		String addrSearch = req.getParameter("addrSearch");
		
		JSONArray jsonArr = new JSONArray();  
		
		if(!addrSearch.trim().isEmpty()) {
			List<MemberVO> memberList = service.mbtest14(addrSearch);
			
			if(memberList != null) {
				for(MemberVO mvo : memberList) {
					JSONObject jsonObj = new JSONObject();
					jsonObj.put("no", mvo.getNo());
					jsonObj.put("name", mvo.getName());
					jsonObj.put("email", mvo.getEmail());
					jsonObj.put("tel", mvo.getTel());
					jsonObj.put("addr", mvo.getAddr());
					jsonObj.put("writeday", mvo.getWriteday());
					jsonObj.put("bday", mvo.getBirthday());
					
					jsonArr.put(jsonObj);
				}
			}
			
		}
		
		String str_jsonArr = jsonArr.toString();
		req.setAttribute("str_jsonArr", str_jsonArr);
		
		return "search/mybatisTest14SearchJSON";
		
	}// end of mybatisTest14JSON(HttpServletRequest req)--------------------------
	
	
	// ==== testdb.xml 파일에 foreach 를 사용하는 예제 ==== //
	@RequestMapping(value="/mybatistest/mybatisTest15.action", method={RequestMethod.GET})     
	public String mybatisTest15(HttpServletRequest req) {
		
		List<Integer> deptnoList = service.mbtest13_deptno();
		req.setAttribute("deptnoList", deptnoList);
		
		return "search/mybatisTest15SearchForm";
		
	}// end of mybatisTest15(HttpServletRequest req)--------------------------
	
	
	// ==== testdb.xml 파일에 foreach 를 사용하는 예제 ==== //
	@RequestMapping(value="/mybatistest/mybatisTest15JSON.action", method={RequestMethod.GET})     
	public String mybatisTest15JSON(HttpServletRequest req) {
		
		String str_deptnoArr = req.getParameter("str_deptnoArr");
		
		System.out.println("===> 확인용 str_deptnoArr : " + str_deptnoArr);
		// ===> 확인용 str_deptnoArr : -9999,30,50,80
		
		String[] deptnoArr = str_deptnoArr.split(",");
		
		for(int i=0; i<deptnoArr.length; i++) {
			System.out.println("===> 확인용 deptnoArr["+i+"] ==> " + deptnoArr[i]);
		}
		/*
		    ===> 확인용 deptnoArr[0] ==> -9999
			===> 확인용 deptnoArr[1] ==> 30
			===> 확인용 deptnoArr[2] ==> 50
			===> 확인용 deptnoArr[3] ==> 80 
		 */
		
		String gender = req.getParameter("gender");
		String ageline = req.getParameter("ageline");
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("deptnoArr", deptnoArr);
		map.put("gender", gender);
		map.put("ageline", ageline);
		
		JSONArray jsonArr = new JSONArray();  
		
		List<HashMap<String, String>> empList = service.mbtest15(map);
			
		if(empList != null) {
			for(HashMap<String,String> empmap : empList) {
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("department_id", empmap.get("DEPARTMENT_ID"));
				jsonObj.put("department_name", empmap.get("DEPARTMENT_NAME"));
				jsonObj.put("employee_id", empmap.get("EMPLOYEE_ID"));
				jsonObj.put("ename", empmap.get("ENAME"));
				jsonObj.put("jubun", empmap.get("JUBUN"));
				jsonObj.put("gender", empmap.get("GENDER"));
				jsonObj.put("age", empmap.get("AGE"));
				jsonObj.put("yearpay", empmap.get("YEARPAY"));
				
				jsonArr.put(jsonObj);
			}
		}
		
		String str_jsonArr = jsonArr.toString();
		
		System.out.println("===> 확인용 str_jsonArr : " + str_jsonArr);
		// ===> 확인용 str_jsonArr : [{"ename":"Charles Johnson","gender":"남","department_id":"80","department_name":"Sales","employee_id":"179","jubun":"8712111234567","yearpay":"81840","age":"32"},{"ename":"Timothy Gates","gender":"남","department_id":"50","department_name":"Shipping","employee_id":"190","jubun":"8510161234567","yearpay":"34800","age":"34"},{"ename":"Shanta Vollman","gender":"남","department_id":"50","department_name":"Shipping","employee_id":"123","jubun":"8010131234567","yearpay":"78000","age":"39"},{"ename":"Kevin Mourgos","gender":"남","department_id":"50","department_name":"Shipping","employee_id":"124","jubun":"8110191234567","yearpay":"69600","age":"38"},{"ename":"Laura Bissot","gender":"남","department_id":"50","department_name":"Shipping","employee_id":"129","jubun":"8507251234567","yearpay":"39600","age":"34"},{"ename":"Louise Doran","gender":"남","department_id":"80","department_name":"Sales","employee_id":"160","jubun":"8710131234567","yearpay":"117000","age":"32"},{"ename":"Nandita Sarchand","gender":"남","department_id":"50","department_name":"Shipping","employee_id":"184","jubun":"8512131234567","yearpay":"50400","age":"34"}]
		
		req.setAttribute("str_jsonArr", str_jsonArr);
		
		return "search/mybatisTest15SearchJSON";
		
	}// end of mybatisTest15JSON(HttpServletRequest req)--------------------------
	
	
	// ====> 차트그리기(AJAX) <==== // 
	@RequestMapping(value="/mybatistest/mybatisTest16.action", method={RequestMethod.GET})     
	public String mybatisTest16() {
				
		return "search/mybatisTest16SearchForm";
		
	}// end of mybatisTest16()--------------------------
	
	
	@RequestMapping(value="/mybatistest/mybatisTest16JSON_gender.action", method={RequestMethod.GET})     
	public String mybatisTest16JSON_gender(HttpServletRequest req) {
		
		List<HashMap<String, String>> genderList = service.mbtest16_gender();
		
		JSONArray jsonArr = new JSONArray();
		
		for(HashMap<String,String> map : genderList) {
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("gender", map.get("GENDER"));
			jsonObj.put("cnt", map.get("CNT"));
			jsonObj.put("percent", map.get("PERCENT"));
			
			jsonArr.put(jsonObj);
		}
		
		String str_jsonArr = jsonArr.toString();
		
		req.setAttribute("str_jsonArr", str_jsonArr);
		
		return "search/mybatisTest16SearchJSON_gender";
		
	}// end of mybatisTest16JSON_gender(HttpServletRequest req)--------------------------
	

	@RequestMapping(value="/mybatistest/mybatisTest16JSON_ageline.action", method={RequestMethod.GET})     
	public String mybatisTest16JSON_ageline(HttpServletRequest req) {
		
		List<HashMap<String, String>> agelineList = service.mbtest16_ageline();
		
		JSONArray jsonArr = new JSONArray();
		
		for(HashMap<String,String> map : agelineList) {
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("ageline", map.get("AGELINE"));
			jsonObj.put("cnt", map.get("CNT"));
			
			jsonArr.put(jsonObj);
		}
		
		String str_jsonArr = jsonArr.toString();
		
		req.setAttribute("str_jsonArr", str_jsonArr);
		
		return "search/mybatisTest16SearchJSON_ageline";
		
	}// end of mybatisTest16JSON_gender(HttpServletRequest req)--------------------------	
	
	
	@RequestMapping(value="/mybatistest/mybatisTest16JSON_deptno.action", method={RequestMethod.GET})     
	public String mybatisTest16JSON_deptno(HttpServletRequest req) {
		
		List<HashMap<String, String>> deptnoList = service.mbtest16_deptno();
		
		JSONArray jsonArr = new JSONArray();
		
		for(HashMap<String,String> map : deptnoList) {
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("department_name", map.get("DEPARTMENT_NAME"));
			jsonObj.put("cnt", map.get("CNT"));
			jsonObj.put("avgyearpay", map.get("AVGYEARPAY"));
			
			jsonArr.put(jsonObj);
		}
		
		String str_jsonArr = jsonArr.toString();
		
		req.setAttribute("str_jsonArr", str_jsonArr);
		
		return "search/mybatisTest16SearchJSON_deptno";
		
	}// end of mybatisTest16JSON_deptno(HttpServletRequest req)--------------------------		
	
	
	
}





