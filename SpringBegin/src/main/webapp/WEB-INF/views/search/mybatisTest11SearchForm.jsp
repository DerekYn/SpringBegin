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
		frm.action = "<%= request.getContextPath() %>/mybatistest/mybatisTest11End.action"; 
		frm.submit();
	}

</script>	

</head>
<body>

	<div align="center">
		<h2>성명을 입력받아 해당 회원을 조회해주는 페이지</h2>
		<h2>(/mybatistest/mybatisTest11.action)</h2>
		
		<form name="searchFrm">
			이름검색 : <input type="text" name="searchName" />
			<br/><br/>
			<button type="button" onClick="goSearch();">찾기</button>
		</form>
	</div>

</body>
</html>
