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
<script src="js/kan.validatePwd.js"></script>
<script src="js/kan.thinking.js"></script>
<link rel="stylesheet" href="mobile/css/bootstrap.min.css">
<link rel="stylesheet" href="mobile/css/font-awesome.min.css">
<title>HRIS - Forget password</title>
<style type="text/css">
	.form_primary{padding-top: 10px;}
	.error{color: red;}
	.password-info{background-color: #FFDEAD;padding:10px;}
	.icon-right{color: green;}
	.icon-wrong{color: red;}
	.li-hide{display: none;}
</style>
</head>
<body>
	<div class="container">
		<html:form action="securityAction.do?proc=changePwd_mobile" styleClass="form_primary">
			
			<div class="form-group password-info">
				<label><bean:message bundle="wx" key="wx.change.pwd.label1" />:</label><br/>
				
					<div class="warning">
					<ul>
						<li><bean:message bundle="wx" key="wx.change.pwd.label2" /></li>
						<li><bean:message bundle="wx" key="wx.change.pwd.label3" /></li>
						<li><bean:message bundle="wx" key="wx.change.pwd.label5" /></li>
						<li><bean:message bundle="wx" key="wx.change.pwd.label6" />!@#$%^&*</li>
						<li class="li-hide"><bean:message bundle="wx" key="wx.change.pwd.label7" /></li>
					</ul>
					</div>
			</div>
			
			
			<div class="form-group">
             	<label for="password" class="control-label">
             		<bean:message bundle="security" key="security.user.current.passwork"/>
             	</label>
                <input type="password" class="form-control password" name="password" id="password" >
         	</div>
			<div class="form-group">
             	<label for="newpassword" class="control-label">
             		<bean:message bundle="security" key="security.user.new.password"/>
             	</label>
                <input type="password" class="form-control newpassword" name="newpassword" id="newpassword" onkeyup="reg_password($(this).val());">
         	</div>
         	<div class="form-group">
             	<label for="confirmnewpassword" class="control-label">
             		<bean:message bundle="security" key="security.user.confirm.new.password"/>
             	</label>
                <input type="password" class="form-control confirmnewpassword" name="confirmnewpassword" id="confirmnewpassword" onkeyup="reg_confirm_password($(this).val());">
         	</div>
         	<div class="form-group-lg">
         		<button class="btn btn-block" id="pwd_update" type="button" onclick="savePassword();"><bean:message bundle="wx" key="wx.change.pwd.submit" /></button>
         	</div>
		</html:form>
	</div>
</body>

</html>