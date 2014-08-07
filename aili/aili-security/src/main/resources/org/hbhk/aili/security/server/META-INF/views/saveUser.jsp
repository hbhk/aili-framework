<%@page language="java" pageEncoding="UTF-8"%>
<html>
<head>
<%@include file="common.jsp"%>
<script type="text/javascript">
     var cfg = {
        type: 'POST', 
        data: JSON.stringify({username:'hbhk',password:'135246'}), 
        dataType: 'json',
        contentType:'application/json;charset=UTF-8',        
        success: function(result) { 
            alert(result.msg); 
        } 
    };
	function login() {  
		cfg.url = "${appcontext}/framework/insertUser";
	    $.ajax(cfg);
	};  
</script>
</head>
<body>
<form action="${appcontext}/framework/insertUser" method="post" >
		<input type="text" name="user" /><br /> 
		<input type="submit"  value="ssss">
	    <input type="button" value="submit"  onclick="login()" /><br /> 
	</form>
</body>
</html>
