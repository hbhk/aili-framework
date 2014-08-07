<%@page language="java" pageEncoding="UTF-8"%>
<%@taglib uri="/hbhk" prefix="hbhk" %>
<html>
<head>
<link rel="shortcut icon" href="${images}/favicon.ico" />
<link href="${styles}/login.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${scripts}/boot.js"></script>
<%-- <script type="text/javascript" src="${scripts}/login.js"></script> --%>
<hbhk:module subModule="hbhk" groups="loginpage"/>
<script type="text/javascript">
	$(function() {
		$('#kaptchaImage').click(
				function() {//生成验证码  
					$(this).hide().attr('src',
							'${appcontext}/security/validateCode.ctrl?' + Math.floor(Math.random() * 100))
							.fadeIn();
		});
	});
</script>
</head>
<body>
	<div  class="login" align="center"  style="width:100%;height:auto">
		<fieldset style="width:400px;" >
			<legend align="left" ><hbhk:i18nForJsp key="hbhk.user.auth"/></legend>
			<form id="loginForm" class="loginForm" action="${appcontext}/security/login.ctrl" method="post" style="text-align:left;">
				<span ><hbhk:i18nForJsp key="hbhk.user.username"/>:</span><input type="text" name="username" /><br /> 
				<span ><hbhk:i18nForJsp key="hbhk.user.password"/>:</span><input type="password" name="password" /><br /> 
				<span><hbhk:i18nForJsp key="hbhk.user.validateCode"/></span><input name="validateCode" type="text" id="kaptcha" maxlength="4" class="chknumber_input" />
				<div align="center"><img src="${appcontext}/security/validateCode.ctrl" width="55" height="20" id="kaptchaImage" style="margin-bottom: -3px" /></div>
				<div align="center" >
				<div><input type="checkbox" name="_spring_security_remember_me" value="true"/><span><hbhk:i18nForJsp key="hbhk.user.rememberme"/></span></div>
				<input type="submit"  class="btn_sub" onmouseover="this.style.backgroundColor='green'" onmouseout="this.style.backgroundColor='#C0F81B'"  value='<hbhk:i18nForJsp key="hbhk.user.login"/>' />
				<br/>
				<b style="color:red">${errorMsg}</b>
				</div>
			</form>
		</fieldset>
	</div>
</body>
</html>
