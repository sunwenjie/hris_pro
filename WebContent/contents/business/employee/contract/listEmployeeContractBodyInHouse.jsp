<%@ page import="com.kan.base.tag.AuthConstants"%>
<%@ page import="com.kan.base.tag.AuthUtils"%>
<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.web.renders.util.ListRender"%>
<%@ page import="com.kan.base.web.renders.util.SearchRender"%>
<%@ page import="com.kan.hro.web.actions.biz.employee.EmployeeContractAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<script type="text/javascript" src="js/kan.popup.workflow.js"></script>

<div id="content">	
	<!-- Information Search Form -->
	<%= SearchRender.generateSearch( request, EmployeeContractAction.getAccessAction(request, response), "employeeContractForm" ) %>
	<!-- Information Search Result -->
	<%= ListRender.generateList( request, EmployeeContractAction.getAccessAction(request, response ) ) %>
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
		<%= ListRender.generateListJS( request, EmployeeContractAction.getAccessAction(request, response ) ) %>
		//隐藏倒入按钮
		 $('#importExcel').hide();
	})(jQuery);
	
	// Reset JS of the Search
	<%= SearchRender.generateSearchReset( request, EmployeeContractAction.getAccessAction(request, response ) ) %>
	
	// loadHtml
	loadHtml('#orderId', 'clientOrderHeaderAction.do?proc=list_object_options_ajax&orderId=<bean:write name="employeeContractForm" property="encodedOrderId" />', false );
	<%
	  	if ( AuthUtils.hasAuthority( EmployeeContractAction.getAccessAction( request, null ), AuthConstants.RIGHT_SUBMIT, "", request, null ) )
	    {
		     out.print( "$('#btnSubmit').show();" );
	    }
	%>
	
	// 绑定提交事件
	$('#btnSubmit').click( function(){
		if($('#selectedIds').val() != null && $('#selectedIds').val() != ''){
			if(confirm('<bean:message bundle="public" key="popup.confirm.submit.records" />')){
				$('.list_form').attr('action', 'employeeContractAction.do?proc=submit_objects');
				submitForm('list_form', "submitObjects", null, null, null, 'tableWrapper');
				$('#selectedIds').val('');
				$('.list_form').attr('action', 'employeeContractAction.do?proc=list_object');
			}
		}else{
			alert('<bean:message bundle="public" key="popup.not.selected.records" />');
		}
	});
	
	// Esc按键事件 - 隐藏弹出框
	$(document).keyup(function(e){
	    if (e.keyCode == 27) 
	    {
	    	$('#importDIVModalId').hide();
	    }
	});
</script>
