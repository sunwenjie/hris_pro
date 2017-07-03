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
	            <input type="button" id="searchBtn" name="searchBtn" value="<bean:message bundle="public" key="button.search" />" onclick="submitForm('searchPaymentAdjustmentImportHeader_form', 'searchObject', null, null, null, null);" />
	            <input type="button" class="reset" id="resetBtn" name="resetBtn" value="<bean:message bundle="public" key="button.reset" />" onclick="resetForm();" />
       		</div>
       		<html:form action="paymentAdjustmentImportHeaderAction.do?proc=list_object" method="post" styleClass="searchPaymentAdjustmentImportHeader_form">
       			<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="paymentAdjustmentImportHeaderHolder" property="sortColumn" />" /> 
				<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="paymentAdjustmentImportHeaderHolder" property="sortOrder" />" />
				<input type="hidden" name="page" id="page" value="<bean:write name="paymentAdjustmentImportHeaderHolder" property="page" />" />
				<input type="hidden" name="selectedIds" id="selectedIds" value="<bean:write name="paymentAdjustmentImportHeaderHolder" property="selectedIds" />" />
				<input type="hidden" name="subAction" id="subAction" value="" />
				<input type="hidden" name="batchId" id="batchId" value='<bean:write name="paymentAdjustmentImportBatchVO" property="encodedBatchId" />' />
				<input type="hidden" name="definedMessage" id="definedMessage" value="true" />
				<fieldset>
					<ol class="auto">
						<li>
							<label>调整ID</label> 
							<html:text property="adjustmentHeaderId" maxlength="12" styleClass="searchPaymentAdjustmentHeader_adjustmentHeaderId" /> 
						</li>
						<li>
							<label><logic:equal name="role" value="1">雇员</logic:equal><logic:equal name="role" value="2">员工</logic:equal>ID</label>
							<html:text property="employeeId" maxlength="11" styleClass="searchPaymentAdjustmentImportHeader_employeeId" /> 
						</li>
						<li>
							<label><logic:equal name="role" value="1">雇员</logic:equal><logic:equal name="role" value="2">员工</logic:equal>姓名（中文）</label>
							<html:text property="employeeNameZH" maxlength="100" styleClass="searchPaymentAdjustmentImportHeader_employeeNameZH" /> 
						</li>
						<li>
							<label><logic:equal name="role" value="1">雇员</logic:equal><logic:equal name="role" value="2">员工</logic:equal>姓名（英文）</label>
							<html:text property="employeeNameEN" maxlength="100" styleClass="searchPaymentAdjustmentImportHeader_employeeNameEN" /> 
						</li>
						<logic:equal name="role" value="1">
							<li>
								<label>客户ID</label>
								<html:text property="clientId" maxlength="11" styleClass="searchPaymentAdjustmentImportHeader_clientId" /> 
							</li> 
							<li>
								<label>客户名称（中文）</label>
								<html:text property="clientNameZH" maxlength="100" styleClass="searchPaymentAdjustmentImportHeader_clientNameZH" /> 
							</li>
							<li>
								<label>客户名称（英文）</label>
								<html:text property="clientNameEN" maxlength="100" styleClass="searchPaymentAdjustmentImportHeader_clientNameEN" /> 
							</li>
						</logic:equal>
						<logic:equal name="role" value="1">
							<li>
								<label>订单ID</label>
								<html:text property="orderId" maxlength="10" styleClass="searchPaymentAdjustmentImportHeader_orderId" /> 
							</li>
						</logic:equal>
						<logic:equal name="role" value="2">
							<li>
								<label>结算规则ID</label>
			   					<logic:notEmpty name="clientOrderHeaderMappingVOs">
									<html:select property="orderId" styleClass="searchPaymentAdjustmentImportHeader_orderId">
										<html:optionsCollection name="clientOrderHeaderMappingVOs" value="mappingId" label="mappingValue" />
									</html:select>
			   					</logic:notEmpty>
			   					<logic:empty name="clientOrderHeaderMappingVOs">
			   						<html:text property="orderId" maxlength="10" styleClass="searchPaymentAdjustmentImportHeader_orderId" /> 
			   					</logic:empty>
		   					</li>
		   				</logic:equal>
		   				<logic:equal name="role" value="1">
							<li>
								<label><logic:equal name="role" value="1">派送信息</logic:equal><logic:equal name="role" value="2">劳动合同</logic:equal>ID</label>
								<html:text property="contractId" maxlength="11" styleClass="searchPaymentAdjustmentImportHeader_contractId" /> 
							</li> 
						</logic:equal>
						<li>
							<label>调整月份</label> 
							<html:select property="monthly" styleClass="searchPaymentAdjustmentImportHeader_monthly">
								<html:optionsCollection property="monthlies" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						
						<li>
							<label><bean:message bundle="public" key="public.status" /></label> 
							<html:select property="status" styleClass="searchPaymentAdjustmentImportHeader_status">
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
				<kan:auth right="list" action="<%=PaymentAdjustmentImportBatchAction.accessAction%>">
					<input type="button" class="reset" id="btnList" name="btnList" value="<bean:message bundle="public" key="button.list" />" onclick="link('paymentAdjustmentImportBatchAction.do?proc=list_object');" />
				</kan:auth>
				<kan:auth right="back" action="<%=PaymentAdjustmentImportBatchAction.accessAction%>">
					<input type="button" class="function" name="btnBack" id="btnBack" value="退回">
				</kan:auth>
				<a id="filterRecords" name="filterRecords" class="inquiry"><bean:message bundle="public" key="set.filerts" /></a>
			</div>
			
			<div class="tabMenu"> 
				<ul> 
					<li id="tabMenu1" onClick="changeTab(1,1)" class="first hover">批次 (ID: <bean:write name="paymentAdjustmentImportBatchVO" property="batchId" />)</li> 
				</ul> 
			</div>
			<div class="tabContent"> 
				<div id="tabContent1" class="kantab" >
					<form action="">
						<fieldset>
						  
				            <ol class="auto">
				            	<li><label>合同数量：</label><span><bean:write name="paymentAdjustmentImportBatchVO" property="countContractId"/></span></li>
				            	<li><label>调整数量：</label><span><bean:write name="paymentAdjustmentImportBatchVO" property="countHeaderId"/></span></li>
				            	<li><label>收入合计（个人）金额：</label><span><bean:write name="paymentAdjustmentImportBatchVO" property="billAmountPersonal"/></span></li>
				            	<li><label>支出合计（个人）金额：</label><span><bean:write name="paymentAdjustmentImportBatchVO" property="costAmountPersonal"/></span></li>
				            </ol>
		                </fieldset>
	               </form> 
				</div>
			</div>
			
			<!-- top -->
			<div id="tableWrapper">
				<!-- Include JSP 包含table对应的JSP文件 -->  
				<jsp:include page="/contents/payment/adjustment/header/table/listPaymentAdjustmentImportHeaderTable.jsp" flush="true"/>
			</div>
			<!-- tableWrapper -->
			<div class="bottom">
				<p>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript">
	(function($) {
		$('#menu_salary_Modules').addClass('current');
		$('#menu_salary_Adjustment_Import').addClass('selected');
		kanList_init();
		kanCheckbox_init();
		//列表双击
		$("tbody tr").dblclick(function(){ 
			 kanlist_dbclick($(this),"searchPaymentAdjustmentImportHeader_form");
		});
		// 批次退回事件
		$('#btnBack').click( function(){
			if($('#selectedIds').val() != null && $('#selectedIds').val() != ''){
				if(confirm("确定退回选中的批次？")){
					$('.searchPaymentAdjustmentImportHeader_form').attr('action', 'paymentAdjustmentImportHeaderAction.do?proc=backUpRecord');
					submitForm('searchPaymentAdjustmentImportHeader_form');
					$('#selectedIds').val('');
				}
			}else{
				alert("请选择要退回的批次。");
			}
		});
	})(jQuery);
	
	function resetForm() {
	    $('.searchPaymentAdjustmentImportHeader_employeeId').val('');
	    $('.searchPaymentAdjustmentImportHeader_employeeNameZH').val('');
	    $('.searchPaymentAdjustmentImportHeader_employeeNameEN').val('');
	    $('.searchPaymentAdjustmentImportHeader_clientId').val('');
	    $('.searchPaymentAdjustmentImportHeader_clientNameZH').val('');
	    $('.searchPaymentAdjustmentImportHeader_clientNameEN').val('');
	    $('.searchPaymentAdjustmentImportHeader_orderId').val('');
	    $('.searchPaymentAdjustmentImportHeader_contractId').val('');
	    $('.searchPaymentAdjustmentImportHeader_monthly').val('0');
	    $('.searchPaymentAdjustmentImportHeader_status').val('0');
	}; 
</script>
