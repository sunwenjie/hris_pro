<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ page import=" com.kan.hro.web.actions.biz.payment.PaymentAdjustmentHeaderAction"%>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>

<%
	final PagedListHolder paymentAdjustmentHeaderHolder = (PagedListHolder) request.getAttribute("paymentAdjustmentHeaderHolder");
%>

<div id="content">
	<div class="box searchForm toggableForm" id="paymentAdjustmentHeader-information">
		<div class="head"><label id="itleLable"><bean:message bundle="payment" key="payment.salary.adjustment.confirm.header.search.title" /></label></div>
		<a href="#" class="toggle tiptip" title="<bean:message key="public.hideoptions"/>">&gt;</a>
		<div id="searchDiv" class="inner">
			<div class="top">
				<input type="button" id="searchBtn" name="searchBtn" value="<bean:message bundle="public" key="button.search" />" onclick="submitForm('list_form', 'searchObject', null, null, null, null);" />
				<input type="button" class="reset" id="resetBtn" name="resetBtn" value="<bean:message bundle="public" key="button.reset" />" onclick="resetForm();" />
			</div>
			<html:form action="paymentAdjustmentHeaderAction.do?proc=list_object_confirm" styleClass="list_form">
				<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="paymentAdjustmentHeaderHolder" property="sortColumn" />" />
				<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="paymentAdjustmentHeaderHolder" property="sortOrder" />" />
				<input type="hidden" name="page" id="page" value="<bean:write name="paymentAdjustmentHeaderHolder" property="page" />" />
				<input type="hidden" name="selectedIds" id="selectedIds" value="<bean:write name="paymentAdjustmentHeaderHolder" property="selectedIds" />" />
				<input type="hidden" name="subAction" id="subAction" value="" />
				<input type="hidden" name="pageFlag" id="pageFlag" value="<bean:write name="pageFlag" />" />
				<fieldset>
					<ol class="auto">
						<li>
							<label><bean:message bundle="payment" key="payment.salary.adjustment.header.id" /></label> 
							<html:text property="adjustmentHeaderId" maxlength="12" styleClass="searchPaymentAdjustmentHeader_adjustmentHeaderId" /> 
						</li>
						<li>
							<label>
								<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.id" /></logic:equal>
								<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.id" /></logic:equal>
							</label> 
							<html:text property="employeeId" styleId="employeeId" maxlength="10" styleClass="searchPaymentAdjustmentHeader_employeeId" /> 
						</li>
						<li>
							<label>
								<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.name.cn" /></logic:equal>
								<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.name.cn" /></logic:equal>
							</label> 
							<html:text property="employeeNameZH" styleId="employeeNameZH" maxlength="10" styleClass="searchPaymentAdjustmentHeader_employeeNameZH" /> 
						</li>
						<li>
							<label>
								<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.name.en" /></logic:equal>
								<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.name.en" /></logic:equal>
							</label> 
							<html:text property="employeeNameEN" styleId="employeeNameEN" maxlength="10" styleClass="searchPaymentAdjustmentHeader_employeeNameEN" /> 
						</li>
						<li <logic:equal name="role" value="2">style="display:none"</logic:equal>>
							<label>客户ID</label>
							<html:text property="clientId" styleId="clientId" maxlength="10" styleClass="searchPaymentAdjustmentHeader_clientId" />
						</li>
						<li>
							<label>
								<logic:equal name="role" value="1"><bean:message bundle="public" key="public.order1.id" /></logic:equal>
								<logic:equal name="role" value="2"><bean:message bundle="public" key="public.order2.id" /></logic:equal>
							</label>
							<logic:equal name="role" value="1">
									<html:text property="orderId" styleId="orderId" maxlength="10" styleClass="searchPaymentAdjustmentHeader_orderId" />
							</logic:equal>
							<logic:equal name="role" value="2">
								<html:select property="orderId" styleId="orderId" styleClass="searchPaymentAdjustmentHeader_orderId">
									<html:optionsCollection name="clientOrderHeaderMappingVOs" value="mappingId" label="mappingValue" />
								</html:select>
							</logic:equal>
						</li>
						<li>
							<label>
								<logic:equal name="role" value="1"><bean:message bundle="public" key="public.contract1.id" /></logic:equal>
								<logic:equal name="role" value="2"><bean:message bundle="public" key="public.contract2.id" /></logic:equal>
							</label>
							<html:text property="contractId" styleId="contractId"  maxlength="10" styleClass="searchPaymentAdjustmentHeader_contractId" />
						</li>
						<li>
							<label><bean:message bundle="security" key="security.entity" /></label> 
							<html:select property="entityId" styleClass="searchPaymentAdjustmentHeader_entityId">
								<html:optionsCollection property="entities" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						<li>
							<label><bean:message bundle="security" key="security.business.type" /></label> 
							<html:select property="businessTypeId" styleClass="searchPaymentAdjustmentHeader_businessTypeId">
								<html:optionsCollection property="businessTypes" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						<li>
							<label><bean:message bundle="payment" key="payment.salary.adjustment.header.monthly" /></label>
							<html:select property="monthly" styleClass="searchPaymentAdjustmentHeader_monthly">
								<html:optionsCollection property="monthlies" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
					</ol>
				</fieldset>
			</html:form>
		</div>
	</div>

	<!-- paymentAdjustmentHeader-information -->
	<div class="box noHeader" id="search-results">
		<jsp:include page="table/listPaymentAdjustmentHeaderConfirmTable.jsp" flush="true"/>
	</div>
</div>

<script type="text/javascript">
	(function($) {
		// 设置顶部菜单选择样式
		$('#menu_salary_Modules').addClass('current');
		$('#menu_salary_Process').addClass('selected');
		$('#menu_salary_AdjustmentConfirm').addClass('selected');
		
		$('#searchDiv').hide();

		// 确认事件
		$('#btnApprove').live('click',function(){
			if($('#selectedIds').val() != null && $('#selectedIds').val() != ''){
				if(confirm('<bean:message bundle="public" key="popup.confirm.approve.records" />')){
					$('.list_form').attr('action', 'paymentAdjustmentHeaderAction.do?proc=approve_object');
					submitForm('list_form', "confirmObjects", null, null, null, "search-results");
					$('#selectedIds').val('');
				}
			}else{
				alert('<bean:message bundle="public" key="popup.not.selected.records" />');
			}
		});
		
		// 退回事件
		$('#btnRollback').live('click',function(){
			if($('#selectedIds').val() != null && $('#selectedIds').val() != ''){
				if(confirm('<bean:message bundle="public" key="popup.confirm.return.records" />')){
					$('.list_form').attr('action', 'paymentAdjustmentHeaderAction.do?proc=rollback_object');
					submitForm('list_form', "rollbackObjects", null, null, null, "search-results");
					$('#selectedIds').val('');
				}
			}else{
				alert('<bean:message bundle="public" key="popup.not.selected.records" />');
			}
		});
	})(jQuery);
	
	function resetForm() {
		$('.searchPaymentAdjustmentHeader_adjustmentHeaderId').val('');
		$('.searchPaymentAdjustmentHeader_clientId').val('');
		$('.searchPaymentAdjustmentHeader_entityId').val('0');
		$('.searchPaymentAdjustmentHeader_businessTypeId').val('0');
		$('.searchPaymentAdjustmentHeader_orderId').val('');
		$('.searchPaymentAdjustmentHeader_contractId').val('');
		$('.searchPaymentAdjustmentHeader_employeeId').val('');
		$('.searchPaymentAdjustmentHeader_employeeNameZH').val('');
		$('.searchPaymentAdjustmentHeader_employeeNameEN').val('');
		$('.searchPaymentAdjustmentHeader_monthly').val('0');
	};
</script>
