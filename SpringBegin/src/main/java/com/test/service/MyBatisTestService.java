package com.test.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.model.MemberVO;
import com.test.model.MemberVO2;
import com.test.model.MyBatisTestDAO;
import com.test.model.MyBatisTestVO;

@Service
public class MyBatisTestService {

	// 의존객체주입(DI : Dependency Injection)
	@Autowired
	private MyBatisTestDAO dao;

	
	public int mbtest1() {
		// DAO 객체의 특정 메소드를 호출하면 된다.
		int n = dao.mbtest1();
		
		return n;
	}


	public int mbtest2(String name) {
		int n = dao.mbtest2(name);
		return n;
	}


	public int mbtest3(MyBatisTestVO vo) {
		int n = dao.mbtest3(vo);
		return n;
	}


	public int mbtest4(MemberVO mvo) {
		int n = dao.mbtest4(mvo);
		return n;
	}


	public int mbtest5(HashMap<String, String> map) {
		int n = dao.mbtest5(map);
		return n;
	}


	public int mbtest6(HashMap<String, MemberVO> map) {
		int n = dao.mbtest6(map);
		return n;
	}


	public String mbtest7(String no) {
		String name = dao.mbtest7(no);
		return name;
	}


	public MemberVO mbtest8(String no) {
		MemberVO mvo = dao.mbtest8(no);
		return mvo;
	}


	public List<MemberVO> mbtest9(String addrSearch) {
		List<MemberVO> memberList = dao.mbtest9(addrSearch);
		return memberList;
	}


	public List<MemberVO2> mbtest10(HashMap<String,String> map) {
		List<MemberVO2> memberList = dao.mbtest10(map);
		return memberList;
	}


	public List<MemberVO2> mbtest11(HashMap<String, String> map) {
		List<MemberVO2> memberList = dao.mbtest11(map);
		return memberList;
	}


	public List<MemberVO> mbtest12(HashMap<String, String> map) {
		List<MemberVO> memberList = dao.mbtest12(map);
		return memberList;
	}


	public List<Integer> mbtest13_deptno() {
		List<Integer> deptnoList = dao.mbtest13_deptno();
		return deptnoList;
	}


	public List<HashMap<String, String>> mbtest13(HashMap<String, String> map) {
		List<HashMap<String, String>> empList = dao.mbtest13(map);
		return empList;
	}


	public List<MemberVO> mbtest14(String addrSearch) {
		List<MemberVO> memberList = dao.mbtest14(addrSearch);
		return memberList;
	}


	public List<HashMap<String, String>> mbtest15(HashMap<String, Object> map) {
		List<HashMap<String, String>> empList = dao.mbtest15(map); 
		return empList;
	}


	public List<HashMap<String, String>> mbtest16_gender() {
		List<HashMap<String, String>> genderList = dao.mbtest16_gender();
		return genderList;
	}


	public List<HashMap<String, String>> mbtest16_ageline() {
		List<HashMap<String, String>> agelineList = dao.mbtest16_ageline();
		return agelineList;
	}


	public List<HashMap<String, String>> mbtest16_deptno() {
		List<HashMap<String, String>> deptnoList = dao.mbtest16_deptno();
		return deptnoList;
	}
	
}
