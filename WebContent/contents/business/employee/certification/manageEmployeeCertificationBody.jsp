<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@page import="com.kan.base.web.renders.util.ManageRender"%>

<div id="content">
	<!-- Information Manage Form -->
	<%= ManageRender.generateManage( request, "HRO_BIZ_EMPLOYEE_CERTIIFICATION", "employeeCertificationForm", true ) %>
</div>

<script type="text/javascript">
	(function($) {
		
		 //选中雇员菜单
		 $('#menu_employee_Modules').addClass('current');
		 $('#menu_employee_Employee').addClass('selected');
		
		// JS of the List
		<%= ManageRender.generateManageJS( request, "HRO_BIZ_EMPLOYEE_CERTIIFICATION", null, null, null ) %>
		
		$('#btnList').val('<bean:message bundle="public" key="button.back.fh" />');
		//重新绑定List按钮事件
		 $('#btnList').unbind( "click" );
		 $('#btnList').click(function() {
			 if(agreest())
	        link('employeeAction.do?proc=to_objectModify&id=<bean:write name="employeeCertificationForm"  property="encodedEmployeeId"/>');
	     });
		 
		 //雇员已有的证书奖励变色
		 $.post("employeeCertificationAction.do?proc=list_employeeCertificationsIDs_jsonArray",{employeeId:$("#employeeId").val()},function(data){
			 	$("#certificationId option").each(function(){
					for(var i = 0 ;i<data.length;i++){
	               	   if(data[i] == $(this).val()){
	               		   $(this).css("color","#999");
	               		   $(this).addClass("certificationId_exist");
	               	   }
	                }
				});
			},'json');
		 
		 //选择已有雇员的奖惩奖励弹出提示
		 $("#certificationId").change(function(){
			 if($("#certificationId :selected").hasClass('certificationId_exist')){
				 if ( confirm ("你所选择的奖惩已存在!\r\n点击'确定'查看该奖惩,点击'取消'返回编辑")){
					 link('employeeAction.do?proc=to_objectModify&id=<bean:write name="employeeCertificationForm"  property="encodedEmployeeId"/>');
					$('#certificationId').val(0);
				 }else{
					 $('#certificationId').val(0);
				 }
			 }
			 
		 });
		 
		//用来显示雇员姓名和id的链接 
		 $('.required').parent().after(
				 '<ol class="auto">'
				 +'<li id="employeeIdLI" style="width: 50%;"><label><logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.id" /></logic:equal><logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.id" /></logic:equal><em> *</em></label> <a href="javascript:void(0)" onclick="link(\'employeeAction.do?proc=to_objectModify&id=<bean:write name='employeeCertificationForm' property='encodedEmployeeId' />\');"  ><bean:write name="employeeCertificationForm" property="employeeId"  /></a></li>'
				 +'<li id="employeeNameLI" style="width: 50%;"><label><logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.name" /></logic:equal><logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.name" /></logic:equal><em> *</em></label> <a href="javascript:void(0)" onclick="link(\'employeeAction.do?proc=to_objectModify&id=<bean:write name='employeeCertificationForm' property='encodedEmployeeId' />\');" ><bean:write name="employeeCertificationForm" property="employeeName" /></a></li>'
				 +'</ol>'
		 );		
	})(jQuery);
</script>

