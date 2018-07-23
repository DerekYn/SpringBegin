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
	        
	select.my_select {height: 25px;}        
</style>

<script type="text/javascript" src="<%= request.getContextPath() %>/resources/js/jquery-3.3.1.min.js"></script>

<script type="text/javascript">

    $(document).ready(function(){
    	
		$("#display").hide();
    	
    	$("#btnSearch").click(function(){
    		
    		var department_id_Arr = document.getElementsByName("department_id"); 
    		var gender = document.getElementById("gender").value;
    		var ageline = document.getElementById("ageline").value;
    		
    		////////////////////////////////////////////////////////
    		for(var i=0; i<department_id_Arr.length; i++) {
    			if(department_id_Arr[i].checked == true)
    			   console.log("부서번호 : " + department_id_Arr[i].value);
    		} 
    		
    		console.log("성별 : " + gender);
    		console.log("연령대 : " + ageline);
    		///////////////////////////////////////////////////////////
    		
    		var deptnoArr = new Array();
    		
    		for(var i=0; i<department_id_Arr.length; i++) {
    			if(department_id_Arr[i].checked == true)
    				deptnoArr.push(department_id_Arr[i].value);   
    		}
    		
    		/////////////////////////////////////////////////
    		for(var i=0; i<deptnoArr.length; i++) {
    			console.log("deptnoArr["+i+"] : " + deptnoArr[i]);
    		}
    		/////////////////////////////////////////////////
    		
    		var str_deptnoArr = deptnoArr.join();
    		console.log("str_deptnoArr => " + str_deptnoArr);
    		// -9999,30,40,50,80
    		
    		callAjax(str_deptnoArr, gender, ageline);
    	});// end of $("#btnSearch").click()----------------
    	
    }); // end of $(document).ready()---------------------

    
	function callAjax(str_deptnoArr, gender, ageline) {
    	
    	var data_form = {"str_deptnoArr":str_deptnoArr, 
    			         "gender":gender,
    			         "ageline":ageline};
    	
    	$.ajax({
    		url: "mybatisTest15JSON.action",
    		type: "GET",
    		data: data_form,
    		dataType: "JSON",
    		success: function(json){
    			$("#result").empty();
    			
    			if(json.length > 0) {
    				$.each(json, function(entryIndex, entry){
    					var html = "";
    					html += "<tr>";
    					html +=   "<td>"+(entryIndex+1)+"</td>";
    					html +=   "<td>"+entry.department_id+"</td>";
    					html +=   "<td>"+entry.deparmentt_name+"</td>";
    					html +=   "<td>"+entry.employee_id+"</td>";
    					html +=   "<td>"+entry.ename+"</td>";
    					html +=   "<td>"+entry.jubun+"</td>";
    					html +=   "<td>"+entry.gender+"</td>";
    					html +=   "<td>"+entry.age+"</td>";
    					html +=   "<td>"+entry.yearpay+"</td>";
    					html += "</tr>";
    					
    					$("#result").append(html);
    				});
    			}
    			else {
    				$("#result").html("<tr><td colspan='8' style='color:red;'>검색된 데이터가 없습니다.</td></tr>"); 
    			}
    			
    			$("#display").show();
    		},
    		error: function(request, status, error){
				alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
			}
    		
    	});// end of $.ajax()-----------------------
    	
    }// end of function callAjax(addrSearch)------------
    
    
    function searchKeep() {
    	$("#department_id").val("${department_id}");
    	$("#gender").val("${gender}");
    }// end of function searchKeep()---------------------
    
    

</script>	

</head>
<body>

	<div align="center">
		<h2>우리회사 사원명단</h2>
		<br/>
		
		<form name="searchFrm">
			<c:forEach var="deptno" items="${deptnoList}">
				<c:if test="${deptno < 0}">
				    <input type="checkbox" name="department_id" id="department_id_${deptno}" value="${deptno}" /><label for="department_id_${deptno}">부서없음</label>&nbsp; 
				</c:if>
			
			    <c:if test="${deptno > 0}">
				    <input type="checkbox" name="department_id" id="department_id_${deptno}" value="${deptno}" /><label for="department_id_${deptno}">${deptno}번부서</label>&nbsp; 
				</c:if>
			</c:forEach>
			
			<br/><br/>
			
			<select name="gender" id="gender" class="my_select">
				<option value="전체">전체</option>
				<option value="남">남</option>
				<option value="여">여</option>
			</select>
						
			<select name="ageline" id="ageline" class="my_select">
				<option value="">전체</option>
				<option value="0">10대미만</option>
				<option value="10">10대</option>
				<option value="20">20대</option>
				<option value="30">30대</option>
				<option value="40">40대</option>
				<option value="50">50대</option>
				<option value="60">60대</option>
			</select>
			
			<button type="button" id="btnSearch">검색</button>&nbsp;&nbsp;
		</form>
	</div>
	
	<div id="display" align="center" style="margin-top: 50px;">
		<table style="width: 900px;">
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
			
			<tbody id="result"></tbody>
		</table>
	</div>
		
	<c:if test="${empList != null}">
		<div align="center" style="margin-top: 50px;">
		  <table style="width: 900px;"> 
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
