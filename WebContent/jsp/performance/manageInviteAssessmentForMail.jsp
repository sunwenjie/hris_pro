<%@page import="com.kan.hro.domain.biz.performance.SelfAssessmentVO"%>
<%@page import="com.kan.base.util.KANUtil"%>
<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%
	response.setHeader("Cache-Control","no-store");
	response.setHeader("Pragma","no-cache");
	response.setDateHeader ("Expires", 0);
	
	/* int year = Integer.valueOf( request.getAttribute( "year" ) ); */
%> 
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="GBK">
<title>HRIS - <bean:message bundle="title" key="performance.invite.assessment" /></title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="baidu-tc-verification" content="29da782ffa1d0074beb8aa1f4c4f66ed" />
<link rel="shortcut icon" href="images/icons/iclick-ico.ico">

<!-- Loading Kanpower Style -->
<link href="css/kanpower.css" rel="stylesheet">
<script src="js/kan.thinking.js"></script>
<script src="js/kan.validate.js"></script>

<style type="text/css">
	div.pm_title{ padding-top: 10px; padding-bottom: 10px; font-family: 黑体, Calibri; color: #5d5d5d; text-align: center; }
	form.manageInviteAssessment_form ol li { width: 100% }
	form.manageInviteAssessment_form ol li label { width: 100% }
	form.manageInviteAssessment_form ol li textarea { width: 99% }
	.highlight_f { background-color: #F1C6BD; }
</style>

<script src="js/jquery-1.8.2.min.js"></script>
<script src="js/kan.js"></script>

<body leftmargin="0" topmargin="0" rightmargin="0" bottommargin="0">
	<div id="wrapper">
		<div id="branding_s" style="padding: 5px 25px 0 25px">
			<div>
				<img src="images/logo/iclick-logo.png" width="160" height="38" alt="IClick LOGO" />
			</div>
		</div>
	
		<div id="content" style="padding-top: 0px;">
			<div class="box toggableForm" style="margin: 0px 20px 20px;">
				<div class="head">
					<label id="pageTitle"><bean:message bundle="performance" key="invite.assessment" /></label>
				</div>
				<div class="inner">
					<div id="messageWrapper">
						<logic:present name="MESSAGE">
							<div class="message <bean:write name="MESSAGE_CLASS" /> fadable">
								<bean:write name="MESSAGE" />
				    			<a onclick="$('div.fadable').remove();" class="messageCloseButton">&nbsp;</a>
							</div>
						</logic:present>
					</div>
					<div class="top">
						<logic:empty name="editBtnHide">
							<logic:equal value="1" name="inviteAssessmentForm" property="status">
								<input type="button" class="editbutton" name="btnSave" id="btnSave" value="<bean:message bundle="public" key="button.save"  />" />
								<input type="button" class="function" name="btnSubmit" id="btnSubmit" value="<bean:message bundle="public" key="button.submit"  />" />
							</logic:equal>
						</logic:empty>	
					</div>
					<div class="pm_title">
						<h1><bean:message bundle="performance" key="invite.assessment.title" arg0="${year}" /></h1>
						<p><bean:message bundle="performance" key="invite.assessment.tips" arg0="${employeeName}" arg1="${closeDate}" /></p>
					</div>
					
					<html:form action="inviteAssessmentAction.do?proc=modify_object" styleClass="manageInviteAssessment_form">
						<input type="hidden" name="id" id="id" value="<bean:write name="inviteAssessmentForm" property="encodedId" />" />
						<html:hidden property="randomKey" styleClass="randomKey" styleId="randomKey" />
						<html:hidden property="subAction" styleClass="subAction" styleId="subAction" />
						<fieldset>
							<ol>
								<li>
									<label><h2><bean:message bundle="performance" key="invite.assessment.ask1" /></h2></label>
									<html:textarea property="answer1" styleClass="answer1" styleId="answer1" />
								</li>
								<li>
									<label><h2><bean:message bundle="performance" key="invite.assessment.ask2" /></h2></label>
									<html:textarea property="answer2" styleClass="answer2" styleId="answer2" />
								</li>
								<li>
									<label><h2><bean:message bundle="performance" key="invite.assessment.ask3" /></h2></label>
									<html:textarea property="answer3" styleClass="answer3" styleId="answer3" />
								</li>
							</ol>
						</fieldset>	
					</html:form>
					
				</div>
			</div>
		</div>
		
		<jsp:include page="/common/footer.jsp"></jsp:include>
	</div>	
</body>

<script type="text/javascript">
	(function($) {
		var status = '${inviteAssessmentForm.status}';
		if( status != 1 ){
			disableForm('manageInviteAssessment_form');
		}
			
		$("#btnSave").click( function(){
			if( validate_form() == 0 ){
				submit('manageInviteAssessment_form');
			}
		});
		
		$("#btnSubmit").click( function(){
			if( validate_form() == 0 ){
				if(confirm("<%=KANUtil.getProperty( request.getLocale(), "self.assessment.pm.confirm.submit", ((SelfAssessmentVO)request.getAttribute( "selfAssessmentForm" )).getEmployeeName()  )%>")){
					$("#subAction").val('submitObject');
					submit('manageInviteAssessment_form');
				}
			}
		});
	})(jQuery);
	
	function validate_form(){
		var flag = 0;
		flag = flag + validate("answer1", true, "common", 20000, 0);
		flag = flag + validate("answer2", true, "common", 20000, 0);
		flag = flag + validate("answer3", true, "common", 20000, 0);
		if(flag > 0){
			$('.error').first().focus();
		}
		return flag;
	};
</script>