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
<html:form action="securityAction.do?proc=choosePosition_mobile" styleClass="login_form">
<div id="layout" style="margin-top: 20%;">
	<div class="waikuang">
         <div class="neirong1">
         	<logic:notEmpty name="positions" >
				<select name="positionId" id="positionId" class="shurukuang9">
				<logic:iterate id="positions" name="positions"  indexId="number">
					<option value='<bean:write name="positions" property="mappingId"/>'><bean:write name="positions" property="mappingValue"/></option>
				</logic:iterate>
				</select>
			</logic:notEmpty>
	 	 </div>
	</div>
	<br/><br/>
	<a href="#" class="button orange" onclick="choosePosition();">È· ¶¨</a>
	<br clear="all" />
</div>
</html:form>
</body>

<script type="text/javascript">
	function choosePosition(){
		$(".login_form").submit();
	}
</script>
