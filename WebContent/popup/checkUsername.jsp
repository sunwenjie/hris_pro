<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ page pageEncoding="GBK" %>

<html>
<HEAD>
	<title>HRS Detail Candidate - Check Username</title>
    <link href="css/frame.css" rel="stylesheet" type="text/css">
	<link href="css/link.css" rel="stylesheet" type="text/css">
	<link href="css/font.css" rel="stylesheet" type="text/css">
</HEAD>

<body bgcolor="#EEEEEE">
<table>
	<tr height="5"><td></td></tr>
	<tr height="25">
		<td class="font1" align="center">
			The username <span class="font10"><bean:write name="username"/></span> <logic:equal name="exist" value="true">you selected has been registered, please change another one.</logic:equal><logic:equal name="exist" value="false">you selected hasn't been registered, you can use now.</logic:equal>
		</td>
	</tr>
	<tr height="10"><td></td></tr>
	<tr height="30"><td align="center"><input type="button" value="Close" onclick="window.close()"></td></tr>
</table>
</body>
</html>