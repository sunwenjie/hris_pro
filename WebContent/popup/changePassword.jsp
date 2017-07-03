<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ page import="com.kan.base.util.KANConstants"%>
<%@ page import="com.kan.base.web.action.BaseAction"%>

<style>
	.icon-wrong{color: red;}
	.warning>ul{padding-left: 20px;}
	.btn-primary{background-color: grey;}
	.li-hide{display: none;}
</style>

<!-- Module Box HTML: Begins -->
<div class="modal midsize content hide" id="passwordModalId">
    <div class="modal-header" id="passwordHeader"> 
        <a class="close" data-dismiss="modal" onclick="$('#passwordModalId').addClass('hide');$('#shield').hide();">×</a>
        <label><bean:message bundle="security" key="security.user.password.change" /></label>
    </div>
    <div class="modal-body">
    		
    	<div class="top ">
	   		<input type="button" class="save btn-primary" name="changePassword_btnSave" id="pwd_update" value="<bean:message bundle="public" key="button.save" />" onclick="savePassword();" />
	    	<input type="button" class="reset" name="changePassword_btnCancel" id="changePassword_btnCancel" onclick="resetPasswordForm()" value="<bean:message bundle="public" key="button.reset" />" />
	    </div>
        <html:form action="securityAction.do?proc=change_password" styleClass="changePassword_form">
        	<div class="form-group password-info">
				<label><bean:message bundle="wx" key="wx.change.pwd.label1" />:</label><br/>
				
					<div class="warning">
					<ul>
						<li><bean:message bundle="wx" key="wx.change.pwd.label2" /></li>
						<li><bean:message bundle="wx" key="wx.change.pwd.label3" /></li>
						<li><bean:message bundle="wx" key="wx.change.pwd.label5" /></li>
						<li><bean:message bundle="wx" key="wx.change.pwd.label6" />!@#$%^&*{}</li>
						<li class="li-hide li-pwd2"><bean:message bundle="wx" key="wx.change.pwd.label7" /></li>
					</ul>
					</div>
			</div>
        
			<fieldset>
				<ol class="auto">
					<li style="width: 100%">
						<label><bean:message bundle="security" key="security.user.new.password" /><em>* </em></label>
						<input type="password" id="newpassword" name="newPassword" maxlength="25" class="changePassword_newPassword" onkeyup="reg_password($(this).val());"/>
					</li>
				</ol>
				<ol class="auto">
					<li style="width: 100%">
						<label><bean:message bundle="security" key="security.user.confirm.new.password" /><em>* </em></label>
						<input type="password" id="confirmnewpassword" name="newPasswordConfirm" maxlength="25" class="changePassword_newPasswordconfirm" onkeyup="reg_confirm_password($(this).val());"/>
					</li>
				</ol>
			</fieldset>
		</html:form >
    </div>
</div>
<!-- Module Box HTML: Ends -->

<script type="text/javascript">
	// 重置
	function resetPasswordForm(){
		$('.changePassword_newPassword').val('');
		$('.changePassword_newPasswordconfirm').val('');
		$('span.icon-right, span.icon-wrong').remove();
		disableBtn();
	};
		
	// 弹出模态窗口
	function popupChangePassword(){
		resetPasswordForm();
		$('#passwordModalId').removeClass('hide');
    	$('#shield').show();
	};
	
	// Esc按键事件 - 隐藏弹出框
	$(document).keyup(function(e){
	    if (e.keyCode == 27) 
	    {
	    	$('#passwordModalId').addClass('hide');
	    	$('#shield').hide();
	    }
	});
	
	disableBtn();
	var right_img = "<span class='icon-right icon-ok icon-large' style='color:green;font-size:14px;'>&nbsp;√</i></span>";
	var wrong_img = "<span class='icon-wrong icon-remove icon-large' style='color:red;font-size:14px;'>&nbsp;×</i></span>";
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
		
		$(".warning ul li:eq(4)").find("span").remove();
		
		if(val1 == val2 && val1 !='' && val2!=''){
	    	 $(".warning ul li:eq(4)").html($(".warning ul li:eq(4)").html()+ right_img );
	    	 two_pwds_matches = true;
	     }else{
	    	 $(".warning ul li:eq(4)").html($(".warning ul li:eq(4)").html()+ wrong_img );
	     }
		return two_pwds_matches;
	};
	
	function savePassword(){
		
		$(".changePassword_form").submit();
	};
	
	function disableBtn(){
		$("#pwd_update").attr("disabled", "disabled");
		$("#pwd_update").css("background-color","#CCCCCC");
	};
	
	function enableBtn(){
		$("#pwd_update").attr("disabled", false);
		$("#pwd_update").css("background-color","#33ac3f");
	};
	
</script>