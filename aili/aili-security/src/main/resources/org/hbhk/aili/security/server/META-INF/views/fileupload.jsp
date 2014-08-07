<%@page language="java" pageEncoding="UTF-8"%>
<html>
<head>
</head>
<body>

	<form action="${appcontext}/framework/uploadFile" enctype="multipart/form-data" 	method="post">
		file:<input type="file" size="100" name="userfile1"><br /> 
		file:<input type="text" size="100" name="filename"><br /> 
		<input	type="submit" value="submit">

	</form>

</body>
</html>
