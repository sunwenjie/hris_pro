<%@page import="java.util.List"%>
<%@page import="com.kan.base.domain.message.TempMessageVO"%>
<%@page import="com.kan.base.domain.workflow.WorkflowActualVO"%>
<%@page import="com.kan.base.page.PagedListHolder"%>
<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.web.action.BaseAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script src="js/jquery-1.8.2.min.js"></script>
<script src="js/kan.js"></script>
<link rel="stylesheet" href="mobile/css/mobile.css" />
<title><bean:message bundle="wx" key="wx.logon.title" /></title>
<style type="text/css">
	.biaoti2_chooseNotice a{text-decoration:none; color:#333333;}
	.biaoti2_chooseNotice a:hover{text-decoration:none; color:#333333;}
	.biaoti2_chooseNotice{width:100%; height:auto; font-size:52pt; color:#333333; padding: 20px 0px 20px 0px;}
	.biaoti2_chooseNotice b{ margin-left:20px; margin-right:18px; width:96px; height:96px; -webkit-border-radius: 48px;-moz-border-radius: 48px;border-radius: 48px; background-color:#ff6600; color:#FFFFFF; float:left; display:inline; text-align:center; font-weight:normal;}
	.biaoti2_chooseNotice span{float:right; color:#999999; font-size:30pt; margin-right:20px;}
</style>
</head>
<body>
<div class="daiban" onclick="link('leaveHeaderAction.do?proc=list_object_mobile');">
   <div id="div_leaveOt" class="biaoti2_chooseNotice" style="background: url('mobile/images/new.png') no-repeat top right; "><a href="#" ><b>1</b><bean:message bundle="wx" key="wx.choose.notice.leave.and.ot" /></a></div>
   <p></p>
</div>
<div class="daiban" onclick="link('workflowActualAction.do?proc=list_object_unfinished_mobile');">
   <div class="biaoti2_chooseNotice"><a href="#" ><b>2</b><bean:message bundle="wx" key="wx.choose.notice.to.do" /></a></div>
   <p></p>
</div>
</body>
<script type="text/javascript">
	(function($) {	
		if("<bean:write name='message_count'/>"==0){
			$("#div_leaveOt").css("background","");
		}
	})(jQuery);
</script>	
