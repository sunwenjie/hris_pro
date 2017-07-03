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
	<logic:equal value="success" name="success">
		<font color="green">�����޸ĳɹ�,΢���Զ���¼��Ҫ���°�,�����µ�¼</font><br />
		Password modification success,auto login required to rebind,please login again
	</logic:equal>
	<logic:notEqual value="success" name="success">
		<font color="red">�����޸�ʧ��,������ĵ�ǰ��������.������������������ϵϵͳ����Ա</font><br />
		Password change failed, your current password is error.other failure to contact the system administrator<br /><br />
	</logic:notEqual>
	<br/>
	<a href="#" onclick="relogon();"> 
	<bean:message bundle="wx" key="wx.logon.relogon"/></a>
	</div>
</body>
<script type="text/javascript">
	(function($) {
	})(jQuery);
	
	function relogon(){
		window.location.href="logonim.do";
	};
</script>
</html>