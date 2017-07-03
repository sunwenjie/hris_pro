<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
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
.form_primary {
	padding-top: 10%;
}
</style>
</head>
<body>
	<div class="form_primary" align="center">
	<logic:equal value="success" name="resetResult">
		<font color="green">操作成功,重置密码的邮件已发送到您关联的邮箱,请查收</font><br />
		Success,A mail with a reset password link had sent to your associated mailbox , please check
	</logic:equal>
	<logic:notEqual value="success" name="resetResult">
		<font color="red">操作失败,没有找到关联的用户信息,请核实您的邮箱或联系系统管理员</font><br />
		Error,can't find your account info.Please check your email or contact system manager
	</logic:notEqual>
	</div>
</body>
</html>
<script type="text/javascript">
	(function($) {
	})(jQuery);
</script>