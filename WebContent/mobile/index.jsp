<%@page import="com.kan.base.domain.MappingVO"%>
<%@page import="java.util.List"%>
<%@page import="com.kan.base.util.KANConstants"%>
<%@page import="com.kan.base.util.KANUtil"%>
<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.web.action.BaseAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script src="js/jquery-1.8.2.min.js"></script>
<link rel="stylesheet" href="mobile/css/mobile.css" />
<link rel="stylesheet" href="mobile/css/bootstrap.min.css">
<link rel="stylesheet" href="mobile/css/font-awesome.min.css">
<script src="js/kan.js"></script>
<title><bean:message bundle="wx" key="wx.logon.title" /></title>
</head>

<style type="text/css">
	.icon-user{
		margin: 20px 10px 0px 0px;
		float: right;
	}
	
	.icon-caret-down{
		margin: 25px 20px 0px 0px;
		float: right;
	}
	
	.oper-div{
		top:65px;
		right:20px;
		position:absolute;
		float:right;
		padding: 10px;
		display: block;
		background-color: #f5f5f5;
	}
	ul{
		margin-bottom: 0px;
	}
	
	li{
		text-decoration: none;
		font-size:3.0em;
		color: black;
		list-style: none;
		padding: 10px;
	}
	li a{
		color: black;
	}
	
</style>

<body>
<div id="mobile">
	<input type="hidden" id="message_count" value="0">
	<div id="top">
		 <i class="icon-caret-down" style="font-size: 40px; color: white;" onclick="showOper();"></i>
		 <i class="icon-user" style="font-size: 40px; color: white;" onclick="showOper();"></i>
		 <div class="oper-div" id="oper-div" style="display: none;">
		 	<ul>
		 		<li>
		 			<i class="icon-key" style="font-size: 40px; color: #49afcd;" onclick="showOper();"></i>
		 			<a id="changePwd" href="#" onclick="changePwd();"><bean:message bundle="security" key="security.user.password.change" /></a></li>
		 		<li>
		 			<i class="icon-signout" style="font-size: 40px; color: #49afcd;" onclick="showOper();"></i>
		 			<a id="logout" href="#" onclick="logout();"><bean:message bundle="wx" key="wx.logon.logout" /></a></li>
		 	</ul>
			</div>	
	</div>
	<table class="table1" height="55%">
	   <tr>
		   <td id="right_3" bgcolor="#f08405" onclick="link('leaveHeaderAction.do?proc=to_objectNew_mobile');"><a><bean:message bundle="wx" key="wx.index.leave.apply" /></a></td>
		   <td id="right_2" bgcolor="#ec650c" width="50%" ><a><bean:message bundle="wx" key="wx.index.notice" /></a><span id="sys_taskInfo">0</span></td>
	   </tr>
	   <tr>
	       <td id="right_1" bgcolor="#ea5413" width="50%" onclick="link('staffAction.do?proc=to_objectModify_mobile');"><a><bean:message bundle="wx" key="wx.index.base.info" /></a></td>
		   <td id="right_6" bgcolor="#f4a403" onclick="link('paymentHeaderAction.do?proc=list_object_mobile');"><a><bean:message bundle="wx" key="wx.index.salary.query" /></a></td>
	   </tr>
	   <tr style="display: none;">
		   <!-- <td id="right_5" bgcolor="#f8c50c" onclick="link('sbHeaderAction.do?proc=list_object_mobile');"><a>社保公积金查询</a></td>
		   -->
		   <td id="right_5" bgcolor="#f8c50c" onclick="alert('<bean:message bundle="wx" key="wx.coding" />');return false;"><a><bean:message bundle="wx" key="wx.index.gps.attendance" /></a></td>
		   <td id="right_4" bgcolor="#f4a403" onclick="link('otHeaderAction.do?proc=to_objectNew_mobile');"><a><bean:message bundle="wx" key="wx.index.overtime.apply" /></a></td>
		</tr>
	</table>
</div>
</body>

<script type="text/javascript">
	var TASKINFO_HANDLER =  function (){
		$.ajax({
			url: 'workflowActualAction.do?proc=get_notReadCount_mobile&date='+new Date(),
			success: function(html){
				// arr[0] 为 工作流 1 为新增的请假加班
				var arr = html.split("##");
				$("#sys_taskInfo").html(Number(arr[0])+Number(arr[1]));
				$("#message_count").val(arr[1]);
			}
		});
	};
	
	(function($) {	
		// 毫秒，3分钟定时刷新一次
		var refreshTime = 1000 * 180;
		TASKINFO_HANDLER();
		setInterval( TASKINFO_HANDLER, refreshTime );
		<%
			String clientMobileModuleRights = "";
			if(KANConstants.ROLE_IN_HOUSE.equals(BaseAction.getRole(request,null))){
			   // 如果是INHOUSE
			  clientMobileModuleRights  = KANConstants.getKANAccountConstants(BaseAction.getAccountId(request,null)).getClientMobileModuleRightsByCorpId(BaseAction.getCorpId(request,null));
			}else{
			   // HRSERVICE
			  clientMobileModuleRights  = KANConstants.getKANAccountConstants(BaseAction.getAccountId(request,null)).OPTIONS_MOBILE_RIGHTS;
			}
			String[] checkIds = null;
			if ( KANUtil.filterEmpty( clientMobileModuleRights ) != null )
			{
			     checkIds = KANUtil.jasonArrayToStringArray( clientMobileModuleRights );
			}
		    for(int i = 1;i<=6;i++){
		      boolean exist = false; 
		      if(checkIds!=null){
		         for(String right:checkIds){
			         if(right.equals(i+"")){
			            exist = true;
			            break;
			         }
			      }  
		      }
		      // 如果没有权限
		      if(!exist){
		         %>
		         $("#right_<%=i%>").attr("onclick","");
		         $("#right_<%=i%>").css({"background-color":"#d3d3d3"});
		         <%
		      }
		   }
		%>
		
		$("#right_2").click(function(){
			link('workflowActualAction.do?proc=chooseNotice&message_count='+$("#message_count").val());
		});
	})(jQuery);
	
	function logout(){
		window.location.href="securityAction.do?proc=logout_mobile";
	};

	function changePwd(){
		window.location.href="securityAction.do?proc=to_changePwd_mobile";
	};
	
	function showOper(){
		var display =$('#oper-div').css('display');
		if(display == 'none'){
			$('#oper-div').show();
		}else{
			$('#oper-div').hide();
		}
	}
</script>

