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

</head>
<body>

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
							<td>${mvo.birthday}</td>
					    </tr>
					    <c:set var="cnt" value="${status.count}" />
					</c:forEach>					
				 </c:if>
					
				 <c:if test="${empty memberList}">
					<td colspan="8">조회하려는 주소지 ${addrSearch}의 데이터는 존재하지 않습니다.</td> 
				 </c:if>
				 
				 <tr>
				 	<td class="total" colspan="5">검색된 회원수</td>
				 	<td class="total" colspan="3">${cnt}명</td>
				 </tr>
			</tbody>
		</table>
	</div>

</body>
</html>