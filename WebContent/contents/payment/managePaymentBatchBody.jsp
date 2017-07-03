<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.web.action.BaseAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%@ page import=" com.kan.hro.web.actions.biz.payment.PaymentAction"%>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>

<div id="content">
	<div id="paymentBatch" class="box toggableForm">
		<div class="head">
			<label id="pageTitle"><bean:message bundle="public" key="oper.create" />&nbsp;<bean:message bundle="payment" key="payment.estimate" /></label>
			<logic:notEmpty name="paymentBatchForm" property="batchId" ><label class="recordId"> &nbsp; (ID: <bean:write name="paymentBatchForm" property="batchId" />)</label></logic:notEmpty>
		</div>
		<div class="inner">
			<div class="top">
				<kan:auth right="new" action="<%=PaymentAction.ACCESS_ACTION%>">
					<input type="button" class="editbutton" name="btnEdit" id="btnEdit" value="<bean:message bundle="public" key="button.create" />" />
				</kan:auth>
				<kan:auth right="list" action="<%=PaymentAction.ACCESS_ACTION%>">
					<input type="button" class="reset" name="btnList" id="btnList" value="<bean:message bundle="public" key="button.list" />" />
				</kan:auth>
			</div>
			<html:form action="paymentAction.do?proc=add_object" styleClass="managePaymentBatch_form">
				<%= BaseAction.addToken( request ) %>
				<input type="hidden" name="batchId" id="batchId" value='<bean:write name="paymentBatchForm" property="encodedId" />'>
				<input type="hidden" name="subAction" id="subAction" class="subAction" value='<bean:write name="paymentBatchForm" property="subAction" />' />
				<fieldset>
					<ol class="auto">
						<li class="required"><label><em>* </em><bean:message bundle="public" key="required.field" /></label></li>  
					</ol>
					<ol class="auto">
						<li>
							<label><bean:message bundle="payment" key="payment.batch.month" /><em> *</em></label> 
							<html:select property="monthly" styleId="monthly" styleClass="managePaymentBatch_monthly">
								<html:optionsCollection property="monthlies" value="mappingId" label="mappingValue" />
	
<!-- <option value="2015/11">2015/11</option>							
<option value="2015/10">2015/10</option>
<option value="2015/09">2015/09</option>
<option value="2015/08">2015/08</option>
<option value="2015/07">2015/07</option>
<option value="2015/06">2015/06</option>
<option value="2015/05">2015/05</option>
<option value="2015/04">2015/04</option>
<option value="2015/03">2015/03</option>
<option value="2015/02">2015/02</option>
<option value="2015/01">2015/01</option> -->
							</html:select>
						</li>					
					</ol>
					<ol class="auto">			
						<li>
							<label><bean:message bundle="security" key="security.entity" /></label> 
							<html:select property="entityId" styleId="entityId" styleClass="managePaymentBatch_entityId">
								<html:optionsCollection property="entitys" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						<li>
							<label><bean:message bundle="security" key="security.business.type" /></label> 
							<html:select property="businessTypeId" styleId="businessTypeId" styleClass="managePaymentBatch_businessTypeId">
								<html:optionsCollection property="businessTypes" value="mappingId" label="mappingValue" />
							</html:select>
						</li>						
						<li<logic:equal name="role" value="2"> style="display:none"</logic:equal>>
							<label>客户ID</label>
							<html:text property="clientId" styleId="clientId" maxlength="10" styleClass="managePaymentBatch_clientId" />
							<a onclick="popupClientSearch()" class="kanhandle"><img src="images/search.png" title="<bean:message bundle="public" key="button.search" />" /></a> 
						</li>
						<li>
							<label>
								<logic:equal name="role" value="1"><bean:message bundle="public" key="public.order1.id" /></logic:equal>
								<logic:equal name="role" value="2"><bean:message bundle="public" key="public.order2.id" /></logic:equal>
							</label> 
							<logic:equal name="role" value="1">
								<html:text property="orderId" styleId="orderId" maxlength="10" styleClass="managePaymentBatch_orderId" /> 
								<a onclick="popupOrderSearch()" class="kanhandle"><img src="images/search.png" title="<bean:message bundle="public" key="button.search" />" /></a> 
							</logic:equal>
							<logic:equal name="role" value="2">
								<html:select property="orderId" styleId="orderId" styleClass="managePaymentBatch_orderId">
									<html:optionsCollection name="clientOrderHeaderMappingVOs" value="mappingId" label="mappingValue" />
								</html:select>
							</logic:equal>
						</li>
						<li>
							<label>
								<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.id" /></logic:equal>
								<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.id" /></logic:equal>
							</label> 
							<html:text property="employeeId" styleId="employeeId" maxlength="10" styleClass="managePaymentBatch_employeeId" /> 
							<a onclick="popupEmployeeSearch()" class="kanhandle"><img src="images/search.png" title="<bean:message bundle="public" key="button.search" />" /></a>
						</li>
						<logic:equal name="role" value="2">
							<li>
								<label>
									<logic:equal name="role" value="1"><bean:message bundle="public" key="public.contract1.id" /></logic:equal>
									<logic:equal name="role" value="2"><bean:message bundle="public" key="public.contract2.id" /></logic:equal>
								</label> 
								<html:text property="contractId" styleId="contractId" maxlength="10" styleClass="managePaymentBatch_contractId" />
								<a onclick="popupContractSearch()" class="kanhandle"><img src="images/search.png" title="<bean:message bundle="public" key="button.search" />" /></a>  
							</li>
						</logic:equal>
					</ol>
					<ol class="auto">
						<li>
							<label><bean:message bundle="public" key="public.description" /></label>
							<html:textarea property="description" styleId="description" styleClass="managePaymentBatch_description"></html:textarea>
						</li>
					</ol>
				</fieldset>
			</html:form>
		</div>
	</div>
</div>

<div id="popupWrapper">
	<logic:equal name="role" value="1">
		<jsp:include page="/popup/searchClient.jsp"></jsp:include>
		<jsp:include page="/popup/searchOrder.jsp"></jsp:include>
	</logic:equal>
	<jsp:include page="/popup/searchContract.jsp"></jsp:include>
	<jsp:include page="/popup/searchEmployee.jsp"></jsp:include>
</div>

<script type="text/javascript">
	(function($) {
		// 设置菜单选择样式
		$('#menu_salary_Modules').addClass('current');
		$('#menu_salary_Process').addClass('selected');
		$('#menu_salary_Estimate').addClass('selected');
		
		// 编辑按钮点击事件
		$('#btnEdit').click( function () { 
       		var flag = 0;
   			flag = flag + validate("managePaymentBatch_monthly", true, "select", 0, 0);
   			
   			if(flag == 0){
   				submit('managePaymentBatch_form');
   			}
		});

		$('#btnList').click( function () {
			if (agreest())
			link('paymentAction.do?proc=list_object');
		});
	})(jQuery);
</script>
