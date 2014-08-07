<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>jQuery async treeview</title>

<link rel="stylesheet" href="${styles}/jquery.treeview.css" />
<link rel="stylesheet" href="${styles}/screen.css" />
<script type="text/javascript" src="${scripts}/boot.js"></script>
<script src="${scripts}/jquery.cookie.js" type="text/javascript"></script>
<script src="${scripts}/jquery.treeview.js" type="text/javascript"></script>
<script src="${scripts}/jquery.treeview.edit.js" type="text/javascript"></script>
<script src="${scripts}/jquery.treeview.async.js" type="text/javascript"></script>
<script src="${scripts}/menu.js" type="text/javascript"></script>

<script type="text/javascript">
	function initTrees() {
		$("#menu").treeview({
			url : "getMenu.ctrl",
			persist:'cookie',
			ajax : {
				data : {
					"additional" : function() {
						return "yeah: " + new Date;
					}
				},
				type : "post"
			}
		});
	}
	$(document).ready(function() {
		initTrees();
		$("#refresh").click(function() {
			$("#menu").empty();
			initTrees();
		});
	});
</script>

</head>
<body>

	<ul id="menu">
	</ul>
	<button id="refresh">Refresh both Trees</button>

</body>
</html>