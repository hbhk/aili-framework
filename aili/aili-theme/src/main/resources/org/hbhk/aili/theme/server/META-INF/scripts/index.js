$(function() {
	// 图片轮播
	$('#myCarousel').carousel('next');
	$('#sidebar .list-group a').mouseenter(function(obj) {
		$(this).toggleClass("active");
	});

	$('#sidebar .list-group a').mouseleave(function(obj) {
		$(this).toggleClass("active");
	});
	var uername = $.cookie("uername");
	if (typeof (uername) != 'undefined') {
		$('#login_form').hide()
		$('#user_info').show()

	} else {
		$('#login_form').show()
		$('#user_info').hide()
	}
	//	
	// $('#login_form').bootstrapValidator({
	// message: 'This value is not valid',
	// feedbackIcons: {
	// valid: 'glyphicon glyphicon-ok',
	// invalid: 'glyphicon glyphicon-remove',
	// validating: 'glyphicon glyphicon-refresh'
	// },
	// fields: {
	// username: {
	// message: 'The username is not valid',
	// validators: {
	// notEmpty: {
	// message: 'The username is required and cannot be empty'
	// },
	// stringLength: {
	// min: 6,
	// max: 30,
	// message: 'The username must be more than 6 and less than 30 characters
	// long'
	// },
	// regexp: {
	// regexp: /^[a-zA-Z0-9_]+$/,
	// message: 'The username can only consist of alphabetical, number and
	// underscore'
	// }
	// }
	// },
	// password: {
	// validators: {
	// notEmpty: {
	// message: 'The email is required and cannot be empty'
	// },
	// emailAddress: {
	// message: 'The input is not a valid email address'
	// }
	// }
	// }
	// }
	// });
	$('.btn-primary').click(function() {
		$.post(getReqUrl("security/login.htm"), {
			username : "Donald Duck",
			password : "Duckburg"
		}, function(data, status) {
			$.cookie("uername", "uername");
			window.location.reload()
		});
	});

	$('#logout').click(function() {
		$.post(getReqUrl("security/login.htm"), {
			username : "Donald Duck",
			password : "Duckburg"
		}, function(data, status) {
			$.removeCookie("uername");
			window.location.reload()
		});
	});

});