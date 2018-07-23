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
����� �������� ��û  ==> DispatcherServlet ==> @Controller Ŭ���� <==>> Service��(�ٽɾ���������, business logic��) <==>> Model��(DAO, DTO) <==>> myBatis <==>> DB(����Ŭ)           
(http://...  *.action)                                  |
         ��                                              |
         |                                              ��
         |                                           View��(.jsp)
         -----------------------------------------------| 
                                                         
���� ��ü�� �����ͺ��̽� �۾��� �������� �ϴ� ��ü�� �ƴ϶� 
�ش����(��: �Խ���)���� �ʿ�� �ϴ� �۾��� ���� �� �����ϴ� ��ü�̴�.
���⼭ ������� ���� ������ �ʿ��� �۾��� �帧�� ����ϴ� ������(��, �޼ҵ� ȣ����)
���� ���� ���α׷����� Model �ܿ��� ����صд�.(��, Model �ܿ��� �޼ҵ带 �ۼ��Ѵ�.)
�� ���� ��ü�� ���� ������(�����Ͻ� ������)�̶�� �θ���, 
���� �ڵ��� ���� @Controller ���� ��û���� ���� Model ������ �Ѱ��ִ� ������ �Ѵ�. 
*/

@Controller
public class MyBatisTestController {

	// ������ü����(DI : Dependency Injection)
	@Autowired
	private MyBatisTestService service;
	
	
	@RequestMapping(value="/mybatistest/mybatisTest1.action", method={RequestMethod.GET})     
	public String mybatisTest1(HttpServletRequest req) {
		
		// DB���� �޾ƿ��� ����(��ȯ)���� ����,
		// DB�� �־��� �Ķ����(����)���� ���� SQL ���� ����
		
		int n = service.mbtest1();
		
		String msg = "";
		
		if(n > 0) {
			msg = "������ �Է¼���!!";
		}
		else {
			msg = "������ �Է½���!!";
		}
		
		req.setAttribute("msg", msg);
		
		return "mybatistTest1";
		
	}// end of mybatisTest1()--------------------
	
	
	
	@RequestMapping(value="/mybatistest/mybatisTest2.action", method={RequestMethod.GET})     
	public String mybatisTest2(HttpServletRequest req) {
		
		// �⺻�� primitive type(int, double, String) �� ����(�Ķ����)������ 
		// �Ѱܼ� SQL ������ �����Ѵ�.
		
		String name = "����ȭ";
		
		int n = service.mbtest2(name);
		
		String msg = "";
		
		if(n > 0) {
			msg = n + "���� ������ ���漺��!!";
		}
		else {
			msg = "������ �������!!";
		}
		
		req.setAttribute("msg", msg);
		
		return "mybatisTest2";
		
	}// end of mybatisTest2()--------------------	

	
	
	// form ������ ����� ��κ� GET ����̴�.
	// ������, ������ �߰��ϴ� form ������ ����� POST ����̴�. 
	@RequestMapping(value="/mybatistest/mybatisTest3.action", method={RequestMethod.GET})     
	public String mybatisTest3() {
		
		// �۾��� form �������� ������ �Ѵ�.
		return "register/mybatisTest3AddForm";
	//   /WEB-INF/views/register/mybatisTest3AddForm.jsp
	}// end of mybatisTest3()------------------------
	
	
	@RequestMapping(value="/mybatistest/mybatisTest3End.action", method={RequestMethod.POST})     
	public String mybatisTest3End(HttpServletRequest req) {
		
		// 1. form ���� �Ѿ�� �� �ޱ�
		String name = req.getParameter("name");
		String email = req.getParameter("email");
		String tel = req.getParameter("tel");
		String addr = req.getParameter("addr");
		
		// 2. DTO(VO)�� �־��ش�.
		MyBatisTestVO vo = new MyBatisTestVO();
		vo.setName(name);
		vo.setEmail(email);
		vo.setTel(tel);
		vo.setAddr(addr);
		
		// 3. Service ������ ������ DTO(VO)�� �ѱ��.
		int n = service.mbtest3(vo);
		
		String msg = "";
		
		if(n>0) {
			msg = "ȸ������ ����!!";
		}
		else {
			msg = "ȸ������ ����!!";
		}
		
		req.setAttribute("msg", msg);
		
		return "register/mybatisTest3AddEnd";
		
	}// end of mybatisTest3End()---------------------
	
	
	@RequestMapping(value="/mybatistest/mybatisTest4.action", method={RequestMethod.GET})     
	public String mybatisTest4() {
		
		// �۾��� form �������� ������ �Ѵ�.
		return "register/mybatisTest4AddForm";
	//   /WEB-INF/views/register/mybatisTest4AddForm.jsp
	}// end of mybatisTest4()------------------------
	
	
	@RequestMapping(value="/mybatistest/mybatisTest4End.action", method={RequestMethod.POST})     
	public String mybatisTest4End(MemberVO mvo, HttpServletRequest req) {
		
		// **** form ���� �Ѿ���� name �� ���� 
		//      DB �� �÷���� DTO(VO)�� get�� set������ ������ �޼ҵ��(ù���ڴ� �빮��)��
		//      ������ ��� ��ó�� �Ķ���͸� MemberVO mvo �� ���� �־��ֱ⸸ �ϸ�
		//      form�� �Էµ� ������ �ڵ�������  MemberVO mvo �� �ԷµǾ����Ƿ�  		
		
		// 1. form ���� �Ѿ�� �� �ޱ����Ͽ� 
		//    ����Ͽ��� req.getParameter("name"); �̷��� �۾��� 
		//    �ʿ����.
				
		// 2. DTO(VO)�� �־��ִ� �۾��� �ʿ����.
		
		
		// 3. Service ������ ������ DTO(VO)�� �ѱ��.
		int n = service.mbtest4(mvo);
		
		String msg = "";
		
		if(n>0) {
			msg = "ȸ������ ����!!";
		}
		else {
			msg = "ȸ������ ����!!";
		}
		
		req.setAttribute("msg", msg);
		
		return "register/mybatisTest4AddEnd";
		
	}// end of mybatisTest4End()---------------------	


	@RequestMapping(value="/mybatistest/mybatisTest5.action", method={RequestMethod.GET})     
	public String mybatisTest5() {
		
		// �۾��� form �������� ������ �Ѵ�.
		return "register/mybatisTest5AddForm";
	//   /WEB-INF/views/register/mybatisTest5AddForm.jsp
	}// end of mybatisTest5()------------------------
	
	
	@RequestMapping(value="/mybatistest/mybatisTest5End.action", method={RequestMethod.POST})     
	public String mybatisTest5End(HttpServletRequest req) {
		
		// 1. form ���� �Ѿ�� �� �ޱ�
		String name = req.getParameter("name");
		String email = req.getParameter("email");
		String tel = req.getParameter("tel");
		String addr = req.getParameter("addr");
		
		// 2. HashMap�� �־��ش�.
		HashMap<String,String> map = new HashMap<String,String>();
		map.put("name", name);
		map.put("email", email);
		map.put("tel", tel);
		map.put("addr", addr);
				
		// 3. Service ������ ������ HashMap�� �ѱ��.
		int n = service.mbtest5(map);
		
		String msg = "";
		
		if(n>0) {
			msg = "ȸ������ ����!!";
		}
		else {
			msg = "ȸ������ ����!!";
		}
		
		req.setAttribute("msg", msg);
		
		return "register/mybatisTest5AddEnd";
		
	}// end of mybatisTest5End()---------------------	
	

	@RequestMapping(value="/mybatistest/mybatisTest6.action", method={RequestMethod.GET})     
	public String mybatisTest6() {
		
		// �۾��� form �������� ������ �Ѵ�.
		return "register/mybatisTest6AddForm";
	//   /WEB-INF/views/register/mybatisTest6AddForm.jsp
	}// end of mybatisTest6()------------------------
	
	
	@RequestMapping(value="/mybatistest/mybatisTest6End.action", method={RequestMethod.POST})     
	public String mybatisTest6End(MemberVO mvo, HttpServletRequest req) {
		
		// 1. HashMap�� �־��ش�.
		HashMap<String, MemberVO> map = new HashMap<String, MemberVO>();
		map.put("mvo", mvo);
						
		// 2. Service ������ ������ HashMap�� �ѱ��.
		int n = service.mbtest6(map);
		
		String msg = "";
		
		if(n>0) {
			msg = "ȸ������ ����!!";
		}
		else {
			msg = "ȸ������ ����!!";
		}
		
		req.setAttribute("msg", msg);
		
		return "register/mybatisTest6AddEnd";
		
	}// end of mybatisTest6End()---------------------
	
	
	@RequestMapping(value="/mybatistest/mybatisTest7.action", method={RequestMethod.GET})     
	public String mybatisTest7() {
		
		// �˻����ǿ� �´� �����͸� ã������ Search �ϴ� form �������� ����.
		return "search/mybatisTest7SearchForm";
	//   /WEB-INF/views/search/mybatisTest7SearchForm.jsp
	}// end of mybatisTest7()------------------------
	
	
	@RequestMapping(value="/mybatistest/mybatisTest7End.action", method={RequestMethod.GET})     
	public String mybatisTest7End(HttpServletRequest req) {
	
		// 1. �˻�� �޾ƿ´�.
		String no = req.getParameter("no");
		
		// 2. Service ������ �˻�� �ѱ��.
		String name = service.mbtest7(no);
		
		String result = null;
		
		if(name != null) {
			result = name;
		}
		else {
			result = "�˻��Ͻ÷��� " + no + "���� ��ġ�ϴ� �����Ͱ� �����ϴ�."; 
		}
		
		req.setAttribute("result", result);
		
		return "search/mybatisTest7SearchEnd";
		
	}// end of mybatisTest7End()--------------------------
	
	
	@RequestMapping(value="/mybatistest/mybatisTest8.action", method={RequestMethod.GET})     
	public String mybatisTest8() {
		
		// �˻����ǿ� �´� �����͸� ã������ Search �ϴ� form �������� ����.
		return "search/mybatisTest8SearchForm";
	//   /WEB-INF/views/search/mybatisTest8SearchForm.jsp
	}// end of mybatisTest8()------------------------
	
	
	@RequestMapping(value="/mybatistest/mybatisTest8End.action", method={RequestMethod.GET})     
	public String mybatisTest8End(HttpServletRequest req) {
	
		// 1. �˻�� �޾ƿ´�.
		String no = req.getParameter("no");
		
		// 2. Service ������ �˻�� �ѱ��.
		MemberVO mvo = service.mbtest8(no);
		
		req.setAttribute("mvo", mvo);
		req.setAttribute("no", no);
		
		return "search/mybatisTest8SearchEnd";
		
	}// end of mybatisTest8End()--------------------------
	
	
	@RequestMapping(value="/mybatistest/mybatisTest9.action", method={RequestMethod.GET})     
	public String mybatisTest9() {
		
		// �˻����ǿ� �´� �����͸� ã������ Search �ϴ� form �������� ����.
		return "search/mybatisTest9SearchForm";
	//   /WEB-INF/views/search/mybatisTest9SearchForm.jsp
	}// end of mybatisTest9()------------------------
	
	
	@RequestMapping(value="/mybatistest/mybatisTest9End.action", method={RequestMethod.GET})     
	public String mybatisTest9End(HttpServletRequest req) {
	
		// 1. �˻�� �޾ƿ´�.
		String addrSearch = req.getParameter("addrSearch");
		
		// 2. Service ������ �˻�� �ѱ��.
		List<MemberVO> memberList = service.mbtest9(addrSearch);
		
		req.setAttribute("memberList", memberList);
		req.setAttribute("addrSearch", addrSearch);
		
		return "search/mybatisTest9SearchEnd";
		
	}// end of mybatisTest9End()--------------------------
	
	
	@RequestMapping(value="/mybatistest/mybatisTest10.action", method={RequestMethod.GET})      
	public String mybatisTest10() {
		
		/*
		    DB�� �÷���� �ڹٺ� ������Ƽ��(VO�� get�޼ҵ��)�� �ٸ� ���
		    resultMap�� ����ؼ� �ذ��Ѵ�. 
		 */
		
		// �˻����ǿ� �´� �����͸� ã������ Search �ϴ� form �������� ����.
		return "search/mybatisTest10SearchForm";
	//   /WEB-INF/views/search/mybatisTest10SearchForm.jsp
	}// end of mybatisTest10()------------------------	
		
	
	@RequestMapping(value="/mybatistest/mybatisTest10End.action", method={RequestMethod.GET})     
	public String mybatisTest10End(HttpServletRequest req) {
	
		/*
		    �˻����� �̸��� �Է����� �ʰ� "ã��" ��ư�� Ŭ���ϸ�
		    ��� ȸ������ ��µǾ�����,
		    �̸��� �־��־ �˻��� �ش��̸��� �����ϸ� ȸ������ ������
		    �ش��̸��� ������ �������� �ʽ��ϴ� ��� �������� �Ѵ�.
		    �׷��� VO�� ����� ���ε� VO�� ������Ƽ��(VO�� get�޼ҵ��)��
		  DB�� Table �÷����� �ٸ� ����̴�.
		*/
		
		// 1. �˻�� �޾ƿ´�.
		String searchName = req.getParameter("searchName");
		HashMap<String,String> map = new HashMap<String,String>();
		map.put("searchNameKey", searchName);
		
		// 2. Service ������ �˻�� �ѱ��.
		List<MemberVO2> memberList = service.mbtest10(map);
		
		req.setAttribute("memberList", memberList);
		req.setAttribute("searchName", searchName);
		
		return "search/mybatisTest10SearchEnd";
		
	}// end of mybatisTest10End()--------------------------
	
	
	@RequestMapping(value="/mybatistest/mybatisTest11.action", method={RequestMethod.GET})      
	public String mybatisTest11() {
		
		/*
		    DB�� �÷���� �ڹٺ� ������Ƽ��(VO�� get�޼ҵ��)�� �ٸ� ���
		    SQL�������� alias(��Ī)�� ����ϸ� �ȴ�.
		 */
		
		// �˻����ǿ� �´� �����͸� ã������ Search �ϴ� form �������� ����.
		return "search/mybatisTest11SearchForm";
	//   /WEB-INF/views/search/mybatisTest11SearchForm.jsp
	}// end of mybatisTest11()------------------------	
		
	
	@RequestMapping(value="/mybatistest/mybatisTest11End.action", method={RequestMethod.GET})     
	public String mybatisTest11End(HttpServletRequest req) {
	
		/*
		    �˻����� �̸��� �Է����� �ʰ� "ã��" ��ư�� Ŭ���ϸ�
		    ��� ȸ������ ��µǾ�����,
		    �̸��� �־��־ �˻��� �ش��̸��� �����ϸ� ȸ������ ������
		    �ش��̸��� ������ �������� �ʽ��ϴ� ��� �������� �Ѵ�.
		    �׷��� VO�� ����� ���ε� VO�� ������Ƽ��(VO�� get�޼ҵ��)��
		  DB�� Table �÷����� �ٸ� ����̴�.
		*/
		
		// 1. �˻�� �޾ƿ´�.
		String searchName = req.getParameter("searchName");
		HashMap<String,String> map = new HashMap<String,String>();
		map.put("searchNameKey", searchName);
		
		// 2. Service ������ �˻�� �ѱ��.
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

	

	// ===> SQL ���� ����� �޾ƿö� VO�� ������� �ʰ�
	//      HashMap�� ����ϴ� ����̴�. <====   
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
	
	
	// ==== testdb.xml ���Ͽ� foreach �� ����ϴ� ���� ==== //
	@RequestMapping(value="/mybatistest/mybatisTest15.action", method={RequestMethod.GET})     
	public String mybatisTest15(HttpServletRequest req) {
		
		List<Integer> deptnoList = service.mbtest13_deptno();
		req.setAttribute("deptnoList", deptnoList);
		
		return "search/mybatisTest15SearchForm";
		
	}// end of mybatisTest15(HttpServletRequest req)--------------------------
	
	
	// ==== testdb.xml ���Ͽ� foreach �� ����ϴ� ���� ==== //
	@RequestMapping(value="/mybatistest/mybatisTest15JSON.action", method={RequestMethod.GET})     
	public String mybatisTest15JSON(HttpServletRequest req) {
		
		String str_deptnoArr = req.getParameter("str_deptnoArr");
		
		System.out.println("===> Ȯ�ο� str_deptnoArr : " + str_deptnoArr);
		// ===> Ȯ�ο� str_deptnoArr : -9999,30,50,80
		
		String[] deptnoArr = str_deptnoArr.split(",");
		
		for(int i=0; i<deptnoArr.length; i++) {
			System.out.println("===> Ȯ�ο� deptnoArr["+i+"] ==> " + deptnoArr[i]);
		}
		/*
		    ===> Ȯ�ο� deptnoArr[0] ==> -9999
			===> Ȯ�ο� deptnoArr[1] ==> 30
			===> Ȯ�ο� deptnoArr[2] ==> 50
			===> Ȯ�ο� deptnoArr[3] ==> 80 
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
		
		System.out.println("===> Ȯ�ο� str_jsonArr : " + str_jsonArr);
		// ===> Ȯ�ο� str_jsonArr : [{"ename":"Charles Johnson","gender":"��","department_id":"80","department_name":"Sales","employee_id":"179","jubun":"8712111234567","yearpay":"81840","age":"32"},{"ename":"Timothy Gates","gender":"��","department_id":"50","department_name":"Shipping","employee_id":"190","jubun":"8510161234567","yearpay":"34800","age":"34"},{"ename":"Shanta Vollman","gender":"��","department_id":"50","department_name":"Shipping","employee_id":"123","jubun":"8010131234567","yearpay":"78000","age":"39"},{"ename":"Kevin Mourgos","gender":"��","department_id":"50","department_name":"Shipping","employee_id":"124","jubun":"8110191234567","yearpay":"69600","age":"38"},{"ename":"Laura Bissot","gender":"��","department_id":"50","department_name":"Shipping","employee_id":"129","jubun":"8507251234567","yearpay":"39600","age":"34"},{"ename":"Louise Doran","gender":"��","department_id":"80","department_name":"Sales","employee_id":"160","jubun":"8710131234567","yearpay":"117000","age":"32"},{"ename":"Nandita Sarchand","gender":"��","department_id":"50","department_name":"Shipping","employee_id":"184","jubun":"8512131234567","yearpay":"50400","age":"34"}]
		
		req.setAttribute("str_jsonArr", str_jsonArr);
		
		return "search/mybatisTest15SearchJSON";
		
	}// end of mybatisTest15JSON(HttpServletRequest req)--------------------------
	
	
	// ====> ��Ʈ�׸���(AJAX) <==== // 
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





