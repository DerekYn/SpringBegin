<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<style type="text/css">
	table {border: 1px solid gray;
	       border-collapse: collapse;
	      }
	
	th, td {border: 1px solid gray;}
	
	.total {background-color: #ffff99; 
	        font-weight: bold;
	        text-align: center;}
</style>

<script type="text/javascript" src="<%= request.getContextPath() %>/resources/js/jquery-3.3.1.min.js"></script>

<script type="text/javascript">

    $(document).ready(function(){
    	
    	if(${department_id != null && gender != null }) {
    		searchKeep();
    	}
    	
    });// end of $(document).ready()---------------------

    
    function searchKeep() {
    	$("#department_id").val("${department_id}");
    	$("#gender").val("${gender}");
    }// end of function searchKeep()---------------------
    
    
	function goSearch() {
		
		var frm = document.searchFrm;
		frm.method = "GET";
		frm.action = "<%= request.getContextPath() %>/mybatistest/mybatisTest13.action"; 
		frm.submit();
		
	}// end of function goSearch()------------------

</script>	

</head>
<body>

	<div align="center">
		<h2>우리회사 사원명단</h2>
		<br/>
		
		<form name="searchFrm">
			<select name="department_id" id="department_id">
				<option value="">부서전체</option>
				<c:forEach var="deptno" items="${deptnoList}">
					<c:if test="${deptno < 0}">
						<option value="${deptno}">부서없음</option>
					</c:if>
					
					<c:if test="${deptno > 0}">
						<option value="${deptno}">${deptno}</option>
					</c:if>
				</c:forEach>	
			</select>
			
			<select name="gender" id="gender">
				<option value="전체">전체</option>
				<option value="남">남</option>
				<option value="여">여</option>
			</select>
			
			<button type="button" onClick="goSearch();">검색</button>&nbsp;&nbsp;
			<button type="button" onClick="javascript:location.href='mybatisTest13.action'">초기화</button>
		</form>
	</div>
	
	<c:if test="${empList != null}">
		<div align="center" style="margin-top: 50px;">
		  <table>
			<thead>
				<tr>
				    <th>일련번호</th>
					<th>부서번호</th>
					<th>부서명</th>
					<th>사원번호</th>
					<th>사원명</th>
					<th>주민번호</th>
					<th>성별</th>
					<th>나이</th>
					<th>연봉</th>
				</tr>
			</thead>
			
			<tbody>
			    <c:set var="cnt" value="0" />
			    
				<c:if test="${not empty empList}">
					<c:forEach var="map" items="${empList}" varStatus="status"> 
					    <tr>
					        <td>${status.count}</td>
						    <td>${map.DEPARTMENT_ID}</td>
							<td>${map.DEPARTMENT_NAME}</td>
							<td>${map.EMPLOYEE_ID}</td>
							<td>${map.ENAME}</td>
							<td>${map.JUBUN}</td>
							<td>${map.GENDER}</td>
							<td>${map.AGE}</td>
							<td>${map.YEARPAY}</td>
					    </tr>
					    <c:set var="cnt" value="${status.count}" />
					</c:forEach>					
				 </c:if>
					
				 <c:if test="${empty empList}">
					<td colspan="9">검색어에 해당하는 사원은 존재하지 않습니다.</td> 
				 </c:if>
				 
				 <tr>
				 	<td class="total" colspan="5">검색된 회원수</td>
				 	<td class="total" colspan="4">${cnt}명</td>
				 </tr>
			</tbody>
		  </table>
		</div>
	</c:if>
	
</body>
</html>
