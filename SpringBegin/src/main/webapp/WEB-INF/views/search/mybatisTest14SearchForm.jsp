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

    	$("#display").hide();
    	
    	$("#btnSearch").click(function(){
    		var addrSearch = $("#addrSearch").val();
    		callAjax(addrSearch);
    	});
    	
    	$("#addrSearch").keydown(function(event){
    		if(event.keyCode == 13) {    // 엔터를 했을 경우
    			
    			var addrSearch = $(this).val();
    			callAjax(addrSearch);	
    			
    			return false;		// 엔터 눌렀을때 검색창에 검색어가 남아있게 하는 방법.
    			/*
    				JSP 페이지에서 Enter 이벤트 발생시
    				JSP 페이지에 input type의 text 박스가 오로지 하나인 경우에는
    				엔터를 치면 입력한 글자가 사라진다.
    				그래서 입력한 글자가 사라지지 않게 하려면
    			*/
    		}
    	});

    	
    	$("#btnClear").click(function(){
    		
    		$("#addrSearch").val("");
    		$("#addrSearch").focus();
    		$("#result").empty();
    		$("#display").hide();
    		
    	});
    	
    });// end of $(document).ready()---------------------
    
    
    function callAjax(addrSearch) {
    	
    	
    	var data_form = {"addrSearch":addrSearch};
    	
    	$.ajax({
    		url: "mybatisTest14JSON.action",
    		type: "GET",
    		data: data_form,
    		dataType: "JSON",
    		success: function(json) {
    			$("#result").empty();
    			
    			if(json.length > 0) {
    				$.each(json, function(entryIndex, entry){
    					var html = "";
    					html += "<tr>";
    					html +=    "<td>"+(entryIndex+1)+"</td>";
    					html +=    "<td>"+entry.no+"</td>";
    					html +=    "<td>"+entry.name+"</td>";
    					html +=    "<td>"+entry.email+"</td>";
    					html +=    "<td>"+entry.tel+"</td>";
    					html +=    "<td>"+entry.addr+"</td>";
    					html +=    "<td>"+entry.writeday+"</td>";
    					html +=    "<td>"+entry.birthday+"</td>";
    					html += "<tr>";
    					
    					$("#result").append(html);
    				});
    			}
    			else {
    				$("#result").html("<tr><td colspan='8' style='color:red;'>검색된 검색어가 없습니다.</td></tr>");
    			}
    			
    			$("#display").show();
    			
    		},
    		error: function(request, status, error){
 			   alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
 			}
    		
    	});
    	
    }// end of function callAjax(addrSearch)----------------------------------------------


</script>	

</head>
<body>

	<div align="center">
		<h2>검색어로 주소지를 입력받아 해당 주소지에 거주하는 회원정보를 보여주는 페이지</h2>
		<br/>
		
		<form name="searchFrm">
			주소지 : <input type="text" id="addrSearch" /><br/><br/>
			
			<button type="button" id="btnSearch" >검색</button>&nbsp;&nbsp;
			<button type="button" id="btnClear" >초기화</button>
		</form>
	</div>
	
	<div id="display" align="center" style="margin-top: 50px;">
		<table>
			<thead>
				<tr>
					<th>일련번호</th>
					<th>회원번호</th>
					<th>성명</th>
					<th>이메일</th>
					<th>전화</th>
					<th>주소</th>
					<th>가입일자</th>
				</tr>
			</thead>
			
			<tbody id="result"></tbody>
		</table>
	</div>
	
		
</body>
</html>
