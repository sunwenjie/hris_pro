<%@page import="com.kan.base.util.KANUtil"%>
<%@page import="com.kan.base.web.action.BaseAction"%>
<%@ page pageEncoding="GBK"%>
<%@page import="com.kan.base.util.KANConstants"%>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<link href="css/css_font_face.css" rel="stylesheet">
<style type="text/css">
	body {background:url(images/illustrations/bg.jpg) repeat fixed; font:normal normal 11px/18px "Open Sans", sans-serif; color:#413333; letter-spacing:0.2px; text-align:justify;}
	
	.error404 #main {padding:100px 0 60px;}
	#main {background:url(images/illustrations/headershadow.png) top repeat-x; width:100%; min-height:98px; display:block; margin:0; padding:0;}
	div {outline: 0px none}
	
	#error {width:482px; height:267px; margin:0 auto;}
	#errorimg {background:url(images/illustrations/error.png); width:397px; height:53px; margin:0 auto; padding:214px 0 0 85px; position:relative; z-index:3;}
	
	#errorimg a {font-weight:bold; font-size:10px; line-height:11px; text-transform:uppercase; color:#c9c1ab; text-shadow:1px 1px 0 #fff;}
	a {color:#3a2c2d; text-decoration:none;}
	#errorimg a:hover strong {color:#9cc663;}
	#errorimg a strong {
	color:#019d92;
	-webkit-transition: all 0.3s linear;
	-moz-transition: all 0.3s linear;
	-o-transition: all 0.3s linear;
	transition: all 0.3s linear;
}
.featured .column h1.title strong {font-weight:800;}
:focus {outline:0px none;}
#footer p {
	font-size: 13px;
	font-family: ºÚÌå, Calibri;
	width: 100%;
	text-align: center;
	color: #333243;
}
</style>
<script type="text/javascript">
function link(url) {
	<%
		if(KANUtil.filterEmpty(BaseAction.getRole(request,null))!=null){
		   out.print("window.location.href = url;");
		}else{
		   out.print("window.location.href = 'logoni.do';");
		}
	%>
	
};
</script>
</head>
<body>
	<div id="main">
	   	<section id="error">
	   	<div id="errorimg" style="display:block;">
	       <a href="javascript:void(0);" onclick="link('dashboardAction.do?proc=showDashboard');" title="Home page">
	       &raquo; Woopsie. Page not found! Go back to <strong>home page</strong>! &laquo;
	       </a>
	   </div>
	   </section>
   </div>
   <div id="footer">
	<p>&copy;iClick</p>
</div> 
</body>
</html>
  