<%@page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>jquery导航菜单插件制作jquery动画菜单熔岩灯菜单效果</title>
<meta name="description" content="jquery导航菜单制作一个类似熔岩灯jquery动画菜单效果，自适应导航菜单显示。jquery 插件下载。" />

<script type="text/javascript" src="${scripts}/boot.js"></script>
<script type="text/javascript" src="${scripts}/jquery.plugins.js"></script>
<script type="text/javascript">
$(function() {
	$(".meun").lavaLamp({
		fx: "backout", 
		speed: 700,
		click: function(event, menuItem) {
			return true;
		}
	});
});
</script>
<!--[if lt IE 7]>
<style type="text/css">
.meun_bg{background:none!important; filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(src='images/image87.png',sizingMethod='crop')}
</style>
<![endif]-->
</head>

<body>

<style type="text/css">
*{margin:0;padding:0;list-style-type:none;}
a,img{border:0;}
/* meun */
.meun{position:relative;height:68px;width:967px;padding-left:13px;background:url(${images}/meun_bg.png) no-repeat 0 8px;overflow: hidden;margin:40px auto;}
.meun_bg{ position:absolute;top:0px;left:0px;background:url(${images}/image87.png) no-repeat;height:8px;width:980px;overflow:hidden;}
.meun li{float:left;}
.meun li.back{background:url(${images}/meun_tab.png) no-repeat;padding-left:8px;height:68px;overflow:hidden;z-index:8;position:absolute;}
.meun li.back .left{background:url(${images}/meun_tab.png) no-repeat right 0;height:68px;float:right;width:8px;}
.meun li.back .arrow{float:left;width:92%;height:68px;position:relative;}
.meun li.back .arrow .icon{position:absolute;top:56px;left:45%;background:url(${images}/arrow.gif) no-repeat;height:5px;width:9px;overflow:hidden;}
.meun li a{ font-family:"微软雅黑","黑体";text-decoration:none;color:#fff;font-size:18px;z-index:10;display:block;float:left;position:relative;overflow:hidden;padding:25px 33px 0;height:43px;}
.meun li a span{cursor:pointer;}
</style>

	<div class="meun">
		<div class="meun_bg"></div>
		<ul>
			<li><a href="http://www.17sucai.com/"><span>首页</span></a></li>
			<li><a href="http://www.17sucai.com/"><span>jquery 特效</span></a></li>
			<li><a href="http://www.17sucai.com/"><span>javascript特效</span></a></li>
			<li class="current"><a href="http://www.17sucai.com/"><span>flash特效</span></a></li>
			<li><a href="http://www.17sucai.com/"><span>div+css教程</span></a></li>
			<li><a href="http://www.17sucai.com/"><span>html5教程</span></a></li>
		</ul>
	</div>
	
</body>
</html>
