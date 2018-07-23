<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<script type="text/javascript">

	function goSearch() {
		var frm = document.searchFrm;
		frm.method = "GET";
		frm.action = "<%= request.getContextPath() %>/mybatistest/mybatisTest9End.action"; 
		frm.submit();
	}

</script>	

</head>
<body>

	<div align="center">
		<h2>주소정보를 입력받아 그 주소지에 거주하는 회원을 조회해주는 페이지</h2>
		<h2>(/mybatistest/mybatisTest9.action)</h2>
		
		<form name="searchFrm">
			주소검색 : <input type="text" name="addrSearch" />
			<br/><br/>
			<button type="button" onClick="goSearch();">찾기</button>
		</form>
	</div>

</body>
</html>
