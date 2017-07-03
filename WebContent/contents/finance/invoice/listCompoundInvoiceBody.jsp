<%@ page import="com.kan.base.util.KANConstants"%>
<%@ page pageEncoding="GB2312"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<div id="content">
 		<!-- subInvoice-Information -->
  	<div class="box" id="subInvoice-Information" >
  		<div class="head">
			<label>系统发票 - 合并</label>
		</div>
		<div class="inner">	
			<div id="messageWrapper">
			</div>
			<div class="top">						
				<input type="button" class="btnAdd" id="btnAdd" name="btnAdd" value="<bean:message bundle="public" key="button.add" />" >
				<input type="button" class="reset" id="btnList" name="btnList" value="<bean:message bundle="public" key="button.list" />" >
				<input type="button" class="delete" name="btnCancel" id="btnCancel" value="取消" style="display:none;"/>
			</div>	
			 <div id="detailFormWrapper" style="display:none" >
				<!-- Include Form JSP 包含Form对应的jsp文件 --> 
				<jsp:include page="/contents/finance/invoice/manageCompoundInvoice.jsp" flush="true"/>
			</div>
			
			<logic:notEmpty name="headerListHolder">														
				<html:form action="systemInvoiceHeaderAction.do?proc=combineInvoice" styleClass="listComInvoice_form">
					<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="headerListHolder" property="sortColumn" />" /> 
					<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="headerListHolder" property="sortOrder" />" />
					<input type="hidden" name="page" id="page" value="<bean:write name="headerListHolder" property="page" />" />
					<input type="hidden" name="selectedIds" id="selectedIds" value="" />
					<input type="hidden" name="invoiceId" id="invoiceId" value="<bean:write name="headerForm" property="selectedIds" />" />					
				<div id="tableWrapper">
					<!-- Include table jsp 包含tabel对应的jsp文件 -->  
					<jsp:include page="/contents/finance/invoice/table/listCompoundInvoiceTable.jsp" flush="true"/>
				</div>
				</html:form>
			</logic:notEmpty>	
		</div>
  	</div>
	
</div>
<!-- Employee Popup Box -->
<div id="popupWrapper">
	<jsp:include page="/popup/searchClient.jsp"></jsp:include>
	<jsp:include page="/popup/searchOrder.jsp"></jsp:include>
	<jsp:include page="/popup/searchEmployee.jsp"></jsp:include>
	<jsp:include page="/popup/searchContract.jsp"></jsp:include>
</div>

<script type="text/javascript">
 (function($) {
		
		$('#menu_finance_Modules').addClass('current');			
		$('#menu_finance_Merge').addClass('selected');
		// 初始化多选框
		kanList_init();
		kanCheckbox_init();
		
		// Header 列表
	 	$('#btnList').click( function () {
			link('systemInvoiceHeaderAction.do?proc=list_object&pageFlag=Merge');
		});	 
		
		$('#btnAdd').click(function (){
			if($('#btnAdd').val()=="添加"){
				$('#btnAdd').val("保存");
				$('#btnCancel').show();
				$('#detailFormWrapper').show();
				$(".subInvoice_monthly").val("<bean:write name='headerForm' property='monthly'/>");
				$(".subInvoice_billAmountCompany").val("<bean:write name='headerForm' property='billAmountCompany'/>");
				$(".subInvoice_costAmountCompany").val("<bean:write name='headerForm' property='costAmountCompany'/>");
				$(".subInvoice_status").val(1);
			}else if($('#btnAdd').val()=="保存"){
				if( validate_manage_secondary_form() == 0 ){
					submit('subInvoice_form');
				}
			}
		});
		
		$('#btnCancel').click(function(){
			$('#btnAdd').val("添加");
			$('#btnCancel').hide();
			$('#detailFormWrapper').hide();
			$(".subInvoice_monthly").val(0);
			$(".subInvoice_entityId").val(0);
			$(".subInvoice_businessTypeId").val(0);
			$(".subInvoice_clientId").val("");
			$(".subInvoice_orderId").val("");
			$(".subInvoice_billAmountCompany").val(0);
			$(".subInvoice_costAmountCompany").val(0);
			$(".subInvoice_description").val("");
			$(".subInvoice_status").val(1);
		});
		
	})(jQuery);
	
	
	
	// Get SubAction Value
	function getSubAction(){
		return $('.compoundInvoice_form input#subAction').val();
	}; 
</script>