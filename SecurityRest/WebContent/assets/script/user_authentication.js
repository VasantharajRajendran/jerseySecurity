$(document).ready(function(){
	var validLogin=$("#login_form").validate({
		rules : {
			emailId : {
				required : '',
				email: ''
			},
		},
		messages : {
			ruleName : {
				required :'',
				email:"Please provide a valid email id."
			}
		},
	});
	$("#login-submit").click(function(){
		if(validLogin.form() == true){
		loginFunction();
		}else{
			$('.errmsg').html('please provide valid credentials.')
			$(this).load();
		}
	});
	
	$("#password").keypress(function (e) {
		 var key = e.which;
		 if(key == 13){
			 if(validLogin.form() == true){
			 loginFunction();
			 }
		  }
	});
	
	function loginFunction(){
		alert($("#password").val());
		var password=$("#password").val();
		var username=$("#emailId").val();
		function make_base_auth(user, password) {
			  var tok = user + ':' + password;
			  var hash = btoa(tok);
			  return "Basic " + hash;
			}
		$.ajax({
			url:'/idns',
			type:'GET',
			/*crossDomain: true,*/
			beforeSend : function(req) {
				req.setRequestHeader('Authorization', make_base_auth(username, password));
			},
			success:function(data){
			/*	sessionId=data;
			if(data != undefined || data != null){
				window.location.href = "./index.html";
				}else{
				$('.errmsg').html('please provide a valid credentials.')
				$(this).load();
			}*/
			}
		});
	}
	
	$("#logout").click(function(){
		$.ajax({
			url:'/idns/userAuthentication/logoutSession',
			crossDomain: true,
			type:'GET',
				success:function(data){
					if(data == "SUCCESS"){
						window.location.href = "./login.html";
					}
					
				}
		});
		
	});
});