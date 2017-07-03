<%@page import="com.kan.base.util.RandomUtil"%>
<%@page import="com.kan.base.util.CachedUtil"%>
<%@page import="com.kan.base.web.action.BaseAction"%>
<%@page import="com.kan.base.util.KANUtil"%>
<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ page import="com.kan.base.domain.security.UserVO"%>

<% UserVO userVO = (UserVO)request.getAttribute("userForm");%>

<div class="loginForm">
	<html:form action="securityAction.do?proc=logon_inHouse" styleClass="login_form">
	<%
		String token = RandomUtil.getRandomString( 32 );
	%>
	<input type="hidden" id="token" name="token" value="<%=token %>">
		<table>
			<tr>
				<td align="left">
					<label class="title"><bean:message key="logon.title"/></label>
				
					<label class="loginError"><html:errors property="LoginBindipError"/></label>
					<%-- <label><bean:message bundle="public" key="public.client" /></label>
					<input type="text" name="clientName" class="login_clientName" maxlength="100" /> --%>
				
					<html:hidden property="corpId" styleClass="login_corpId" value="300115" />
					<label class="loginError"><html:errors property="LoginClientError"/></label>
					
					<label><bean:message key="logon.username"/></label>
					<html:text property="username" maxlength="50" styleClass="login_username" />
					
					<!-- error -->
					<label class="loginError">
						<html:errors property="LoginUsernameError"/>
						<html:errors property="LoginPositionError"/>
						<html:errors property="UserInBlackList"/>
					</label>
					<!-- error -->
					
					<label><bean:message key="logon.password"/></label>
					<html:password property="password" maxlength="20" styleClass="login_password"/>
					
					<label><bean:message key="logon.ver.code"/></label>
					<label>
						<input type='text' name='imageValidate' maxlength="5" value='' style="width: 120px;margin-right: 15px;" >
						<img src="jcaptcha?token=<%=token %>" id="imageValidate" style="vertical-align: middle;height: 25px;" onclick="document.getElementById('imageValidate').src='${pageContext.request.contextPath }/jcaptcha?token='+$('#token').val()+'&' + Math.random();">
					</label>
					<!-- error -->
					<label class="loginError">
						<html:errors property="LoginPasswordError"/>
						<html:errors property="VerCodeError"/>
					</label>
					<!-- error -->
					
					<p>
						<input style="float: left;" type="submit" name="btnLogin" id="btnLogin" value="<bean:message bundle="public" key="button.login" />" />
						<!-- 
						<label style="padding-top: 7px;margin-left: 115px;">
							<a href="#" onclick="forgetPwd();" style="text-decoration: none;"><bean:message bundle="wx" key="wx.logon.forget.password" /></a>
						</label>
						 -->
					</p>
				</td>
			</tr>
		</table>	
	</html:form>
</div>

<script type="text/javascript">
	(function($) {
		<logic:notEmpty name="CHANGEPWD_MESSAGE">
			alert("<bean:write name='CHANGEPWD_MESSAGE'/>");
		</logic:notEmpty>
		
		<% if(userVO == null || KANUtil.filterEmpty(userVO.getUsername()) == null){%>
			$(".login_username").addClass("inputFormatHint");
			$(".login_username").val("<bean:message key="logon.user_email_tel"/>");
		<%}%>

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
			flag = flag + validate("login_username", true, "common", 50, 2);
			
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
		// kanThinking_column('login_clientName', 'login_corpId', 'userAction.do?proc=list_clientVO_json');
	})(jQuery);

	function clickCheckBox(){
		$('#cbPersistentCookie').click();
	};
	
	function forgetPwd(){
		window.location.href="securityAction.do?proc=toForgetPwd";
	};
</script>