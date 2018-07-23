package com.test.model;

import java.util.HashMap;
import java.util.List;

public interface InterMyBatisTestDAO {

	int mbtest1();
	
	int mbtest2(String name);
	
	int mbtest3(MyBatisTestVO vo);
	
	int mbtest4(MemberVO mvo);
	
	int mbtest5(HashMap<String,String> map);
	
	int mbtest6(HashMap<String, MemberVO> map);
	
	String mbtest7(String no);
	
	MemberVO mbtest8(String no);
	
	List<MemberVO> mbtest9(String addrSearch);

	List<MemberVO2> mbtest10(HashMap<String, String> map);
	
	List<MemberVO2> mbtest11(HashMap<String, String> map);
	
	List<MemberVO> mbtest12(HashMap<String, String> map);
	
	List<Integer> mbtest13_deptno();
	
	List<HashMap<String, String>> mbtest13(HashMap<String, String> map);
	
	List<MemberVO> mbtest14(String addrSearch);
	
	List<HashMap<String, String>> mbtest15(HashMap<String, Object> map); 
	
	List<HashMap<String, String>> mbtest16_gender();
	
	List<HashMap<String, String>> mbtest16_ageline();
	
	List<HashMap<String, String>> mbtest16_deptno();
}
