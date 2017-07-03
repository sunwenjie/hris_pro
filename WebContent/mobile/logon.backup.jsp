<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.web.action.BaseAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script src="js/jquery-1.8.2.min.js"></script>
<link rel="stylesheet" href="mobile/css/mobile.css" />
<title><bean:message bundle="wx" key="wx.logon.title" /></title>
<body>
<div id="login">
    <div id="layout">
    <html:form action="securityAction.do?proc=logon_inHouse_mobile" styleClass="logon_form">
		<div align="center"><img src="mobile/images/KANpower-logo.png"  class="logo"/></div>
		<div class="waikuang">
				  <div class="neirong1"><input id="clientTitle" name="clientTitle" type="text" value="<bean:message bundle="wx" key="wx.logon.client.name" />" class="shurukuang4" onfocus="if(value=='<bean:message bundle="wx" key="wx.logon.client.name" />') {value=''}" onblur="if (value=='') {value='<bean:message bundle="wx" key="wx.logon.client.name" />'}"/>
				  </div>
				  <div class="neirong1"><input id="username" name="username" type="text" value="<bean:message bundle="wx" key="wx.logon.user.name" />" class="shurukuang4" onfocus="if(value=='<bean:message bundle="wx" key="wx.logon.user.name" />') {value=''}" onblur="if (value=='') {value='<bean:message bundle="wx" key="wx.logon.user.name" />'}"/>
				  </div>
				  <div class="neirong5"><input id="password" name="password" value="<bean:message bundle="wx" key="wx.logon.user.password" />" type="text" class="shurukuang4" onfocus="if(this.value==defaultValue) {this.value='';this.type='password'}" onblur="if(!value) {value=defaultValue; this.type='text';}"/>
				  </div> 
		</div>
		<div class="neirong4" style="display: none"><span><input name="cbPersistentCookie" type="checkbox" value="1" checked /> 保存用户名</span>
		    <sup></sup>
		</div>
		<a href="javascript:logon();" class="button orange"><bean:message bundle="wx" key="wx.logon.logon.button" /></a>
	</html:form>
	<br clear="all" />
	</div>
</div>

</body>						
<script type="text/javascript">
	(function($) {		
		/* var userCookie = getcookie("KANUser");
		alert(userCookie);
		var userInfos = userCookie.split("##");
		if(userInfos.length==3){
			$("#clientTitle").val(userInfos[0]);
			$("#username").val(userInfos[1]);
		}
		//$(".shurukuang4").val(test); */
	})(jQuery);
	 
	/**
	* 自定义函数
	**/
	function logon(){
		$(".logon_form").submit();
	}

	function getcookie(objname){//获取指定名称的cookie的值
		var arrstr = document.cookie.split("; ");
		for(var i = 0;i < arrstr.length;i ++){
		var temp = arrstr[i].split("=");
		if(temp[0] == objname) return unescape(temp[1]);
		}
	}
	
</script>

