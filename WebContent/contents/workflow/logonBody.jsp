<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<div class="loginForm">
	<html:form action="securityAction.do?proc=logon" styleClass="login_form">
		<table>
			<tr>
				<td align="left">
					<label class="title"><bean:message key="logon.title"/></label>
					<label class="loginError"><html:errors property="LoginError"/></label>
					<label><bean:message key="logon.account"/></label>
					<html:text property="accountName" maxlength="20" styleClass="login_accountName" />
					<html:hidden property="accountId" styleClass="login_accountId" />
					<label><bean:message key="logon.username"/></label>
					<html:text property="username" maxlength="20" styleClass="login_username" />
					<label><bean:message key="logon.password"/></label>
					<html:password property="password" maxlength="20" styleClass="login_password"/>
					<p>
						<input style="float: left;" type="submit" name="btnLogin" id="btnLogin" value="<bean:message bundle="public" key="button.login" />" /> 
						<%-- <input type="checkbox" name="cbPersistentCookie" id="cbPersistentCookie" value="yes" checked="checked" /><span style="cursor:hand" onclick="clickCheckBox();"><bean:message key="logon.keep_logon_state"/></span> --%>
					</p>
					<a href="#" class="commonlink"><bean:message key="logon.error_username_or_passord"/></a>
				</td>
			</tr>
		</table>	
	</html:form>
</div>

<script type="text/javascript">
	(function($) {
		$(".login_username").addClass("inputFormatHint");
		$(".login_username").val("<bean:message key="logon.user_email_tel"/>");
		
		$(".login_username").focusin(function() {
			if($(".login_username").val() == '<bean:message key="logon.user_email_tel"/>'){
				$(".login_username").removeClass("inputFormatHint");
				$(".login_username").val("");
			}
		});

		$(".login_username").focusout(function() {
			if($(".login_username").val() == ""){
				$(".login_username").addClass("inputFormatHint");
				$(".login_username").val("<bean:message key="logon.user_email_tel"/>");
			}
		});
		
		$("#btnLogin").click( function () { 
			var flag = 0;
			
			if($(".login_username").val() == "<bean:message key="logon.user_email_tel"/>"){
				$(".login_username").removeClass("inputFormatHint");
				$(".login_username").val("");
			}
			flag = flag + validate("login_username", true, "common", 20, 5);
			
			if($(".login_username").val() == ""){
				$(".login_username").addClass("inputFormatHint");
				$(".login_username").val("<bean:message key="logon.user_email_tel"/>");
			}
			flag = flag + validate("login_password", true, "common", 20, 5);
			
			if(flag == 0){
				$(".login_form").submit();
			}
		});
		// 绑定回车提交表单
		$(".login_username").keydown(function (e){
			if(e.keyCode==13){
				$('#btnLogin').click();
			}
		});
		$(".login_password").keydown(function (e){
			if(e.keyCode==13){
				$("#btnLogin").click();
			}
		});
		// Use the common thinking
		kanThinking_column('login_accountName', 'login_accountId', 'accountAction.do?proc=list_object_json');
	})(jQuery);

	function clickCheckBox(){
		$('#cbPersistentCookie').click();
	};
	
</script>