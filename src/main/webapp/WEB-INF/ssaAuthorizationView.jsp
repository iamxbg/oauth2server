<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix='c' uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang='en'>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>授權登陸頁面</title>
	<!-- bootstrap css -->

	<script type="text/javascript" src='<%=basePath%>js/jquery.min.js'></script>

	
	<link rel='stylesheet' style='text/css'  href='<%=basePath%>css/ssaAuthorizationView.css'>

	
</head>
<body>
	
	<div>
		<div id='app_infos'>
			<div id='app_profile'>
				<IMG alt="" src="" />
			</div>
			<div id='app_name'>
				<SPAN>${client.name}</SPAN>
			</div>
		</div>
		

		<div id='scopes'>
			<span id='tip'>使用${service_name}登陆后,该应用将获得以下权限:</span>
			<ul >
				<c:forEach items="${scpList}" var='scp'>
					<li><span>${scp.description }</span></li>
				</c:forEach>
			</ul>
		</div>

		<div id='actions'>
			<div id='aggree'>
				<span>授權登陸</span>
			</div>
		</div>

		<form id='authForm' action='<%=basePath%>ssa/authorize' method='post'>
			<input type='text' name='ticket' value='${ticket}'/>
		</form>
		
	</div>
	 
	 <script type='text/javascript'>
		$(function(){
			
			$('#aggree').on({
				'mouseenter':function(){$(this).css('background','lime')}
				,'mouseleave':function(){$(this).css('background','green')}
				,'click':function(){
					
						$('#authForm').submit();
					
				}
				
			})
		})
	 </script>
	
</body>
</html>