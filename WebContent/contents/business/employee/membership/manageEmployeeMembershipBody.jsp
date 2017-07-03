<%@page import="com.kan.hro.domain.biz.employee.EmployeeMembershipVO"%>
<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@page import="com.kan.base.web.renders.util.ManageRender"%>

<div id="content">
	<!-- Information Manage Form -->
	<%= ManageRender.generateManage( request, "HRO_BIZ_EMPLOYEE_MEMBERSHIP", "employeeMembershipForm", true ) %>
</div>
							
<script type="text/javascript">
	(function($) {	
		// JS of the List
		<%= ManageRender.generateManageJS( request, "HRO_BIZ_EMPLOYEE_MEMBERSHIP", null, null, null ) %>
		
		$("#btnList").val('<bean:message bundle="public" key="button.back.fh" />');
		//重新绑定取消按钮事件
		 $('#btnList').unbind( "click" );
		 $('#btnList').click(function() {
			 <%
			 final EmployeeMembershipVO employeeMembershipVO = (EmployeeMembershipVO)request.getAttribute("employeeMembershipForm");
			 final String employeeId = employeeMembershipVO.getEmployeeId();
			 final String targateId = employeeMembershipVO.encodedField(employeeId);
			 %>
	        link('employeeAction.do?proc=to_objectModify&id=<%=targateId%>');
	     });
		 
		 
		 //雇员已有的社会活动变色
		 $.post("employeeMembershipAction.do?proc=get_exist_employeeMemberships",{employeeId:$("#employeeId").val()},function(data){
				var existEmployeeMemberships = data.split("#");
				//遍历下拉框
				$("#membershipId").each(function(){
					$(this).children("option").each(function(){

						for(var i = 0 ;i<existEmployeeMemberships.length;i++){
		                	   var existEmployeeMembership = existEmployeeMemberships[i].split(":");
		                	   if(existEmployeeMembership[0] == $(this).val()){
		                		   var curId = $(this).val();
		                		   $("#option_membershipId_"+curId).css("color","#999");
		                		  // $("#option_membershipId_"+curId).remove();
		                		   
		                	   }
		                   }

					});
				});
			},"text");
		 
		 // 选择雇员已有的社会活动弹出提示
		 $("#membershipId").change(function(){
			 var membershipId = this.value;
			 var employeeId = $("#employeeId").val();
			 $.post("employeeMembershipAction.do?proc=is_employeeMembership_exist",{employeeId:employeeId,membershipId:membershipId},function(data){
				 if(data!=null&&data!=''){
					  if ( confirm ("你所选择的社团活动已存在!\r\n点击'确定'查看该活动,   点击'取消'返回编辑")){
						window.location.href = "employeeMembershipAction.do?proc=to_objectModify&id="+data;
						$('#membershipId')[0].selectedIndex = 0; 
					 }else{
						 $('#membershipId')[0].selectedIndex = 0; 
					 } 
				 }
			 },"text");
		 });
		 
		//用来显示雇员姓名和id的链接 
		 $('.required').parent().after(
				 '<ol class="auto">'
				 +'<li id="employeeIdLI" style="width: 50%;"><label><logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.id" /></logic:equal><logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.id" /></logic:equal><em> *</em></label> <a href="javascript:void(0)" onclick="link(\'employeeAction.do?proc=to_objectModify&id=<bean:write name='employeeMembershipForm' property='encodedEmployeeId' />\');"  ><bean:write name="employeeMembershipForm" property="employeeId"  /></a></li>'
				 +'<li id="employeeNameLI" style="width: 50%;"><label><logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.name" /></logic:equal><logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.name" /></logic:equal><em> *</em></label> <a href="javascript:void(0)" onclick="link(\'employeeAction.do?proc=to_objectModify&id=<bean:write name='employeeMembershipForm' property='encodedEmployeeId' />\');" ><bean:write name="employeeMembershipForm" property="employeeName" /></a></li>'
				 +'</ol>'
		 );		
	})(jQuery);
</script>

