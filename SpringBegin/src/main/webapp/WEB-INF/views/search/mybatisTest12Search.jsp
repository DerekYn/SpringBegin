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
    	
    	if(${searchWord != null}) {
    		searchKeep();
    	}
    	
    });// end of $(document).ready()---------------------

    
    function searchKeep() {
    	$("#colName").val("${colName}");
    	$("#searchWord").val("${searchWord}");
    	$("#startday").val("${startday}");
    	$("#endday").val("${endday}");
    	
    }// end of function searchKeep()---------------------
    
    
	function goSearch() {
		
		var searchWord = document.getElementById("searchWord").value; 
		
		if(searchWord.trim() == "") {
			alert("검색어를 입력하세요!!");
			return;
		}
		else {
			var frm = document.searchFrm;
			frm.method = "GET";
			frm.action = "<%= request.getContextPath() %>/mybatistest/mybatisTest12.action"; 
			frm.submit();
		}
		
	}// end of function goSearch()------------------

</script>	

</head>
<body>

	<div align="center">
		<h2>회원명단</h2>
		<br/>
		
		<form name="searchFrm">
			<select name="colName" id="colName">
				<option value="name">성명</option>
				<option value="email">이메일</option>
				<option value="tel">전화</option>
				<option value="addr">주소</option>
			</select>
			<input type="text" name="searchWord" id="searchWord" size="20" />
			<br/><br/>
			시작일 : <input type="date" name="startday" id="startday" size="12" /> ~ 종료일 : <input type="date" name="endday" id="endday" size="12" /> 
			<br/><br/>
			<button type="button" onClick="goSearch();">검색</button>&nbsp;&nbsp;
			<button type="button" onClick="javascript:location.href='mybatisTest12.action'">초기화</button>
		</form>
	</div>
	
	<c:if test="${memberList != null}">
		<div align="center" style="margin-top: 50px;">
		  <table>
			<thead>
				<tr>
				    <th>일련번호</th>
					<th>회원번호</th>
					<th>성명</th>
					<th>이메일</th>
					<th>전화번호</th>
					<th>주소</th>
					<th>가입일자</th>
					<th>생일</th>
				</tr>
			</thead>
			
			<tbody>
			    <c:set var="cnt" value="0" />
			    
				<c:if test="${not empty memberList}">
					<c:forEach var="mvo" items="${memberList}" varStatus="status"> 
					    <tr>
					        <td>${status.count}</td>
						    <td>${mvo.no}</td>
							<td>${mvo.name}</td>
							<td>${mvo.email}</td>
							<td>${mvo.tel}</td>
							<td>${mvo.addr}</td>
							<td>${mvo.writeday}</td>
							<td>${mvo.bday}</td>
					    </tr>
					    <c:set var="cnt" value="${status.count}" />
					</c:forEach>					
				 </c:if>
					
				 <c:if test="${empty memberList}">
					<td colspan="8">검색어 ${searchWord}에 해당하는 회원은 존재하지 않습니다.</td> 
				 </c:if>
				 
				 <tr>
				 	<td class="total" colspan="5">검색된 회원수</td>
				 	<td class="total" colspan="3">${cnt}명</td>
				 </tr>
			</tbody>
		  </table>
		</div>
	</c:if>
	
</body>
</html>
