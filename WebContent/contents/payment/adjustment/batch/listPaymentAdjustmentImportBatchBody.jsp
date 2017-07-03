<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ page import="com.kan.hro.web.actions.biz.payment.PaymentAdjustmentImportBatchAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://kangroup/authTag" prefix="kan"%>

<div id="content">	
	<!-- Information Search Form -->
	<div class="box searchForm toggableForm" id="Search-Information">
		<div class="head">
       		<label>工资调整导入</label>
   	    </div>
   	    <a class="toggle tiptip" title="<bean:message key="public.hideoptions"/>">&gt;</a>
   	    <div id="searchDiv" class="inner" style="display: none;">
   	    	<div class="top">
	            <input type="button" id="searchBtn" name="searchBtn" value="<bean:message bundle="public" key="button.search" />" onclick="submitForm('searchPaymentAdjustmentImportBatch_form', 'searchObject', null, null, null, null);" />
	            <input type="button" class="reset" id="resetBtn" name="resetBtn" value="<bean:message bundle="public" key="button.reset" />" onclick="resetForm();" />
       		</div>
       		<html:form action="paymentAdjustmentImportBatchAction.do?proc=list_object" method="post" styleClass="searchPaymentAdjustmentImportBatch_form">
       			<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="paymentAdjustmentImportBatchHolder" property="sortColumn" />" /> 
				<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="paymentAdjustmentImportBatchHolder" property="sortOrder" />" />
				<input type="hidden" name="page" id="page" value="<bean:write name="paymentAdjustmentImportBatchHolder" property="page" />" />
				<input type="hidden" name="selectedIds" id="selectedIds" value="<bean:write name="paymentAdjustmentImportBatchHolder" property="selectedIds" />" />
				<input type="hidden" name="subAction" id="subAction" value="" />
				<fieldset>
					<ol class="auto">
						<li>
							<label><logic:equal name="role" value="1">雇员</logic:equal><logic:equal name="role" value="2">员工</logic:equal>ID</label>
							<html:text property="employeeId" maxlength="11" styleClass="searchPaymentAdjustmentImportBatch_employeeId" /> 
						</li>
						<li>
							<label><logic:equal name="role" value="1">雇员</logic:equal><logic:equal name="role" value="2">员工</logic:equal>姓名（中文）</label>
							<html:text property="employeeNameZH" maxlength="100" styleClass="searchPaymentAdjustmentImportBatch_employeeNameZH" /> 
						</li>
						<li>
							<label><logic:equal name="role" value="1">雇员</logic:equal><logic:equal name="role" value="2">员工</logic:equal>姓名（英文）</label>
							<html:text property="employeeNameEN" maxlength="100" styleClass="searchPaymentAdjustmentImportBatch_employeeNameEN" /> 
						</li>
						<logic:equal name="role" value="1">
							<li>
								<label>客户ID</label>
								<html:text property="clientId" maxlength="11" styleClass="searchPaymentAdjustmentImportBatch_clientId" /> 
							</li> 
							<li>
								<label>客户名称（中文）</label>
								<html:text property="clientNameZH" maxlength="100" styleClass="searchPaymentAdjustmentImportBatch_clientNameZH" /> 
							</li>
							<li>
								<label>客户名称（英文）</label>
								<html:text property="clientNameEN" maxlength="100" styleClass="searchPaymentAdjustmentImportBatch_clientNameEN" /> 
							</li>
						</logic:equal>
						<logic:equal name="role" value="1">
							<li>
								<label>订单ID</label>
								<html:text property="orderId" maxlength="10" styleClass="searchPaymentAdjustmentImportBatch_orderId" /> 
							</li>
						</logic:equal>
						<logic:equal name="role" value="2">
							<li>
								<label>结算规则ID</label>
			   					<logic:notEmpty name="clientOrderHeaderMappingVOs">
									<html:select property="orderId" styleClass="searchPaymentAdjustmentImportBatch_orderId">
										<html:optionsCollection name="clientOrderHeaderMappingVOs" value="mappingId" label="mappingValue" />
									</html:select>
			   					</logic:notEmpty>
			   					<logic:empty name="clientOrderHeaderMappingVOs">
			   						<html:text property="orderId" maxlength="10" styleClass="searchPaymentAdjustmentImportBatch_orderId" /> 
			   					</logic:empty>
		   					</li>
		   				</logic:equal>
		   				<logic:equal name="role" value="1">
							<li>
								<label>派送信息ID</label>
								<html:text property="contractId" maxlength="11" styleClass="searchPaymentAdjustmentImportBatch_contractId" /> 
							</li> 
						</logic:equal>
						<li>
							<label>调整月份</label> 
							<html:select property="monthly" styleClass="searchPaymentAdjustmentImportBatch_monthly">
								<html:optionsCollection property="monthlies" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						
						<li>
							<label><bean:message bundle="public" key="public.status" /></label> 
							<html:select property="status" styleClass="searchPaymentAdjustmentImportBatch_status">
								<html:optionsCollection property="statuses" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
					</ol>
				</fieldset> 
       		</html:form>
   	    </div>
	</div>
	
	<!-- Information Search Result -->
	<div class="box noHeader" id="search-results">
		<div class="inner">
			<div id="messageWrapper">
				<logic:present name="MESSAGE">
					<div class="message <bean:write name="MESSAGE_CLASS" /> fadable">
						<bean:write name="MESSAGE" />
		    			<a onclick="$('div.fadable').remove();" class="messageCloseButton">&nbsp;</a>
					</div>
				</logic:present>
			</div>
			<div class="top">
				<kan:auth right="submit" action="<%=PaymentAdjustmentImportBatchAction.accessAction%>">
					<input type="button" class="" name="btnSubmit" id="btnSubmit" value="<bean:message bundle="public" key="button.submit" />" />
				</kan:auth>
				<kan:auth right="back" action="<%=PaymentAdjustmentImportBatchAction.accessAction%>">
					<input type="button" class="function" name="btnBack" id="btnBack" value="退回">
				</kan:auth>
				<a id="filterRecords" name="filterRecords" class="inquiry"><bean:message bundle="public" key="set.filerts" /></a>
				<kan:auth right="import" action="<%=PaymentAdjustmentImportBatchAction.accessAction%>">
				<img style='float: right' src='images/import.png' onclick='popupExcelImport();' title='工资调整导入' />
				</kan:auth>
			</div>
			<!-- top -->
			<div id="tableWrapper">
				<!-- Include JSP 包含table对应的JSP文件 -->  
				<jsp:include page="/contents/payment/adjustment/batch/table/listPaymentAdjustmentImportBatchTable.jsp" flush="true"/> 
			</div>
			<!-- tableWrapper -->
			<div class="bottom">
				<p>
			</div>
		</div>
	</div>
</div>

<div id="popupWrapper">
	
	<jsp:include page="/popup/importExcel.jsp" flush = "true">
		<jsp:param value="true" name="needRemark"/>
		<jsp:param value="HRO_PAYMENT_ADJUSTMENT_HEADER" name="accessAction"/>
		<jsp:param  name="closeRefesh" value="true"/>
	</jsp:include>
</div>
					

<script type="text/javascript">
	(function($) {
		$('#menu_salary_Modules').addClass('current');
		$('#menu_salary_Adjustment_Import').addClass('selected');
		kanList_init();
		kanCheckbox_init();
		//列表双击
		$("tbody tr").dblclick(function(){ 
			 kanlist_dbclick($(this),"searchPaymentAdjustmentImportBatch_form");
		}); 
		
		// 批次退回事件
		$('#btnBack').click( function(){
			if($('#selectedIds').val() != null && $('#selectedIds').val() != ''){
				if(confirm("确定退回选中的批次？")){
					$('.searchPaymentAdjustmentImportBatch_form').attr('action', 'paymentAdjustmentImportBatchAction.do?proc=back_batch');
					submitForm('searchPaymentAdjustmentImportBatch_form', "submitObjects", null, null, null, 'tableWrapper');
					$('#selectedIds').val('');
				}
			}else{
				alert("请选择要退回的批次。");
			}
		});
		
		// 批次更新事件
		$('#btnSubmit').click( function(){
			if($('#selectedIds').val() != null && $('#selectedIds').val() != ''){
				if(confirm("确定提交选中的批次？")){
					$('.searchPaymentAdjustmentImportBatch_form').attr('action', 'paymentAdjustmentImportBatchAction.do?proc=submit_batch');
					submitForm('searchPaymentAdjustmentImportBatch_form', "submitObjects", null, null, null, 'tableWrapper');
					$('#selectedIds').val('');
				}
			}else{
				alert("请选择要提交的批次。");
			}
		});
		
	})(jQuery);
	
	function resetForm() {
	    $('.searchPaymentAdjustmentImportBatch_employeeId').val('');
	    $('.searchPaymentAdjustmentImportBatch_employeeNameZH').val('');
	    $('.searchPaymentAdjustmentImportBatch_employeeNameEN').val('');
	    $('.searchPaymentAdjustmentImportBatch_clientId').val('');
	    $('.searchPaymentAdjustmentImportBatch_clientNameZH').val('');
	    $('.searchPaymentAdjustmentImportBatch_clientNameEN').val('');
	    $('.searchPaymentAdjustmentImportBatch_orderId').val('');
	    $('.searchPaymentAdjustmentImportBatch_contractId').val('');
	    $('.searchPaymentAdjustmentImportBatch_monthly').val('0');
	    $('.searchPaymentAdjustmentImportBatch_status').val('0');
	}; 
</script>
