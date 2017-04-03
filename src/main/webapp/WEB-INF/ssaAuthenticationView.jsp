<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
	<link  rel="stylesheet" style="text/css" href="<%=basePath%>css/bootstrap-theme.min.css"/>
	<link  rel="stylesheet" style="text/css" href="<%=basePath%>css/bootstrap.min.css"/>
	<!-- insert js  -->
	<script type="text/javascript" src='<%=basePath%>js/angular.min.js'></script>
	<script type="text/javascript" src='<%=basePath%>js/sockjs.js'></script>
	<script type="text/javascript" src='<%=basePath%>js/jquery.min.js'></script>
	<script type="text/javascript" src='<%=basePath%>js/bootstrap.min.js'></script>
	<script type="text/javascript" src='<%=basePath%>js/stomp.js'></script>
	<script type='text/javascript' src='<%=basePath%>js/echarts/echarts.js'></script>
	
	<link rel='stylesheet' style='text/css' media='screen and (max-width:600px)' href='<%=basePath%>css/ssaAuthenticationView2.css'>
	<link rel='stylesheet' style='text/css' media='screen and (min-width:600px)' href='<%=basePath%>css/ssaAuthenticationView.css'>
</head>
<body>
	<div id='label'>
	
	</div>
	<div id='auth'>
		<form action='<%=basePath%>openid/authenticate' method='post'>
		<ul>
			<li class='iptLi'>
				<div class='label'></div>
				<div class='value'><input name='account' placeholder="請填寫用戶名"/></div>
			</li>
			<li class='errLi'>
				<div class='error'><span color='red'>${requestScope.account_error}</span></div>
			</li>
			<li class='iptLi'>
				<div class='label'></div>
				<div class='value'><input name='password' type='password' placeholder='請填寫密碼'/></div>
				
			</li>
			<li class='errLi'>
				<div class='error'><span color='red'>${requestScope.password_error}</span></div>
			</li>
			<li class='errLi'>
				<div class='error'><span color='red'>${requestScope.fail}</span></div>
			</li>
			<li id='lastLi'>	
					<input id='submit' type='submit' value='提交'/>
			</li>
			<input type='text' name='service_id' value="${requestScope.service_id}"/>
			<input type='text' name='client_id' value='${requestScope.client_id}'/>
			<input type='text' name='state' value='${requestScope.state}'/>
			<input type='text' name='redirect_uri' value='${requestScope.redirect_uri }'/>
			<input type='text' name='user_id' value=${requestScope.user_id }/>
		</ul>
		</form>
	</div>
	
	<script type="text/javascript" src='js/jquery-1.9.1.min.js'></script>
	<script type="text/javascript">
		$(function(){
			var marginLeft=(document.body.clientWidth-$('#auth').width())*0.5;
			//alert('margin-left:'+marginLeft)
			$('#auth').css('margin-left',marginLeft);
			$('#auth').css('visibility','visible');
		})
	</script>
</body>
</html>