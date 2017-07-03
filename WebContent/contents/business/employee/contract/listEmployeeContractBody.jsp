<%@ page import="com.kan.hro.web.actions.biz.employee.EmployeeSecurityAction"%>
<%@ page import="com.kan.hro.domain.biz.employee.EmployeeContractVO"%>
<%@ page import="com.kan.base.tag.AuthConstants"%>
<%@ page import="com.kan.base.tag.AuthUtils"%>
<%@ page import="com.kan.hro.web.actions.biz.employee.EmployeeContractAction"%>
<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ page import="com.kan.base.web.renders.util.ListRender"%>
<%@ page import="com.kan.base.web.renders.util.SearchRender"%>
<%@ page import="com.kan.base.web.renders.util.EmployeeUserManageRender"%>
<script type="text/javascript" src="js/kan.popup.workflow.js"></script>

<%
	boolean isServiceContract = "2".equals( request.getParameter( "flag" ) );
	if( !isServiceContract )
	{
	   isServiceContract = "2".equals( request.getAttribute( "flag" ) );
	}
	final String accessAction =  isServiceContract ? EmployeeContractAction.getAccessAction(request, response) : EmployeeContractAction.accessActionLabor;
%>

<div id="content">	
	<!-- Information Search Form -->
	<%= SearchRender.generateSearch( request, isServiceContract ? EmployeeContractAction.getAccessAction(request, response) : EmployeeContractAction.accessActionLabor, "employeeContractForm" )%>
	<!-- Information Search Result -->
	<%= ListRender.generateList( request, isServiceContract ? EmployeeContractAction.getAccessAction(request, response) : EmployeeContractAction.accessActionLabor ) %>
</div>

<div id="handlePopupWrapper">
	<jsp:include page="/popup/handleEmployeeContract.jsp"></jsp:include>
</div>
<div id="resignTemplatePopupWrapper">
	<jsp:include page="/popup/resignTemplate.jsp"></jsp:include>
</div>

<script type="text/javascript">	
	(function($) {
		// JS of the List
		<%= ListRender.generateListJS( request, accessAction ) %>
		<logic:equal name="role" value="5">
			$('#menu_employee_ServiceAgreement').addClass('current');
        </logic:equal>
		// 添加隐藏表单域-标记是劳动合同还是服务协议
		var isServiceContract = <%=isServiceContract%>;
		if(isServiceContract){
			$(".list_form").append('<input type="hidden" value="2" name="flag"');
		}else{
			$(".list_form").append('<input type="hidden" value="1" name="flag"');
		}
		
		<%
		  if ( AuthUtils.hasAuthority( accessAction, AuthConstants.RIGHT_SUBMIT, "", request, null ) )
	      {
		     out.print( "$('#btnSubmit').show();" );
	      }
		%>
		//雇员登陆限制隐藏一些字段
		<%= EmployeeUserManageRender.generateDisplayBtnJS( request, "HRO_BIZ_EMPLOYEE_SERVICE_CONTRACT")%>
		
		// 绑定提交事件
		$('#btnSubmit').click( function(){
			if($('#selectedIds').val() != null && $('#selectedIds').val() != ''){
				if(confirm("确定提交选中？")){
					$('.list_form').attr('action', 'employeeContractAction.do?proc=submit_objects');
					submitForm('list_form', "submitObjects", null, null, null, 'tableWrapper');
					$('#selectedIds').val('');
					$('.list_form').attr('action', 'employeeContractAction.do?proc=list_object');
				}
			}else{
				alert("请选择要提交的记录！");
			}
		});
	})(jQuery);

	// Reset JS of the Search
<%= SearchRender.generateSearchReset( request, isServiceContract?EmployeeContractAction.getAccessAction(request, response):EmployeeContractAction.accessActionLabor ) %>
</script>
