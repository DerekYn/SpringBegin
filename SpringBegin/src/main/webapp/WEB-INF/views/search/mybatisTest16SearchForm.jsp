<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<style type="text/css">
	table {width: 100%;
		   margin-top: 10%;
		   border: 1px solid gray;
		   border-collapse: collapse;}
					 
	table th, table td {border: 1px solid gray;
						text-align: center;}
											
	table th {background-color: #ffffe6;}
</style>

<script src="https://code.highcharts.com/highcharts.js"></script>
<script src="https://code.highcharts.com/modules/exporting.js"></script>
<script src="https://code.highcharts.com/modules/export-data.js"></script>

<script type="text/javascript" src="<%= request.getContextPath() %>/resources/js/jquery-3.3.1.min.js" ></script>

<script type="text/javascript">

	$(document).ready(function(){
		
		$("#btnShow").click(function(){
			
			var chartType = $("#chartType").val();
			// 언제 getElementbyId 쓰는가. 
			
			callAjax(chartType);
			
		});// end of $("#btnShow").click(function(){});----------------------------
		
		
		$("#btnClear").click(function(){
			
			$("#chart").empty();
			// 차트가 들어갈 부분 div의 ID를 비운다.
			$("#tbl").empty();
			
		});// end of $("#btnClear").click(function(){})--------------------------
		
		
	});// end of $(document).ready(function(){});----------------------------------	
	
	
	
	function callAjax(chartType) {
		
		switch(chartType) {
		
			case "gender" :
				// alert("성별");
				
				$.ajax({
					url: "mybatisTest16JSON_gender.action",
					type: "GET",
					dataType: "JSON",
					success: function(json){
						// 애초에 json 은 배열타입으로 넘어와서 json 의 키값을 이용해 아래 for문에서 값을 추출해준다.
						$("#chart").empty();
						$("#tbl").empty();
						
						var resultArr = [];
						
						for(var i=0; i<json.length; i++) {
							var obj = {name: json[i].gender,
						               y: Number(json[i].percent)};
							resultArr.push(obj);	// 값 넣기
						}
						
						//////////////////////////////// 차트가 들어올 부분 ////////////////////////////////////
						Highcharts.chart('chart', {
						    chart: {
						        plotBackgroundColor: null,
						        plotBorderWidth: null,
						        plotShadow: false,
						        type: 'pie'
						    },
						    title: {
						        text: '우리회사 직원 성별 통계'
						    },
						    tooltip: {
						        pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
						    },
						    plotOptions: {
						        pie: {
						            allowPointSelect: true,
						            cursor: 'pointer',
						            dataLabels: {
						                enabled: true,
						                format: '<b>{point.name}</b>: {point.percentage:.1f} %',
						                style: {
						                    color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
						                }
						            }
						        }
						    },
						    series: [{
						        name: '구성비율',
						        colorByPoint: true,
						        data: resultArr
						    }]
						});
						//////////////////////////////// 차트가 들어올 부분 ////////////////////////////////////
						
						var html = "<table id='tblGender'>"
						         + "<thead>"
						         + "<tr>"
						         + "<th>성별</th>"
						         + "<th>인원수</th>"
						         + "<th>비율(%)</th>"
						         + "</tr>"
						         + "</thead>"
						         + "<tbody>";
						         
						$.each(json, function(entryIndex, entry) {
							html += "<tr>";
							html += "<td>"+entry.gender+"</td>";
							html += "<td>"+entry.cnt+"</td>";
							html += "<td>"+entry.percent+"</td>";
							html += "</tr>";
						});
						
						html += "</tbody>";
						html += "</table>";
						
						$("#tbl").html(html);
						
					},
					error: function(request, status, error){
						alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
					}
				});
				
				
			break;
			case "ageline" :
				// alert("연령대");
				
				$.ajax({
					url: "mybatisTest16JSON_ageline.action",
					type: "GET",
					dataType: "JSON",
					success: function(json){
						
						// 애초에 json 은 배열타입으로 넘어와서 json 의 키값을 이용해 아래 for문에서 값을 추출해준다.
						$("#chart").empty();
						$("#tbl").empty();
						
						var resultArr = [];
						
						for(var i=0; i<json.length; i++) {
							var subArr = [ json[i].ageline, Number(json[i].cnt) ];
							
							resultArr.push(subArr);	// 배열속에 값을 넣기
						}
						
						////////////////////////////////차트가 들어올 부분 ////////////////////////////////////

						Highcharts.chart('chart', {
						    chart: {
						        type: 'column'
						    },
						    title: {
						        text: '우리회사 직원 연령대별 인원 통계'
						    },
						    subtitle: {
						        text: '출처 : <a href="https://www.google.com/">구글</a>'
						    },
						    xAxis: {
						        type: 'category',
						        labels: {
						            rotation: -45,
						            style: {
						                fontSize: '13px',
						                fontFamily: 'Verdana, sans-serif'
						            }
						        }
						    },
						    yAxis: {
						        min: 0,
						        title: {
						            text: '인원수 (명)'
						        }
						    },
						    legend: {
						        enabled: false
						    },
						    tooltip: {
						        pointFormat: '인원수 in 2017: <b>{point.y:.0f} 명</b>'
						    },
						    series: [{
						        name: 'Population',
						        data: resultArr,
						        dataLabels: {
						            enabled: true,
						            rotation: -90,
						            color: '#FFFFFF',
						            align: 'right',
						            format: '{point.y:.0f}', // one decimal
						            y: 10, // 10 pixels down from the top
						            style: {
						                fontSize: '13px',
						                fontFamily: 'Verdana, sans-serif'
						            }
						        }
						    }]
						});
						
						//////////////////////////////// 차트가 들어올 부분 ////////////////////////////////////
						
					},
					error: function(request, status, error){
						alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
					}
				});
				
			break;
			case "deptno" :
				// alert("부서번호");
				
				//////////////////////////////// 차트가 들어올 부분 ////////////////////////////////////

				$.ajax({
					url: "mybatisTest16JSON_deptno.action",
					type: "GET",
					dataType: "JSON",
					success: function(json){
						
						// 애초에 json 은 배열타입으로 넘어와서 json 의 키값을 이용해 아래 for문에서 값을 추출해준다.
						$("#chart").empty();
						$("#tbl").empty();
						
						var resultArr = [];
						
						for(var i=0; i<json.length; i++) {
							var subArr = [ json[i].deptname, Number(json[i].salary) ];
							
							resultArr.push(subArr);	// 배열속에 값을 넣기
						}
						
						////////////////////////////////차트가 들어올 부분 ////////////////////////////////////

						Highcharts.chart('chart', {
						    chart: {
						        type: 'column'
						    },
						    title: {
						        text: '우리회사 부서별 평균 월급 통계'
						    },
						    subtitle: {
						        text: '출처 : <a href="https://www.google.com/">구글</a>'
						    },
						    xAxis: {
						        type: 'category',
						        labels: {
						            rotation: -45,
						            style: {
						                fontSize: '13px',
						                fontFamily: 'Verdana, sans-serif'
						            }
						        }
						    },
						    yAxis: {
						        min: 0,
						        title: {
						            text: '인원수 (명)'
						        }
						    },
						    legend: {
						        enabled: false
						    },
						    tooltip: {
						        pointFormat: '인원수 in 2017: <b>{point.y:.0f} 명</b>'
						    },
						    series: [{
						        name: 'Population',
						        data: resultArr,
						        dataLabels: {
						            enabled: true,
						            rotation: -90,
						            color: '#FFFFFF',
						            align: 'right',
						            format: '{point.y:.0f}', // one decimal
						            y: 10, // 10 pixels down from the top
						            style: {
						                fontSize: '13px',
						                fontFamily: 'Verdana, sans-serif'
						            }
						        }
						    }]
						});
						
						//////////////////////////////// 차트가 들어올 부분 ////////////////////////////////////
						
						var html = "<table id='tblDeptno'>"
					         + "<thead>"
					         + "<tr>"
					         + "<th>부서번호</th>"
					         + "<th>부서명</th>"
					         + "<th>평균연봉</th>"
					         + "</tr>"
					         + "</thead>"
					         + "<tbody>";
						         
						$.each(json, function(entryIndex, entry) {
							html += "<tr>";
							html += "<td>"+entry.deptno+"</td>";
							html += "<td>"+entry.deptname+"</td>";
							html += "<td>"+entry.salary+"</td>";
							html += "</tr>";
						});
						
						html += "</tbody>";
						html += "</table>";
						
						$("#tbl").html(html);
							
						},
						error: function(request, status, error){
							alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
						}
				});
				
			break;
		
		}// end of switch(chartType)--------------------------------
		
	}// end of function callAjax(chartType)-----------------------------------
    		
</script>	

</head>
<body>

	<div align="center" style="margin-bottom: 100px;">
		<h2>우리회사 사원명단(Chart)</h2>
		<br/><br/>
		
		<form name="searchFrm">
			
			<select name="chartType" id="chartType" style="height: 25px;">
				<option value="gender">성별</option>
				<option value="ageline">연령대</option>
				<option value="deptno">부서번호</option>
			</select>
									
			<button type="button" id="btnShow">보여주기</button>&nbsp;&nbsp;
			<button type="button" id="btnClear">초기화</button>
		</form>
	</div>
	
	<br/>
	<div id="chart" style="min-width: 310px; height: 400px; max-width: 600px; margin: 0 auto"></div>
	<div id="tbl" style="margin: 0 auto; width: 40%; border: 0px solid red;"></div>

</body>
</html>
