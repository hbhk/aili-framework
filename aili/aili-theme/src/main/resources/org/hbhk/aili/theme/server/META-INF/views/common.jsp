<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html lang="zh-cn">
<head>
	<script src="${scripts}/boot.js"></script>
 	<link href="${base}uploadify/uploadify.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="${base}uploadify/jquery.uploadify.js"></script>
	<script type="text/javascript">
		var $j = jQuery.noConflict();
		 var base="${base}";
		 var UserContext = {'user':'${cuser}','name':'${cuserName}'};
	</script>
</head>
