<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%
	response.setHeader("Cache-Control","no-store");
	response.setHeader("Pragma","no-cache");
	response.setDateHeader ("Expires", 0);
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

<style type="text/css">
	div#submit-success{ font-size: 13px; font-family: 黑体, Calibri; color: #5d5d5d; text-align: center; }
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
	
		<div id="content" style="padding-top: 0px; text-align: center; min-height: 300px;">
		
			<div id="submit-success" class="box">	
				<p>
					<bean:message bundle="performance" key="invite.link.failure.or.expired" />
				</p>
			</div>
			
		</div>
		
		<jsp:include page="/common/footer.jsp"></jsp:include>
	</div>	
</body>