$(function() {
	
		$("#loginForm").validate({
			rules:{
			username:"required",
			password:"required",
			validateCode:"required"
			},
			messages:{
				username:security.hbhk.i18n('hbhk.user.inputusername'),
				password:security.hbhk.i18n('hbhk.user.inputpass'),
				validateCode:security.hbhk.i18n('hbhk.user.inputcode')
		    }
		});
		
});