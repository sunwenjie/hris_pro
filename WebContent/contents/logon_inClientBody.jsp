<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<div class="loginForm">
	<html:form action="clientSecurityAction.do?proc=logon_inClient" styleClass="login_form">
		<table>
			<tr>
				<td align="left">
					<label class="title"><bean:message key="logon.title"/></label>
					<label class="loginError"><html:errors property="LoginBindipError"/></label>
					<label><bean:message key="logon.account"/></label>
					<html:text property="accountName" maxlength="100" styleClass="login_accountName" />
					<html:hidden property="accountId" styleClass="login_accountId" />
					<label class="loginError"><html:errors property="LoginAccountError"/></label>
					<label><bean:message key="logon.username"/></label>
					<html:text property="username" maxlength="50" styleClass="login_username" />
					<label class="loginError"><html:errors property="LoginUsernameError"/></label>
					<label><bean:message key="logon.password"/></label>
					<html:password property="password" maxlength="20" styleClass="login_password"/>
					<label class="loginError"><html:errors property="LoginPasswordError"/></label>
					<p>
						<input style="float: left;" type="submit" name="btnLogin" id="btnLogin" value="<bean:message bundle="public" key="button.login" />" /> 
						<input type="checkbox" name="cbPersistentCookie" id="cbPersistentCookie" /><span style="cursor:hand" onclick="clickCheckBox();"><bean:message key="logon.keep_logon_state"/></span>
					</p>
					<a href="#" class="commonlink"><bean:message key="logon.error_username_or_passord"/></a>
				</td>
			</tr>
		</table>	
	</html:form>
	<logic:present name="MESSAGE">
		<script type="text/javascript">
			alert('${MESSAGE}');
		</script>
	</logic:present>

</div>

<script type="text/javascript">
	(function($) {
		$(".login_username").addClass("inputFormatHint");
		$(".login_username").val("<bean:message key="logon.user_email_tel"/>");

		$(".login_username").bind('focusin', function() {
			if($(".login_username").val() == '<bean:message key="logon.user_email_tel"/>'){
				$(".login_username").removeClass("inputFormatHint");
				$(".login_username").val("");
			}
		});
		
		$(".login_username").bind('focusout', function() {
			if($(".login_username").val() == ""){
				$(".login_username").addClass("inputFormatHint");
				$(".login_username").val("<bean:message key="logon.user_email_tel"/>");
			}else if($(".login_username").val() != null){
				$(".login_username").removeClass("inputFormatHint");
			}
		});

		$("#btnLogin").click( function () { 
			// «Âø’¥ÌŒÛ–≈œ¢
			$('.loginError').each(function(){
				$(this).remove();
			});
			
			var flag = 0;
			
			if($(".login_username").val() == "<bean:message key="logon.user_email_tel"/>"){
				$(".login_username").removeClass("inputFormatHint");
				$(".login_username").val("");
			}
			flag = flag + validate("login_username", true, "common", 100, 2);
			
			if($(".login_username").val() == ""){
				$(".login_username").addClass("inputFormatHint");
				$(".login_username").val("<bean:message key="logon.user_email_tel"/>");
			}
			flag = flag + validate("login_password", true, "common", 20, 5);
			
			if(flag != 0){
				return false;
			}
		});
		
		// Use the common thinking
		kanThinking_column('login_accountName', 'login_accountId', 'accountAction.do?proc=list_object_json');
	})(jQuery);

	function clickCheckBox(){
		$('#cbPersistentCookie').click();
	};
	
	window.onload =function (){  
		$.ajax({
			 url : 'clientSecurityAction.do?proc=autoLogon',
			 dataType : "json",
			 success : function(data){
				 if(data.success==true){
					 $('.login_accountName').val(data.accountName);
					 $('.login_accountId').val(data.accountId);
					 $('.login_username').val(data.username);
					 $('.login_password').val(data.password);
					 $(".login_form").submit();
				 }
			 }
		});
	};
	
</script>