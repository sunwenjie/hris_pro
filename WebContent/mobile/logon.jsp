<%@page import="com.kan.base.util.AccessToken"%>
<%@page import="com.kan.base.util.TokenThread"%>
<%@page import="com.kan.base.util.WXUtil"%>
<%@page import="net.sf.json.JSONObject"%>
<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<script src="js/jquery-1.8.2.min.js"></script>
<script src="js/kan.js"></script>
<link rel="stylesheet" href="mobile/css/bootstrap.min.css">
<style type="text/css">
	#iclick-logo{
		margin-top: 20%;
		width: 100px;
		text-align: center;
	}
</style>
<title><bean:message bundle="wx" key="wx.logon.title" /></title>
</head>
<body>
<script type="text/javascript">
    pubid=31;
    unitid=11;
    w=640;
    h=100;
</script>
<script language="javascript" src="http://static.iax.optimix.asia/js/iAX.js" ></script>
	<div class="container">
	<html:form action="securityAction.do?proc=logon_inHouse_mobile" styleClass="logon_form">
		<input type="hidden" name="clientTitle" value="iclick">
		<%
			String wxUserName = "";
			final Object obj = request.getAttribute("wxUserName");
			if(obj!=null){
			   wxUserName = String.valueOf(obj);
			}
		%>
		<input type="hidden" id="wxUserName" name="wxUserName" value="<%=wxUserName %>">
		<div class="form-group" align="left">
			<img alt="iclick-logo" id="iclick-logo" src="images/logo/iclick-logo.png">
		</div>
		<div class="form-group">
             	<label for="username" class="control-label"><bean:message key="logon.username"/>:</label>
                <input type="text" class="form-control" name="username" id="username" placeholder='<bean:message bundle="wx" key="wx.logon.placeholder.username"/>' onkeyup="tolowercase();">
         </div>
         <div class="form-group">
             	<label for="password" class="control-label"><bean:message key="logon.password"/>:</label>
                 <input type="password" class="form-control" name="password" id="password" placeholder='<bean:message bundle="wx" key="wx.logon.placeholder.password"/>' >
                 <!-- error -->
                 	<logic:present name="loginerror">
	                 	<font style="color: red">
							<bean:message bundle="wx" key="wx.logon.error"/>
						</font>
					</logic:present>
				<!-- error -->
				
         </div>
         <div class="form-group">
                <label>
      				<input type="checkbox" name="autoLogin" value="1"> <bean:message bundle="wx" key="wx.logon.autoLogin"/>
    			</label>
    				<a style="margin-left: 20px;" href="#" onclick="forgetPwd();"><bean:message bundle="wx" key="wx.logon.forget.password" /></a>
         </div>
         <div class="form-group-lg">
         	<button class="btn btn-info btn-block" type="submit"><bean:message bundle="wx" key="wx.logon.logon.button" /></button>
         </div>
	</html:form>
	</div>
</body>

<script type="text/javascript">
	(function($) {	
	})(jQuery);
	
	function tolowercase(){
		document.getElementById("username").value = document.getElementById("username").value.toLowerCase();
	};
	
	function forgetPwd(){
		$(".logon_form").attr("action","securityAction.do?proc=toForgetPwd");
		$(".logon_form").submit();
	};
</script>
</html>
