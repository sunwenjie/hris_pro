<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<script src="js/jquery-1.8.2.min.js"></script>
<script src="js/kan.js"></script>
<link rel="stylesheet" href="mobile/css/bootstrap.min.css">
<title>HRIS - Forget password</title>
<style type="text/css">
	.form_primary{
		padding-top: 20%;
	}
</style>
</head>
<body>
	<div class="container">
	<logic:present name="wxUserEmail">
		<html:form action="securityAction.do?proc=forgetPassword" styleClass="form_primary">
			<div class="form-group">
             	<label for="username" class="control-label">
             		<bean:message bundle="wx" key="wx.logon.label.username.mail"/><br/>
             		<bean:message bundle="wx" key="wx.logon.label.username.mail.sub"/>
             		:</label>
    		            <input type="text" readonly="readonly" class="form-control" name="username" id="username" value='<bean:write name="wxUserEmail" />'>
         	</div>
         	<div class="form-group-lg">
         		<button class="btn btn-info btn-block" type="submit"><bean:message bundle="wx" key="wx.btn.confirm" /></button>
         	</div>
		</html:form>
		</logic:present>
		<logic:notPresent name="wxUserEmail">
			<div style="padding-top: 20%;" align="center">
				<bean:message bundle="wx" key="wx.reset.error.confirm" />
			</div>
		</logic:notPresent>
	</div>
</body>	
</html>
<script type="text/javascript">
	(function($) {
	})(jQuery);	
	
</script>