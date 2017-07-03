<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@page import="com.kan.base.util.KANUtil"%>
<%@ page import="com.kan.hro.domain.biz.vendor.VendorUserVO"%>

<% VendorUserVO vendorUserVO = (VendorUserVO)request.getAttribute("vendorUserForm");%>

<div class="loginForm">
	<html:form action="vendorSecurityAction.do?proc=logon_inVendor" styleClass="login_form">
		<table>
			<tr>
				<td align="left">
					<label class="title"><bean:message key="logon.title"/></label>
					<label class="loginError"><html:errors property="LoginBindipError"/></label>
<%-- 					<label class="loginError"><html:errors property="LoginError"/></label> --%>
					<label>供应商ID</label>
					<html:text property="vendorId" maxlength="11" styleClass="login_vendorId" value=""/>
					<label class="loginError"><html:errors property="LoginVendorError"/></label>
					<label>用户名</label>
					<html:text property="username" maxlength="50" styleClass="login_username" />
					<label class="loginError"><html:errors property="LoginUsernameError"/><html:errors property="LoginPositionError"/></label>
					<label>密码</label>
					<html:password property="password" maxlength="20" styleClass="login_password"/>
					<label class="loginError"><html:errors property="LoginPasswordError"/></label>
					<p>
						<input style="float: left;" type="submit" name="btnLogin" id="btnLogin" value="<bean:message bundle="public" key="button.login" />" /> 
						<input type="checkbox" name="cbPersistentCookie" id="cbPersistentCookie"  /><span style="cursor:hand" onclick="clickCheckBox();"><bean:message key="logon.keep_logon_state"/></span>
					</p>
					<a href="#" class="commonlink"><bean:message key="logon.error_username_or_passord"/></a>
				</td>
			</tr>
		</table>	
	</html:form>
</div>

<script type="text/javascript">
	(function($) {
		<% if(vendorUserVO == null || KANUtil.filterEmpty(vendorUserVO.getUsername()) == null){%>		
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
			// 清空错误信息
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
	})(jQuery);

	function clickCheckBox(){
		$('#cbPersistentCookie').click();
	};
	
	window.onload =function (){  
		$.ajax({
			 url : 'vendorSecurityAction.do?proc=autoLogon',
			 dataType : "json",
			 success : function(data){
				 if(data.success==true){
					 $('.login_vendorId').val(data.vendorId);
					 $('.login_username').val(data.username);
					 $('.login_password').val(data.password);
					 $(".login_form").submit();
				 }
			 }
		});
	};
</script>