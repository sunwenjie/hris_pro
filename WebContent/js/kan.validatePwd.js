
	var right_img = "<i class='icon-right icon-ok icon-large'></i>";
	var wrong_img = "<i class='icon-wrong icon-remove icon-large'></i>";
	var new_pwd_is_true = false;

function reg_password(val){
		reg = /^(?=.*(\d|([A-Z])))(?=.*([A-Z]|[\x21-\x2F\x3A-\x40\x5B-\x60\x7B-\x7E]))(?=.*([\x21-\x2F\x3A-\x40\x5B-\x60\x7B-\x7E]|\d))([\x21-\x7E]){3,40}$/
		$(".icon-ok").remove();
		$(".icon-remove").remove();
		
	    if(!val) return false;
		var is_true = true;
	
		var pwd_reg = 0;
		if(val.length >= 8){
			$(".warning ul li:eq(0)").html($(".warning ul li:eq(0)").text() + right_img);
			pwd_reg += 1
		}else{
			$(".warning ul li:eq(0)").html($(".warning ul li:eq(0)").text() + wrong_img);
		}
		if(val.match(/[a-z]+/) && val.match(/[A-Z]+/)){
			$(".warning ul li:eq(1)").html($(".warning ul li:eq(1)").text() + right_img);
			pwd_reg += 1
		}else{
			$(".warning ul li:eq(1)").html($(".warning ul li:eq(1)").text() + wrong_img);
		}
		if(val.match(/\d/)){
			$(".warning ul li:eq(2)").html($(".warning ul li:eq(2)").text() + right_img);
			pwd_reg += 1
		}else{
			$(".warning ul li:eq(2)").html($(".warning ul li:eq(2)").text() + wrong_img);
		}
		if(val.match(/[\x21-\x2F\x3A-\x40\x5B-\x60\x7B-\x7E]+/)){
			$(".warning ul li:eq(3)").html($(".warning ul li:eq(3)").text() + right_img);
			pwd_reg += 1
		}else{
			$(".warning ul li:eq(3)").html($(".warning ul li:eq(3)").text() + wrong_img);
		}
		if(pwd_reg < 4){
			is_true = false;
		}

	    
	     if(is_true){
	         if($("#confirmnewpassword").val() == val){
	             enableBtn();
	         }
	        
	         new_pwd_is_true = true;
	     }else{
	         disableBtn();
	         new_pwd_is_true = false;
	     }
	     
	     
	     if(!two_pwds_matches($("#confirmnewpassword").val(),val)){
	    	 disableBtn();
	     }

	};
	//判断两次密码输入是否一致
	function reg_confirm_password(str){
	    new_pwd = $("#newpassword").val();
	    $(".li-pwd2").removeClass("li-hide");
	    
	    if(two_pwds_matches(new_pwd,str) && str == new_pwd && new_pwd_is_true){
	        enableBtn();
	    }else{
	    	disableBtn();
	    }
	};
	
	function two_pwds_matches(val1,val2){
		
		var two_pwds_matches = false;
		
		$(".warning ul li:eq(4)").find("i").remove();
		
		if(val1 == val2 && val1 !='' && val2!=''){
	    	 $(".warning ul li:eq(4)").html($(".warning ul li:eq(4)").html()+ right_img );
	    	 two_pwds_matches = true;
	     }else{
	    	 $(".warning ul li:eq(4)").html($(".warning ul li:eq(4)").html()+ wrong_img );
	     }
		return two_pwds_matches;
	};
	
	function savePassword(){
		
		$(".form_primary").submit();
	};
	
	function disableBtn(){
		$("#pwd_update").attr("disabled", "disabled");
		$("#pwd_update").removeClass("btn-primary");
	};
	
	function enableBtn(){
		$("#pwd_update").attr("disabled", false);
		$("#pwd_update").addClass("btn-primary");
	};