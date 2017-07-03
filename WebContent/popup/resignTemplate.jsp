<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ page import="com.kan.base.web.action.BaseAction"%>
<%@ page import="com.kan.hro.web.actions.biz.employee.EmployeeContractAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>

<!-- Module Box HTML: Begins -->
<div class="popup modal small content hide" id="employeeContractResignTemplateId">
    <div class="modal-header" id="employeeContractHeader" style="cursor:move;">
        <a class="close" data-dismiss="modal" onclick="$('#employeeContractResignTemplateId').addClass('hide');$('#shield').hide();" title="<bean:message bundle="public" key="button.close" />">×</a>
        <label><bean:message bundle="business" key="business.select.file.template" /></label>
    </div>
    
    <div class="modal-body">
    	<div class="top">
    		<kan:auth action="<%=EmployeeContractAction.getAccessAction(request,response)%>" right="export">
	   			<input type="button" class="export" name="btnPopupExportResign" id="btnPopupExportResign" value="<bean:message bundle="public" key="button.print" />" onclick="exportResign();"/>
	   		</kan:auth>
	    </div>
	    <html:form action="resignTemplateAction.do?proc=exportResign" styleClass="exportResign_form">
        	<div class="toggableForm">
	        	<%= BaseAction.addToken( request ) %>
	        	<input type="hidden" id="contractId" name="contractId" value="<bean:write name="employeeContractForm" property="contractId"/>"/>
	        	<input type="hidden" id="status" name="status" value="<bean:write name="employeeContractForm" property="status"/>"/>
	        	<ol class="auto">
		        	<li>
						<select id="resignTemplateId" name="resignTemplateId">
						</select>
					</li>
				</ol>
			</div>
		</html:form>
    </div>
</div>
<!-- Module Box HTML: Ends -->

<script type="text/javascript">
	// Esc按键事件 - 隐藏弹出框
	$(document).keyup(function(e){
	    if (e.keyCode == 27) 
	    {
	    	$('#employeeContractResignTemplateId').addClass('hide');
	    	$('#shield').hide();
	    }
	});
	
	// 显示退工单模版
	function showExportResign(contractId,status){
		$('#employeeContractResignTemplateId').removeClass('hide');
    	$('#shield').show();
    	$(".exportResign_form #contractId").val(contractId);
    	$(".exportResign_form #status").val(status);
    	
    	$.post("resignTemplateAction.do?proc=list_object_ajax&status="+status,{},function(data){
			$("#resignTemplateId").html(data);
		},"text");
	};
	
	// 导出退工单PDF
	function exportResign(){
		if ($("#resignTemplateId")[0].value == 0) {
			alert('<bean:message bundle="public" key="popup.not.selected.option" />');
		}else{
			$(".exportResign_form").submit();
			$('#employeeContractResignTemplateId').addClass('hide');
			$('#shield').hide();
		}
	}
</script>