package com.test.model;

import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MyBatisTestDAO implements InterMyBatisTestDAO {

	// 의존객체주입(DI : Dependency Injection)
	@Autowired
	private SqlSessionTemplate sqlsession;

	
	@Override
	public int mbtest1() {
		// JDBC설정, Connection.... 기타 등등의 작업이 필요 없다.
		int n = sqlsession.insert("testdb.mbtest1");
		return n;
	}


	@Override
	public int mbtest2(String name) {
		int n = sqlsession.update("testdb.mbtest2",name);
		return n;
	}


	@Override
	public int mbtest3(MyBatisTestVO vo) {
		int n = sqlsession.insert("testdb.mbtest3", vo);
		return n;
	}


	@Override
	public int mbtest4(MemberVO mvo) {
		int n = sqlsession.insert("testdb.mbtest4", mvo);
		return n;
	}


	@Override
	public int mbtest5(HashMap<String, String> map) {
		int n = sqlsession.insert("testdb.mbtest5", map);
		return n;
	}


	@Override
	public int mbtest6(HashMap<String, MemberVO> map) {
		int n = sqlsession.insert("testdb.mbtest6", map);
		return n;
	}


	@Override
	public String mbtest7(String no) {
		String name = sqlsession.selectOne("testdb.mbtest7", no);
		return name;
	}


	@Override
	public MemberVO mbtest8(String no) {
		MemberVO mvo = sqlsession.selectOne("testdb.mbtest8", no);
		return mvo;
	}


	@Override
	public List<MemberVO> mbtest9(String addrSearch) {
		List<MemberVO> memberList = sqlsession.selectList("testdb.mbtest9", addrSearch);
		return memberList;
	}


	@Override
	public List<MemberVO2> mbtest10(HashMap<String,String> map) {
		List<MemberVO2> memberList = sqlsession.selectList("testdb.mbtest10", map);
		return memberList;
	}
	
	
	@Override
	public List<MemberVO2> mbtest11(HashMap<String,String> map) {
		List<MemberVO2> memberList = sqlsession.selectList("testdb.mbtest11", map);
		return memberList;
	}


	@Override
	public List<MemberVO> mbtest12(HashMap<String, String> map) {
		List<MemberVO> memberList = sqlsession.selectList("testdb.mbtest12", map);
		return memberList;
	}


	@Override
	public List<Integer> mbtest13_deptno() {
		List<Integer> deptnoList = sqlsession.selectList("testdb.mbtest13_deptno");
		return deptnoList;
	}


	@Override
	public List<HashMap<String, String>> mbtest13(HashMap<String, String> map) {
		List<HashMap<String, String>> empList = sqlsession.selectList("testdb.mbtest13", map);
		return empList;
	}


	@Override
	public List<MemberVO> mbtest14(String addrSearch) {
		List<MemberVO> memberList = sqlsession.selectList("testdb.mbtest14", addrSearch);
		return memberList;
	}


	@Override
	public List<HashMap<String, String>> mbtest15(HashMap<String, Object> map) {
		List<HashMap<String, String>> empList = sqlsession.selectList("testdb.mbtest15", map);
		return empList;
	}


	@Override
	public List<HashMap<String, String>> mbtest16_gender() {
		List<HashMap<String, String>> genderList = sqlsession.selectList("testdb.mbtest16_gender"); 
		return genderList;
	}


	@Override
	public List<HashMap<String, String>> mbtest16_ageline() {
		List<HashMap<String, String>> agelineList = sqlsession.selectList("testdb.mbtest16_ageline"); 
		return agelineList;
	}


	@Override
	public List<HashMap<String, String>> mbtest16_deptno() {
		List<HashMap<String, String>> deptnoList = sqlsession.selectList("testdb.mbtest16_deptno"); 
		return deptnoList;
	}
	
	
	
}
